import java.awt.*;
import java.util.*;
import java.applet.*;
import java.net.*;

public class Main extends Applet implements Runnable{
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

	// Init - Methode
	public void init (){

		// Neue Hintergrundfarbe
        Color superblue = new Color (0, 0, 255);

		// Setzen der Hintergrundfarbe
		setBackground (Color.black);

		// Speed wird von Parameter speed des Applets bestimmt
		if (getParameter ("speed") != null){
			speed = Integer.parseInt(getParameter("speed"));
		}
		else speed = 15;

		// Initialisierung des Balles
		ball = new Ball (200, 400, 0, 0, 40, Color.red, 0);
	}


	// Start - Methode, hier beginnt das Applet zu laufen
	public void start (){
		// Schaffen eines neuen Threads, in dem das Spiel laeuft
		th = new Thread (this);
		th.start ();
	}

	// Stop - Methode, hier wird das Applet gestopt
	public void stop (){
		th.stop();
	}

	// Auffangen des Mausereignisses mouseDown
	public boolean mouseDown (Event e, int x, int y){
		// Spiel laeuft
		if (!isStoped){
			// Test ob Ball getroffen wurde
			if (ball.getroffen (x, y)){
				ball.beschleunigen(x,y);
	        }
		}
		// Wenn Spiel noch nicht gestartet ist, oder wieder gestartet wird
		else if (isStoped && e.clickCount == 2){
		    // Alle wichtigen Werte zuruecksetzen
			isStoped = false;
			init ();
		}

		return true;
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
		// Solange Ball noch in der Luft ist
		if (ball.isOut() == false){
			// Setzen der Farbe
			g.setColor (Color.yellow);

			// Punktestand bei laufenden Spiel
			g.drawString ("Score: " + ball.getCount(), 10, 40);

			// Zeichnen des Balls
			ball.paintBall(g);

			// Startbildschirm
			if (isStoped){
				g.setColor (Color.yellow);
				g.drawString ("Doppelklicken um das Spiel zu starten!", 40, 200);
			}
		}
		// Wenn Ball den Boden berruehrt
		else if (ball.isOut() == true){
			g.setColor (Color.yellow);
			if (highscoreCount < ball.getCount()){
				highscoreCount = ball.getCount();
			}
			// Erreichte Punkte und game over
			g.drawString ("Clicks: " + ball.getCount(), 90, 140);
			g.drawString ("Click-Highscore: "+highscoreCount,90, 170);
			g.drawString ("Doppelklicken um neu zu starten", 20, 220);

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