package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Lasso {
	private Point start = new Point();
	private int width = 1;
	private int height = 1;
	private Point trans = new Point();

	public Lasso(Point p){
		start.setLocation(p);
	}
	
	public void setPoint(Point p){
		
		width = (int)Math.abs(start.getX()-p.getX());
		height = (int)Math.abs(start.getY()-p.getY());
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(new AffineTransform());
		g2.translate(trans.getX(), trans.getY());
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.yellow);
		g2.drawRect((int)start.getX(), (int)start.getY(), width, height);
	}
	
	
	public void dragToMove(Point from, Point to){
		//Add to trans
		trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
	}
	
	
	
	public boolean isPointInRectangle(Point p){
		Rectangle bound = new Rectangle((int)start.getX()+(int) trans.getX(), (int)start.getY() + (int)trans.getY(), width, height);
		return bound.contains(p);
	}
	
	public Rectangle getBound(){
		return new Rectangle((int)start.getX()+(int) trans.getX(), (int)start.getY() + (int)trans.getY(), width, height);
	}
}
