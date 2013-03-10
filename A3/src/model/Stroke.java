package model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Stroke {
	private ArrayList<Point> pointList;
	//Treat point as a points
	private boolean isSelect = false;
	private Point trans = new Point();
	
	public Stroke(){
		pointList = new ArrayList<Point>(0);
	}
	
	public Stroke(Point p){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
	}
	
	
	/**
	 * Draw
	 */
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		//Reset affine
		g2.setTransform(new AffineTransform());
		g2.translate(trans.getX(), trans.getY());
		
        for (int i = 0; i < pointList.size()-1; i++) {
		    Point p1 = pointList.get(i);
		    Point p2 = pointList.get(i+1);
		    g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		}
	}
	
	void addPoint(Point p){
		pointList.add(p);
	}
	
	/**
	 * Hit test for erase
	 */
	public boolean isPointOnStroke(Rectangle eraser){
		for (int i = 0; i < pointList.size()-1; i++) {
			Point p1 = pointList.get(i);
		    Point p2 = pointList.get(i+1);
		    Line2D line = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		    if(line.intersects(eraser)){			    	
		    	return true;
			}
		}
		return false;
	}
	
	public boolean isLineIntersectStroke(Line2D eraser){ //use just 2 points
		for (int i = 0; i < pointList.size()-1; i++) {
		    Point p1 = pointList.get(i);
		    Point p2 = pointList.get(i+1);
		    Line2D line = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		    if(line.intersectsLine(eraser)){
		    	return true;
		    }
		}
		
		return false;
	}
	
	/**
	 * Select and drag to move
	 */
	public void setSelect(boolean select){
		isSelect = select;
	}

	public void dragToMove(Point from, Point to){
		if(isSelect){
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		}
	}
	/**
	 * Hit test for drag to move
	 */
	public boolean isInsideRectangle(Rectangle bound ){
		for (int i = 0; i < pointList.size(); i++) {
		    Point p1 = pointList.get(i);
		    if(!bound.contains(p1)){
		    	return false;
		    }
		}
		
		return true;
	}
}
