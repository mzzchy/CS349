package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Lasso {
	private Point start;
	private int width;
	private int height;
	private Point trans = new Point();

	public Lasso(Point p){
		start = p;
		width = 1;
		height = 1;
	}
	
	public void setPoint(Point p){
		if(p.getX()< start.getX() || p.getY() < start.getY()){
			start = p;
		}
		width = (int)Math.abs(start.getX()-p.getX());
		height = (int)Math.abs(start.getY()-p.getY());
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
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
		return new Rectangle((int)start.getX(), (int)start.getY(), width, height);
	}
}
