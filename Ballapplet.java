import java.applet.*;
import java.awt.*;
import java.lang.*;
import java.net.*;			
import java.awt.event.*;
import javax.swing.*;
public class Ballapplet extends JApplet implements Runnable, MouseListener{

	//ImageIcon background;
	ImageIcon ballbild;

	private Image dbImage;
	private Graphics dbg;
	private Ball derball;
	

	public void init(){
		ImageIcon background = new ImageIcon("bckgrd.gif");
		ImageIcon ballbild = new ImageIcon ("ball.gif");
		derball = new Ball(ballbild, 200,200,0,0,50);
		JLabel backgrd = new JLabel(background);
		JLabel ball = new JLabel (ballbild);
		JPanel content = new JPanel();
		
		
		content.add(backgrd);
		content.add(ball);

		setContentPane(content);

	}

	public void start(){
		Thread th = new Thread(this);
		th.start();
	}

	public void stop(){}

	public void destroy(){}

	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}


	public void paintComponent(Graphics g){
		
	}

	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true){
			repaint();
			try{
				Thread.sleep (20);	//Stoppen des threads für 20 millisec
			}
		
			catch(InterruptedException ex){
				
			}
			
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

	public void update(Graphics g){
		if (dbImage==null){
			dbImage= createImage(this.getSize().width,this.getSize().height);
			dbg=dbImage.getGraphics();
		
		}
		dbg.setColor(getBackground());
		dbg.fillRect(0,0,this.getSize().width,this.getSize().height);
		
		dbg.setColor(getForeground());
		paint(dbg);
		
		g.drawImage(dbImage,0,0,this);
	}

	public void createImage(){}














}