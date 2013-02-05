package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


public class Rectangle extends Object{
	
	int x,y,width,height;
	Color color;
	int transX, transY = 0;
	double theta = 0.0;
	int anchorX, anchorY = 0;
	
	ArrayList<Rectangle> childList = new ArrayList<Rectangle>(0);
	
	public Rectangle(int rX, int rY, int rWidth, int rHeight){
		x = rX;
		y = rY;
		width = rWidth;
		height = rHeight;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void paintRectangle(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.translate(transX, transY);
		g2.rotate(theta, anchorX, anchorY);
		g2.fillRect(x, y, width, height);
		
		for(Rectangle r : childList){
			r.paintRectangle(g);
		}
	}
	
	public boolean isHitInRectangle(Point p){
		//Easy way, perform reverse translation
		//Then check using the easy bound
		return true;
	}
	
	public void addChild(Rectangle child){
		childList.add(child);
	}
	
	public void setTranslate(int x, int y){
		transX = x;
		transY = y;
	}
	
	public void setRotate(double angle, int onX, int onY){
		theta = Math.PI*angle/180;
		anchorX = onX;
		anchorY = onY;
	}
}
