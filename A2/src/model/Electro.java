package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Electro extends Rectangle{
	
	AffineTransform affine;
	boolean electroOn;
	Color onColor;
	ArrayList <Rectangle> blocks;
	
	public Electro(int rX, int rY, int rWidth, int rHeight, String rId) {
		super(rX, rY, rWidth, rHeight, rId);
		electroOn = false;
		onColor = Color.blue;
		
		blocks = new ArrayList<Rectangle>(0);
		//Add rectangle in it
		Rectangle rect1 = new Rectangle(300,350,100,30,"rect1");
		rect1.setColor(Color.green);
		blocks.add(rect1);
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
		if(electroOn){
			g2.setColor(onColor);
		}else{
			g2.setColor(color);
		}
		affine =  g2.getTransform();
		g2.fillRect(x, y, width, height);
		
		if(nextRect == null && electroOn ){
			for(Rectangle aRect : blocks){
				if(isCloseToRect(aRect) ){
					nextRect = aRect;
					g2.translate(-nextRect.x, -nextRect.y);
					nextRect.paint(g);
					break;
				}
			}
		}else if(nextRect != null){
			if(electroOn){
				g2.translate(-nextRect.x, -nextRect.y);
				nextRect.paint(g);
			}else{
				Shape pad = new Rectangle2D.Double(0,430,600,30);
				affine.translate(-nextRect.x, -nextRect.y);
				Shape aRect = affine.createTransformedShape(new Rectangle2D.Double(nextRect.x,nextRect.y,nextRect.width,nextRect.height ));
				if(pad.intersects(aRect.getBounds2D())){
					Point2D p = new Point2D.Double(nextRect.x,nextRect.y);
					p = affine.transform(p, p);
					//Set the block p
					nextRect.x = (int) p.getX() - nextRect.width;
					nextRect.y = 430- nextRect.height;
				}
				nextRect = null;
			}
			
		}
		
		AffineTransform identity = new AffineTransform();
		g2.setTransform(identity);
		
		//Draw rest rect
		for(Rectangle aRect : blocks){
			if(aRect != nextRect){
				aRect.paint(g);
			}
		}
	}
	
	@Override
	public boolean isHitInRectangle(Point point){
	    Shape shape = affine.createTransformedShape(new Rectangle2D.Double(x,y,width,height));
	    return shape.contains(point.x,point.y);
	}
	
	//Check if rect is close to the elctro pad, add 5 offset to increase hitbox size
	public boolean isCloseToRect(Rectangle rect){
		//+5 off set
		Shape pad = affine.createTransformedShape(new Rectangle2D.Double(x,y,width+5,height+5));
		return pad.intersects(rect.x,rect.y,rect.width,rect.height);
	}
}
