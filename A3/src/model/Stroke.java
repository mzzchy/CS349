package model;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Stroke {
	ArrayList<Point> pointList;
	//Treat point as a points
	
	public Stroke(){
		pointList = new ArrayList<Point>(0);
	}
	
	public Stroke(Point p){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
	}
	
	void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < pointList.size()-1; i++) {
		    Point p1 = pointList.get(i);
		    Point p2 = pointList.get(i+1);
		    g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		}
		
	}
	void addPoint(Point p){
		 pointList.add(p);
	}
	
	//For erase
	boolean isPointOnStroke(Rectangle eraser){
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
	
	boolean isLineIntersectStroke(Line2D eraser){ //use just 2 points
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
}
