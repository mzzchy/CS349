package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Lasso {

	private Point center = new Point();
	private boolean hasObjectIn = false;
	private ArrayList<Point> pointList;
	private boolean lassoEnd = false;
	private Polygon poly = null;
	private AffineTransform affine = new AffineTransform();
	private double scaleFactor = 1.0;
	
	//Start point
	public Lasso(Point p){
		pointList = new ArrayList<Point>(1);
		pointList.add(p);
	}
	//Add point
	public void addPoint(Point p){
		pointList.add(p);
	}
	
	//Mark the End
	public void setLassoEnd(){
		lassoEnd = true;
		//Go through, find the end point
		double minX = -1;
		double minY = -1;
		double maxX = -1;
		double maxY = -1;
		poly = new Polygon();
		for(Point p : pointList){
			poly.addPoint((int)p.getX(), (int)p.getY());
				
			if(minX > p.getX()) minX = p.getX();
			if(minY > p.getY()) minY = p.getY();
			if(maxX < p.getX()) maxX = p.getX();
			if(maxY < p.getY()) maxY = p.getY();
		}
		
		center.setLocation((minX+maxX)/2, (minY+maxY)/2);
	}
	
	
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
			g2.transform(affine);
		}
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.yellow);
		
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
	
	/** Extrac 3 transformation from Lasso to apply to object
	 * @param from
	 * @param to
	 */
	
	public AffineTransform getTransAffine(Point from, Point to){
		AffineTransform newAffine = new AffineTransform();
		//Add to trans
		if(hasObjectIn){
			newAffine.translate(to.getX()-from.getX(), to.getY()-from.getY());
			affine.concatenate(newAffine);
			
			center.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		}
		return newAffine;
	}
	
	public AffineTransform getRotateAffine(Point from, Point to) {
		AffineTransform newAffine = new AffineTransform();
		if(hasObjectIn){
			if(from.getX()< to.getX() || from.getY()< to.getY()){	
				newAffine.rotate(Math.toRadians(3));
				affine.concatenate(newAffine);
				
			}else if(from.getX()> to.getX() || from.getY()> to.getY()){
				//,center.getX(),center.getY()
				newAffine.rotate(Math.toRadians(-3));
				affine.concatenate(newAffine);
			}
		}
		return newAffine;
	}

	
	public AffineTransform getScaleAffine(Point from, Point to){
		AffineTransform newAffine = new AffineTransform();
		if(hasObjectIn){
			
			if((from.getX()< to.getX() || from.getY()< to.getY())&& scaleFactor < 2.0){
				Point newCenter = new Point(center);
				newCenter.setLocation(center.getX()* 1.05, center.getY()* 1.05);
				
				newAffine.scale(1.05, 1.05);
				newAffine.translate(center.getX()-newCenter.getX(), center.getY()-newCenter.getY());
				affine.concatenate(newAffine);
				
				center = newCenter;
				scaleFactor += 0.05;
				
			}else if((from.getX()> to.getX() || from.getY()> to.getY()) && scaleFactor > 0.5){
				Point newCenter = new Point(center);
				newCenter.setLocation(center.getX()* 0.95, center.getY()* 0.95);
				
				newAffine.scale(0.95, 0.95);
				newAffine.translate(center.getX()-newCenter.getX(), center.getY()-newCenter.getY());
				affine.concatenate(newAffine);
				
				center = newCenter;
				scaleFactor -= 0.05;
			}
		}
		return newAffine;
	}
	
	
	/**
	 * Hit test
	 */
	//TODO: return the bound that is actually get affine transformed
	public boolean isPointInLasso(Point p){
		Shape shape = affine.createTransformedShape(poly);
		return shape.contains(p);
	}
	
	
	public Shape getBound(){
		Shape bound = affine.createTransformedShape(poly);
		return bound;
	}
	
	
}
