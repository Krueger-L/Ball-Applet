import java.applet.*;
import java.awt.*;
import javax.swing.*;

public class Ball{

	public int pos_x;
	public int pos_y;
	public int x_speed;
	public int y_speed;
	public int ballradius;


	public ImageIcon derBall = new ImageIcon("ball.gif");

	public Ball(ImageIcon derBall, int pos_x, int pos_y, int x_speed, int y_speed, int ballradius ){
		this.derBall = derBall;
		this.pos_x= pos_x;
		this.pos_y= pos_y;
		this.x_speed = x_speed;
		this.y_speed = y_speed;
		this.ballradius= ballradius;


	}
	public void paintBall(Graphics g){
	//	g.paintIcon(derBall, pos_x, pos_y, x_speed, y_speed, ballradius);

	}
}