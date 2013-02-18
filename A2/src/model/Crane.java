package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Crane extends Object{
	Track track;
	Rectangle land;
	String hitId;
	Point hitPoint;
	public Crane(){
		hitId = null;
		hitPoint  = null;
		//Draw land
		land = new Rectangle(0,430,600,30,"land");
		land.setColor(Color.gray);
		//Draw Track
		track = new Track(10,400,100,30,"track");
		track.setColor(Color.BLACK);
		
		Rectangle body = new Rectangle(20,350, 80, 50, "body");
		body.setColor(Color.gray);
		track.addRect(body);
		
		Rectangle joint = new Rectangle(45,340 ,30,10 ,"joint" );
		joint.setColor(Color.BLACK);
		body.addRect(joint);
		
		//Note: each anchor point is the previous attach point, this way is very easy to set rotation
		Arm arm1 = new Arm(45,270, 20,70,"arm1");
		arm1.setColor(Color.magenta);
		arm1.setAnchor(0, 45, 340);
		joint.addRect(arm1);
		
		Arm arm2 = new Arm(45,200, 20,70,"arm2");
		arm2.setColor(Color.cyan);
		arm2.setAnchor(0, 45, 270);
		arm1.addRect(arm2);
		
		Arm arm3 = new Arm(45,130, 20,70, "arm3");
		arm3.setColor(Color.blue);
		arm3.setAnchor(0,45,200);
		arm2.addRect(arm3);
		
		Arm arm4 = new Arm(45,60, 20,70, "arm4");
		arm4.setColor(Color.red);
		arm4.setAnchor(0,45,130);
		arm3.addRect(arm4);
		
		Electro electro = new Electro(35,40,50,20, "electro");
		electro.setColor(Color.yellow);
		arm4.addRect(electro);
		
	}
	
	public void drawCrane(Graphics g){
		land.paint(g);
		track.paint(g);
	}
	
	//Handle click event 
	public boolean handleClick(Point point){
		
		hitId = track.getHitId(point);
		hitPoint = point;
		
		if(hitId != null){
			if(hitId.endsWith("electro")){
				Electro electro = (Electro) track.getRect("electro");
				electro.setElectro();
				return true;
			}
//			System.out.print(hitId+'\n');
		}
		return false;
	}
	
	public boolean handleDrag(Point point){
		if(hitId != null){ //we clicked on something already, handle the drag, else, do nothing
			if(hitId.equals("track")){
				track.dragToMove(hitPoint.x<point.x);
			}else if(hitId.equals("arm1")){
				Arm arm1 = (Arm) track.getRect("arm1");
				arm1.dragToRotate(hitPoint, point);
			}else if(hitId.equals("arm2")){
				Arm arm2 = (Arm) track.getRect("arm2");
				arm2.dragToRotate(hitPoint, point);
			}else if(hitId.equals("arm3")){
				Arm arm3 = (Arm) track.getRect("arm3");
				arm3.dragToRotate(hitPoint, point);
			}else if(hitId.equals("arm4")){
				Arm arm4 = (Arm) track.getRect("arm4");
				arm4.dragToRotate(hitPoint, point);
			}

			//Update point
			hitPoint = point;
			return true;
		}else{
			return false;
		}
	}
	
	//Cancel the click
	public void handleRelease(Point point){
		hitId = null;
		hitPoint = null;
	}
	
}
