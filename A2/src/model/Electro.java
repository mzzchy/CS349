package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Electro extends Rectangle{
	
	AffineTransform affine;
	boolean electroOn;
	
	public Electro(int rX, int rY, int rWidth, int rHeight, String rId) {
		super(rX, rY, rWidth, rHeight, rId);
		electroOn = false;
	}

	public void setElectro(boolean on){
		electroOn = on;
	}
	
	public void setElectro(){
		electroOn = !electroOn;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
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
	
	
	public boolean isCloseToRect(Rectangle rect){
		
		
		return true;
	}
}
