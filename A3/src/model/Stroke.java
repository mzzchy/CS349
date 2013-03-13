package model;

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
	private String state = "PAUSE"; //play and pause
	private Frame frame;
	public Stroke(){
		pointList = new ArrayList<Point>(0);
		frame = new Frame();
	}
	
	public Stroke(Point p){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
		frame = new Frame();
	}
	//So there should be 2 mode, static mode and move mode
	
	/**
	 * Draw
	 */
	public void draw(Graphics g){
		if(frame.isCurrentFrameVisible()){
			Graphics2D g2 = (Graphics2D) g;
			//Reset affine
			g2.setTransform(new AffineTransform());
			//If object is visible
		
			if(state == "PAUSE"){
				g2.translate(trans.getX(), trans.getY());
			}else if(state == "PLAY" || state == "VIEW"){
				trans = frame.getCurrentFrame();
				g2.translate(trans.getX(), trans.getY());
				//Get transform from frame factory
			}
			//Send message to frame
	        for (int i = 0; i < pointList.size()-1; i++) {
			    Point p1 = pointList.get(i);
			    Point p2 = pointList.get(i+1);
			    g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
			}
		}
		
	}
	/**
	 * Erased at that frame
	 * @param current
	 */
	public void setErased(int current){
		frame.setEndFrame(current);
	}
	
	
	
	public void setComman(String cmd){
		state = cmd;
	}
	
	void addPoint(Point p){
		pointList.add(p);
	}
	
	
	/**
	 * Select and drag to move
	 */
	public void setSelect(boolean select){
		isSelect = select;
	}

	//If select, put actual trans, else, push empty trans
	public void dragToMove(Point from, Point to, int current){
		if(isSelect){
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
			frame.dragToMove(from, to, current);
		}else{
			Point p = new Point();
			frame.dragToMove(p, p, current);
		}
	}
	
	/**
	 * Frame
	 */
	
	public void setCurrentFrame(int i){
		frame.setCurrentFrame(i);
	}
	
	/**
	 * Hit test for drag to move
	 */
	public boolean isInsideRectangle(Rectangle bound ){
		for (int i = 0; i < pointList.size(); i++) {
			//Translate point to current position
		    Point p = new Point(pointList.get(i));
		    p.translate((int)trans.getX(), (int)trans.getY());
		    
		    if(!bound.contains(p)){
		    	return false;
		    }
		}
		
		return true;
	}
	
	/**
	 * Hit test for erase
	 */
	public boolean isPointOnStroke(Rectangle eraser){
		for (int i = 0; i < pointList.size()-1; i++) {
			//Translate point to current position
			Point p1 = new Point( pointList.get(i));
		    Point p2 = new Point( pointList.get(i+1));
		    p1.translate((int)trans.getX(), (int)trans.getY());
		    p2.translate((int)trans.getX(), (int)trans.getY());
		    Line2D line = new Line2D.Double(p1, p2);
		    
		    if(line.intersects(eraser)){			    	
		    	return true;
			}
		}
		return false;
	}
	
	public boolean isLineIntersectStroke(Line2D eraser){ //use just 2 points
		for (int i = 0; i < pointList.size()-1; i++) {
			//Translate point to current position
			Point p1 = new Point( pointList.get(i));
		    Point p2 = new Point( pointList.get(i+1));
		    p1.translate((int)trans.getX(), (int)trans.getY());
		    p2.translate((int)trans.getX(), (int)trans.getY());
		    Line2D line = new Line2D.Double(p1, p2);
		    
		    if(line.intersectsLine(eraser)){
		    	return true;
		    }
		}
		
		return false;
	}
}
