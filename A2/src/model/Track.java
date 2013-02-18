package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Track extends Rectangle{
	
	Point trans;
	
	public Track(int rX, int rY, int rWidth, int rHeight, String rId) {
		super(rX, rY, rWidth, rHeight, rId);
		trans = new Point(0,0);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.translate(trans.x, trans.y);
		g2.fillRect(x, y, width, height);
		if(nextRect != null){
			nextRect.paint(g);
		}
	}
	
	@Override
	public boolean isHitInRectangle(Point point){
		return (point.x>=x && point.x <= x+width && point.y>=y && point.y<=y+height);
	}
	
	public void dragToMove(boolean right){
		int diff = -1;
		if(right){
			diff = 1;
		}
		trans.x += diff;
		
		//Apply translate to anchor point and affine as well?
		//Arm arm1 = (Arm) getRect("arm1");
		//AffineTransform transform = AffineTransform.getTranslateInstance(diff, 0);
		//arm1.setAffineTransform(transform);
	}
}
