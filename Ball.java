import java.applet.*;
import java.awt.*;
import javax.swing.*;

public class Ball{

	public double pos_x;
	public double pos_y;
	public double x_speed;
	public double y_speed;
	public int ballradius;
	public int klickflaeche = 200;
	public int kraftProKlick = 10;
	public int linkerRand = 0;
	public int rechterRand = 400;
	public int obererRand = 0;
	public double gravitation = 0.4;



	public ImageIcon derBall = new ImageIcon("ball.gif");

	public Ball(ImageIcon derBall, int pos_x, int pos_y, double x_speed, double y_speed, int ballradius ){
		this.derBall = derBall;
		this.pos_x= pos_x;
		this.pos_y= pos_y;
		this.x_speed = x_speed;
		this.y_speed = y_speed;
		this.ballradius= ballradius;


	}
	
	public boolean getroffen(int maus_x, int maus_y){
		// Bestimmen der Verbindungsvektoren
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;

		// Berechnen der Distanz (Skalarprodukt)
		double distance = Math.sqrt ((x*x) + (y*y));

		// Wenn Distanz kleiner/gleich Ballradius && Klick in der Klickflaeche erfolgt, gilt der Ball als getroffen
		if ((distance <= ballradius) && (maus_y >= klickflaeche)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void beschleunigen(int maus_x, int maus_y){
		// Bestimmen der Abstaende
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;
		double x_positiv = x;
		double y_positiv = y;
		//Positive Werte bilden
		if (x_positiv<0){
			x_positiv = x_positiv*-1;
		}
		if (y_positiv<0){
			y_positiv = y_positiv*-1;
		}
		//Anteile der Kraft auf beide Achsen bestimmen
		double kraftAnteile = x_positiv+y_positiv;
		double x_kraft = (kraftProKlick/kraftAnteile)*x_positiv;
		double y_kraft = (kraftProKlick/kraftAnteile)*y_positiv;
		//Kraefte den x und y Geschwindigkeiten des Balles anrechnen 
		//Klick unten links
		if(x<=0 && y>=0){
			x_speed = x_speed + x_kraft;
			y_speed = y_speed - y_kraft;
		}
		//Klick unten rechts
		else if(x>=0 && y>=0){
			x_speed = x_speed - x_kraft;
			y_speed = y_speed - y_kraft;
		}
		//Klick oben rechts
		else if(x>=0 && y<=0){
			x_speed = x_speed - x_kraft;
			y_speed = y_speed + y_kraft;
		}
		//Klick oben links
		else{
			x_speed = x_speed + x_kraft;
			y_speed = y_speed + y_kraft;
		}
	}
	
	public void bewegen(){
		//Abprallen des Balles an linken,rechten,oberen Rand
		if((pos_x<(linkerRand+ballradius))||(pos_x>(rechterRand-ballradius))){
			x_speed = x_speed*-1;
		}
		if(pos_y<(obererRand+ballradius)){
			y_speed = y_speed*-1;
		}
		//Fallgeschwindigkeit/Zeit erhöhen
		y_speed = y_speed + gravitation;
		//Neue Position bestimmen
		pos_x = pos_x+x_speed;
		pos_y = pos_y+y_speed;
	}
	
	public void paintBall(Graphics g){
	//	g.paintIcon(derBall, pos_x, pos_y, x_speed, y_speed, ballradius);

	}
}