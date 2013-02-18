package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Arm extends Rectangle{
	int angle;
	Point2D anchor;
	AffineTransform affine;
//	boolean isDragging;
//	
	public Arm(int rX, int rY, int rWidth, int rHeight, String rId) {
		super(rX, rY, rWidth, rHeight, rId);
		anchor = new Point2D.Double(0,0);
//		isDragging = false;
	}
	
	public AffineTransform getAffineTransform(){
		return affine;
	}
	/*
	public void setAffineTransform(AffineTransform transform){
		affine.concatenate(transform);
		//transform.transform(anchor, anchor);
		if(nextRect != null && nextRect instanceof Arm){ //Avoid electro for now
			Arm nextArm = (Arm) nextRect;
			nextArm.setAffineTransform(transform);
		}
	}
	*/
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.rotate(Math.toRadians(angle), anchor.getX(), anchor.getY());
		affine =  g2.getTransform();
		g2.fillRect(x, y, width, height);
		if(nextRect != null){
			nextRect.paint(g);
		}
	}
	
	@Override
	public boolean isHitInRectangle(Point point){
	    Shape shape = affine.createTransformedShape(new Rectangle2D.Double(x,y,width,height));
	    return shape.contains(point.x,point.y);
	}
	
	//Set anchor point
	public void setAnchor(int rAngle, int onX, int onY){
		angle = rAngle;
		anchor.setLocation(onX, onY);
		//affine = AffineTransform.getRotateInstance(Math.toRadians(angle), anchor.getX(), anchor.getY());
	}
	
	public void dragToRotate(Point start, Point end){
		int clockwise = -2;
		Point2D anchorP = new Point2D.Double();
		anchorP = affine.transform(anchor, anchorP);
		//This seems to be working
//		System.out.print(start+""+end+""+anchorP+'\n');
		if(start.x > anchorP.getX() && end.x > anchorP.getX() && start.y < end.y){
			clockwise = 2;
		}else if(start.x < anchorP.getX() && end.x < anchorP.getX() && start.y > end.y){
			clockwise = 2;
		}else if(start.y < anchorP.getY() && end.x < anchorP.getY() && start.x < end.x){
			clockwise = 2;
		}else if(start.y > anchorP.getY() && end.x > anchorP.getY() && start.x > end.x){
			clockwise = 2;
		}
		
		angle += clockwise;
		//setAffineTransform(AffineTransform.getRotateInstance(Math.toRadians(clockwise), anchor.getX(), anchor.getY()));
	}
	
	//Since track move, then anchor point should also move
	/*	public void setTranslate(AffineTransform transform){
			anchor =  transform.transform(anchor, null);
			affine.concatenate(transform);
			if(nextRect != null && nextRect instanceof Arm){ //Avoid electro for now
				Arm nextArm = (Arm) nextRect;
				nextArm.setTranslate(transform);
			}
		}*/
	
}
