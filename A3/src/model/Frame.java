package model;

import java.awt.Point;
import java.util.ArrayList;

public class Frame {
	/**
	 *  a list of displacement vector, with accumulation
	 */
	ArrayList<Point> transList = new ArrayList<Point>(0);
	int nextFrame = 0;
	
	public Frame(){
		Point start = new Point();
		transList.add(start);
	}
	
	public void dragToMove(Point from, Point to){
		Point trans = new Point(transList.get(transList.size()-1));
		trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		transList.add(trans);
	}
	
	public Point getCurrentFrame(){
		nextFrame += 1;
		if(nextFrame > transList.size()){
			nextFrame = transList.size();
		}
		
		return transList.get(nextFrame-1);
	}
}
