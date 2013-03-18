package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Stroke {
	private ArrayList<Point> pointList;
	//Treat point as a points
	private boolean isSelect = false;
	private AffineTransform affine = new AffineTransform();
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
	
	public Stroke(Point p , int current){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
		frame = new Frame(current);
	}
	
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
				g2.transform(affine);
			}else if(state == "PLAY" || state == "VIEW"){
				affine = frame.getCurrentTransform();
				g2.transform(affine);
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
	 * Select and apply transformation
	 */
	public void setSelect(boolean select){
		isSelect = select;
	}

	public void applyAffine(AffineTransform newAffine, int current) {
		if(isSelect){
			affine.concatenate(newAffine);
			frame.applyAffine(newAffine, current);
		}else{
			AffineTransform empty = new AffineTransform();
			frame.applyAffine(empty, current);
		}
	}
	
	/**
	 * Frame
	 */
	
	public void setCurrentFrame(int i){
		frame.setCurrentFrame(i);
	}
	
	public void insertFrame(){
		frame.insertStaticFrame();
	}
	
	
	/**
	 * Hit test for drag to move, need to ask for inver transformation
	 */
	
	
	public boolean isInsidePolygon(Shape bound){
		try {
			AffineTransform inverse =  new AffineTransform(affine);
			inverse.invert();
			Shape shape = inverse.createTransformedShape(bound);
			for(Point p: pointList){
				if(!shape.contains(p)){
					return false;
				}
			}
		} catch (NoninvertibleTransformException e) {
			System.out.print("Inverse fail");
		}
		return true;
		
	}
	
	
	/**
	 * Hit test for erase
	 */
	public boolean isPointOnStroke(Rectangle eraser){
		//Get inverse transform and ask for intersection
		try {
			AffineTransform inverse =  new AffineTransform(affine);
			inverse.invert();
			Shape shape = inverse.createTransformedShape(eraser);
			Rectangle2D bound = shape.getBounds2D();
			for (int i = 0; i < pointList.size()-1; i++) {
			    Line2D line = new Line2D.Double(pointList.get(i), pointList.get(i+1));
			   
			    if(line.intersects(bound)){			    	
			    	return true;
				}
			}
		} catch (NoninvertibleTransformException e) {
			System.out.print("Inverse fail");
		}
		
		return false;
	}
	
	public boolean isLineIntersectStroke(Line2D eraser){ //use just 2 points
		
		try {
			AffineTransform inverse =  new AffineTransform(affine);
			inverse.invert();
			Shape shape = inverse.createTransformedShape(eraser);
			Rectangle2D bound = shape.getBounds2D();
			for (int i = 0; i < pointList.size()-1; i++) {
			    Line2D line = new Line2D.Double(pointList.get(i), pointList.get(i+1));
			    if(line.intersects(bound)){
			    	return true;
			    }
			}
		} catch (NoninvertibleTransformException e) {
			System.out.print("Inverse fail");
		}
		
		return false;
	}

	
}
