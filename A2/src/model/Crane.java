package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Crane extends Object{
	Rectangle track;
	String hitId;
	Point hitPoint;
	public Crane(){
		hitId = null;
		hitPoint  = null;
		//Draw Track
		track = new Rectangle(10,400,100,30,"track");
		track.setColor(Color.BLACK);
		
		Rectangle body = new Rectangle(20,350, 80, 50, "body");
		body.setColor(Color.gray);
		track.addChild(body);
		
		Rectangle joint = new Rectangle(45,340 ,30,10 ,"joint" );
		joint.setColor(Color.BLACK);
		track.addChild(joint);
		
		//Note: each anchor point is the previous attach point, this way is very easy to set rotation
		Rectangle arm1 = new Rectangle(45,270, 20,70,"arm1");
		arm1.setColor(Color.magenta);
		arm1.setRotate(0, 45, 340);
		track.addChild(arm1);
		
		Rectangle arm2 = new Rectangle(45,200, 20,70,"arm2");
		arm2.setColor(Color.cyan);
		arm2.setRotate(0, 45, 270);
		arm1.addChild(arm2);
		
		Rectangle arm3 = new Rectangle(45,130, 20,70, "arm3");
		arm3.setColor(Color.blue);
		arm3.setRotate(0,45,200);
		arm2.addChild(arm3);
		
		Rectangle arm4 = new Rectangle(45,60, 20,70, "arm4");
		arm4.setColor(Color.red);
		arm4.setRotate(0,45,130);
		arm3.addChild(arm4);
		
		Rectangle electro = new Rectangle(35,50,40,10, "electro");
		electro.setColor(Color.yellow);
		electro.setRotate(0,45,60);
		arm3.addChild(electro);
		
	}
	
	public void drawCrane(Graphics g){
		track.paintRectangle(g);
	}
	
	public void handleClick(Point point){
		//Handle click event of cane hand
		hitId = track.getHitId(point);
		hitPoint = point;
		
		if(hitId != null){
			System.out.print(hitId);
			
		}
	}
	
	public boolean handleDrag(Point point){
		if(hitId != null){ //we clicked on something already, handle the drag, else, do nothing
			if(hitId.equals("track")){
				//Only handle move on x
				if(hitPoint.x<point.x){
					track.setMovement(1, 0);
				}else if(hitPoint.x>point.x){
					track.setMovement(-1, 0);
				}
				hitPoint = point;
			}else if(hitId.equals("arm1")){
				//Just need to check if it is clockwise or counter clock wise
				
				hitPoint = point;
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void handleRelease(Point point){
		hitId = null;
		hitPoint = null;
		//if we had set some click, the cancel it
	}
	
}
