package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

//Store basic pixel info
public class Stroke {
	Set <Point>pointList; //set can avoid duplicate point
	
	public Stroke(){
		pointList = new HashSet<Point>();
	}
	
	public void addPoint(Point p){
		pointList.add(p);
	}
	
	public void removePoint(Point p){ //Need a more general p
		pointList.remove(p);
	}
	
	public Set<Point> getPoints(){
		return pointList;
	}
}
