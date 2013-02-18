package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

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
		return (point.x>=x+trans.x && point.x <= x+trans.x+width && point.y>=y && point.y<=y+height);
	}
	
	public void dragToMove(boolean right){
		if(right){
			trans.x += 1;
		}else{
			trans.x -= 1;
		}
	}
	
}