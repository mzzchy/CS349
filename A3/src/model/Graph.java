package model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
		//If p is in stroke, then remove
		//must use iterator
//		for(Stroke s: strokeList){
//			if(s.isPointOnStroke(p)){
//				//Remove self?, 
//			}
//		}
	}
	
}
