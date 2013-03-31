package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
//import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Lasso {
	private Point start = new Point();
//	private int width = 0;
//	private int height = 0;
	private Point trans = new Point();
	private boolean hasObjectIn = false;
	private ArrayList<Point> pointList;
	private boolean lassoEnd = false;
	private Polygon poly = null;
	//Start point
	public Lasso(Point p){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
		start.setLocation(p);
	}
	//Add point
	public void addPoint(Point p){
		pointList.add(p);
	}
	
	//Mark the End
	public void setLassoEnd(){
		lassoEnd = true;
		//Go through, find the end point
		poly = new Polygon();
		for(Point p : pointList){
			poly.addPoint((int)p.getX(), (int)p.getY());
		}
//	Fake Rectangle	
//		double minX = -1;
//		double minY = -1;
//		double maxX = -1;
//		double maxY = -1;
//		
//		for(Point p : pointList){
//			if(minX > p.getX()) minX = p.getX();
//			if(minY > p.getY()) minY = p.getY();
//			if(maxX <p.getX()) maxX = p.getX();
//			if(maxY < p.getY()) maxY = p.getY();
//		}
//		
//		//Create a rectangle bound based on that
//		start.setLocation(minX, minY);
//		width = (int)(maxX- minX);
//		height = (int)(maxY- minY);
	}
	
	
//	public void setPoint(Point p){
//		
//		width = (int)Math.abs(start.getX()-p.getX());
//		height = (int)Math.abs(start.getY()-p.getY());
//	}
	/**
	 * Whether there is object in lasso
	 * @param has
	 */
	public void setObjectIn(boolean has){
		hasObjectIn = has;
	}
	
	public boolean hasObject(){
		return hasObjectIn;
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(new AffineTransform());
		if(hasObjectIn){
			g2.translate(trans.getX(), trans.getY());
		}
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.yellow);
		
//		g2.drawRect((int)start.getX(), (int)start.getY(), width, height);
		for (int i = 0; i < pointList.size()-1; i++) {
			Point p1 = pointList.get(i);
			Point p2 = pointList.get(i+1);
			g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		}
		if(lassoEnd){ //Conect 1st and last
			Point p1 = pointList.get(0);
			Point p2 = pointList.get(pointList.size()-1);
			g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		}
		
	}
	
	
	public void dragToMove(Point from, Point to){
		//Add to trans
		if(hasObjectIn){
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		}
	}
	
	/**
	 * Hit test
	 */
	
	public boolean isPointInLasso(Point p){
//		Rectangle bound = new Rectangle((int)start.getX()+(int) trans.getX(), (int)start.getY() + (int)trans.getY(), width, height);
		return poly.contains(p);
	}
	
//	public Rectangle getBound(){
//		//Return a fake bound
//		return new Rectangle((int)start.getX()+(int) trans.getX(), (int)start.getY() + (int)trans.getY(), width, height);
//	}
	
	public Polygon getBound(){
		return poly;
	}
	
}
