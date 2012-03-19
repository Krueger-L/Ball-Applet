import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.*;
import java.net.*;

public class Main extends Applet implements MouseListener, Runnable{
	// Deklarationen der Variablen
	private int speed;				// Threadgeschwindigkeit
	private int highscoreCount=0;
	boolean isStoped = true;		// Zeigt an, ob das Spiel gestopt ist (true) oder laeuft (false)

	// Deklaration der Objektreferenz
	private Ball ball;
	//Thread
	Thread th;						// Thread in dem das Spiel laeuft

    // Variablen fuer die Doppelpufferung
	private Image dbImage;
	private Graphics dbg;

	//Hintergrundbild
	private Image hintergrund;

	//Der Text zum Starten soll zentriert werden, darum muss die Zeichenbreite ermittelt werden -> metrics
	FontMetrics  fontMetrik;

	//die Strings die benutzt werden
	String startMessage;
	String score;
	String count;
	String highscoreCounter;

	// Init - Methode
	public void init (){	
		addMouseListener(this);
		startMessage = "Doppelklicken um zu starten";

		// Setzen der Hintergrundfarbe
		setBackground (Color.black);

		//Hintergrundbild laden
		hintergrund = getImage(getCodeBase(),"bckgrd.gif");

		// Speed wird von Parameter speed des Applets bestimmt
		if (getParameter ("speed") != null){
			speed = Integer.parseInt(getParameter("speed"));
		}
		else speed = 10;

		// Initialisierung des Balles
		ball = new Ball (200, 400, 0, 0, 40, Color.red, 0);
		
		count = "Clicks: " + ball.getCount();
		highscoreCounter = "Click-Highscore: "+ highscoreCount;
		score = "Score: " + ball.getCount();
	}


	// Start - Methode, hier beginnt das Applet zu laufen
	public void start (){
		// Schaffen eines neuen Threads, in dem das Spiel laeuft
		th = new Thread (this);
		th.start ();
	}

	// Stop - Methode, hier wird das Applet gestopt
	public void stop (){
		//th.stop();
	}

	public void mouseEntered( MouseEvent e ){}
  	public void mouseExited( MouseEvent e ){}
  	public void mouseDragged( MouseEvent e ){} 
   	public void mouseMoved( MouseEvent e ){}
	public void mouseReleased( MouseEvent e ){} 
	public void mouseClicked(MouseEvent e ){}
	public void mousePressed( MouseEvent e){

		// Spiel laeuft
		if (!isStoped){
			// Test ob Ball getroffen wurde
			if (ball.getroffen (e.getX(), e.getY())){
				ball.beschleunigen(e.getX(), e.getY());
	        }
			
		}
		// Wenn Spiel noch nicht gestartet ist, oder wieder gestartet wird
		else if (isStoped && e.getClickCount() == 2){
		    // Alle wichtigen Werte zuruecksetzen
			isStoped = false;
			removeMouseListener(this);
			init ();
			
		}
		

	}
	// Implementierung der Runmethode
	public void run (){
		// Erniedrigen der ThreadPriority um zeichnen zu erleichtern
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true){
			if (ball.isOut()==false && !isStoped){
				ball.bewegen();
			}

			repaint();

			try{
				// Stoppen des Threads fuer 10 Millisekunden
				Thread.sleep (speed);
			}
			catch (InterruptedException ex){
				// nichts machen
			}

			// Zuruecksetzen der ThreadPriority auf Maximalwert
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

	// Paint - Methode
	public void paint (Graphics g){
		// Font groesser machen
		Font normalerFont = g.getFont();
		Font f = normalerFont.deriveFont(20f);
		
		// die start- message zentrieren, also textweite und koordinaten zum zeichnen ermitteln 
		fontMetrik = getFontMetrics(f);
		int messageAusrichtungX = (getSize().width - fontMetrik.stringWidth(startMessage)) / 2;
		int messageAusrichtungY = getSize().height /2;
		
		//counter links unten
		int clickCountAusrichtungX = (getSize().width - fontMetrik.stringWidth(highscoreCounter)) / 2;
		int clickCountAusrichtungY = getSize().height - 50;

		//highscore rechts unten
		int highscoreAusrichtungX = (getSize().width - fontMetrik.stringWidth(count))/2;	//optional 30 oder so, wenn daneben noch zeit angezeigt werden soll
		int highscoreAusrichtungY = getSize().height - 50;
		g.setFont(f);
		//hintergrund zeichnen
		g.drawImage (hintergrund, 0, 0, this);

		// Solange Ball noch in der Luft ist
		if (ball.isOut() == false){
			// Setzen der Farbe
			g.setColor (Color.yellow);

			// Punktestand bei laufenden Spiel
			//g.drawString ("Score: " + ball.getCount(), 10, 40);
			g.drawString ("Score: " + ball.getCount(), highscoreAusrichtungX, highscoreAusrichtungY);
			// Zeichnen des Balls
			ball.paintBall(g);

			// Startbildschirm
			if (isStoped){
				g.setColor (Color.yellow);
				g.drawString (/*"Doppelklicken um das Spiel zu starten!"*/startMessage, messageAusrichtungX, messageAusrichtungY);
			}
		}
		// Wenn Ball den Boden berruehrt
		else if (ball.isOut() == true){
			g.setColor (Color.yellow);
			if (highscoreCount < ball.getCount()){
				highscoreCount = ball.getCount();
			}
			// Erreichte Punkte und game over
			//g.drawString ("Clicks: " + ball.getCount(), 90, 140);
			g.drawString ("Clicks: " + ball.getCount(), highscoreAusrichtungX, (messageAusrichtungY-50));
			g.drawString ("Click-Highscore: "+highscoreCount,clickCountAusrichtungX, (messageAusrichtungY-100));
			
			g.drawString (/*"Doppelklicken um neu zu starten"*/startMessage, messageAusrichtungX, messageAusrichtungY);

			isStoped = true;	// Zuruecksetzen der isStoped Variablen, um wieder neu beginnen zu koennen

		}
	}

	// Update - Methode, Realisierung der Doppelpufferung zur Reduzierung des Bildschirmflackerns
	public void update (Graphics g){
		// Initialisierung des DoubleBuffers
		if (dbImage == null){
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		// Bildschirm im Hintergrund loeschen
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// Auf geloeschten Hintergrund Vordergrund zeichnen
		dbg.setColor (getForeground());
		paint (dbg);

		// Nun fertig gezeichnetes Bild Offscreen auf dem richtigen Bildschirm anzeigen
		g.drawImage (dbImage, 0, 0, this);
	}
}