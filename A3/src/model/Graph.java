package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

//Store basic pixel info
public class Graph {
	
	ArrayList<Stroke> strokeList;
	Stroke currentStroke = null;
	public Graph(){
		strokeList = new ArrayList<Stroke>(0);
	}
	
	public void draw(Graphics g){
		for(Stroke s: strokeList){
			s.draw(g);
		}
	}
	
	public void startStroke(Point p){
		currentStroke = new Stroke(p);
		strokeList.add(currentStroke);
	}
	
	public void continueStroke(Point p){
		currentStroke.addPoint(p);
	}
	
	public void endStroke(Point p){
		currentStroke.addPoint(p);
	}
	
	public void removeStroke(Point p){
		Rectangle eraser = new Rectangle((int)p.getX(), (int) p.getY(),15,15);
		for (int i = 0; i < strokeList.size(); i++) {
			Stroke s = strokeList.get(i);
			if(s.isPointOnStroke(eraser)){
				strokeList.remove(i);
				break;
			}
		}
	}
	
	public void removeStroke(Point p1, Point p2){
		Line2D eraser = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		for (int i = 0; i < strokeList.size(); i++) {
			Stroke s = strokeList.get(i);
			if(s.isLineIntersectStroke(eraser)){
				strokeList.remove(i);
				break;
			}
		}
	}
	
}
