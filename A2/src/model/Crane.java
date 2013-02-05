package model;

import java.awt.Graphics;
import java.awt.Color;

public class Crane extends Object{
	Rectangle track;
	
	public Crane(){
		//Draw Track
		track = new Rectangle(10,400,100,30);
		track.setColor(Color.BLACK);
		
		Rectangle body = new Rectangle(20,350, 80, 50);
		body.setColor(Color.gray);
		track.addChild(body);
		
		Rectangle joint = new Rectangle(45,340 ,30,10 );
		joint.setColor(Color.BLACK);
		track.addChild(joint);
		
		//Note: each anchor point is the previous attach point, this way is very easy to set rotation
		Rectangle arm1 = new Rectangle(45,270, 20,70);
		arm1.setColor(Color.magenta);
		arm1.setRotate(0, 45, 340);
		track.addChild(arm1);
		
		Rectangle arm2 = new Rectangle(45,200, 20,70);
		arm2.setColor(Color.cyan);
		arm2.setRotate(0, 45, 270);
		arm1.addChild(arm2);
		
		Rectangle arm3 = new Rectangle(45,130, 20,70);
		arm3.setColor(Color.blue);
		arm3.setRotate(0,45,200);
		arm2.addChild(arm3);
		
		Rectangle arm4 = new Rectangle(45,60, 20,70);
		arm4.setColor(Color.red);
		arm4.setRotate(0,45,130);
		arm3.addChild(arm4);
		
		Rectangle electro = new Rectangle(35,50,40,10);
		electro.setColor(Color.yellow);
		electro.setRotate(0,45,60);
		arm3.addChild(electro);
		
		
	}
	
	public void drawCrane(Graphics g){
		track.paintRectangle(g);
	}
	
}
