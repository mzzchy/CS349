package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Rectangle extends Object{
	
	String id;
	int x,y,width,height;
	Color color;
	Rectangle nextRect;
	
	public Rectangle(int rX, int rY, int rWidth, int rHeight, String rId){
		x = rX;
		y = rY;
		width = rWidth;
		height = rHeight;
		id = rId;
		nextRect = null;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void addRect(Rectangle rect){
		nextRect = rect;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fillRect(x, y, width, height);
		if(nextRect != null){
			nextRect.paint(g);
		}
	}
	
	//Default is to return false
	public boolean isHitInRectangle(Point point){
		return false;
	}
	
	//Find the hit id
	public String getHitId(Point point){
		if(isHitInRectangle(point)){
			return id;
		}else if(nextRect != null){
			return nextRect.getHitId(point);
		}else{
			return null;
		}
	}
	
	//Find the child based on id name
	public Rectangle getRect(String childId){
		if(id.equals(childId)){
			return this;
		}else if(nextRect != null){
			return nextRect.getRect(childId);
		}else{
			return null;
		}
		
	}
}