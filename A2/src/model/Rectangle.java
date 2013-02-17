package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


public class Rectangle extends Object{
	
	String id;
	int x,y,width,height;
	Color color;
	int transX = 0 ;
	int transY = 0;
	double theta = 0.0;
	int anchorX = 0;
	int anchorY = 0;
	double angle = 0.0;
	
	ArrayList<Rectangle> childList = new ArrayList<Rectangle>(0);
	
	public Rectangle(int rX, int rY, int rWidth, int rHeight, String rId){
		x = rX;
		y = rY;
		width = rWidth;
		height = rHeight;
		id = rId;
		//set a default anchor based on that
		anchorX = x;
		anchorY = y;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void paintRectangle(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		//g2.translate(moveX, moveY);
		g2.translate(transX, transY);
		g2.rotate(theta, anchorX, anchorY);
		g2.fillRect(x, y, width, height);
		
		for(Rectangle r : childList){
			r.paintRectangle(g);
		}
	}
	
	//Do inverse rotate on point
	public boolean isHitInRectangle(Point point){
		//Do inverse rotate  and translate for point
		if(theta != 0){
			point.translate(anchorX, anchorY);
			//rotate -theta, or angle?
			double cos = Math.cos(-angle);
			double sin = Math.sin(-angle);
			
			double newX = point.x*(cos) - point.y*sin;
			double newY = point.x*sin + point.y*cos;
			point.x = (int) newX;
			point.y = (int) newY;
			
			point.translate(-anchorX, -anchorY);
		}
		
		point.translate(-transX, -transY);
		//Movement
		//point.translate(-moveX, -moveY);
		//Bounding box check
		return (point.x>=x && point.x <= x+width && point.y>=y && point.y<=y+height);
	}
	
	//Check if itself get hits, it not, ask its children
	//If no children ,then return null;
	public String getHitId(Point point){
		if(isHitInRectangle(point)){
			return id;
		}/*else{
			return null;
		}
		*/else{
			for(Rectangle aRect : childList){
				//Make a new copy of p that is already translated
				Point p = new Point(point);
				String hitId = aRect.getHitId(p);
				if(hitId != null){
					return hitId;
				}
			}
			return null;
		}
	}
	
	public void addChild(Rectangle child){
		childList.add(child);
	}
	
	public void setTranslate(int x, int y){
		transX = x;
		transY = y;
	}
	
	public void setRotate(double rAngle, int onX, int onY){
		angle = rAngle;
		theta = Math.PI*angle/180;
		anchorX = onX;
		anchorY = onY;
	}
	
	//int moveX = 0;
	//int moveY = 0;
	
	public void setMovement(int x, int y){
		transX += x;
		//moveX += x;
		//moveY += y;
	}
	
}
