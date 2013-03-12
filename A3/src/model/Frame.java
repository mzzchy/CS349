package model;

import java.awt.Point;
import java.util.ArrayList;

public class Frame {
	/**
	 *  a list of displacement vector, with accumulation correspond to each frame
	 */
	ArrayList<Point> transList = new ArrayList<Point>(0);
	int currentFrame = 0;
	
	public Frame(){
		Point empty = new Point();
		transList.add(empty);
	}
	
	public Frame(int initFrame){
		//????? + 1
		for(int i = 0; i < initFrame+1; i++){
			Point empty = new Point();
			transList.add(empty);
		}
		currentFrame = initFrame+1; //???
	}
	
	public void dragToMove(Point from, Point to, int current){
		if(current > transList.size()-1){ //add new frame
			Point trans = new Point(transList.get(transList.size()-1));
			//Check if it is about overwritten....
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
			transList.add(trans);
		}else{ //overwritten existing frame
			Point trans = transList.get(current);
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		}
	}
	
	public Point getCurrentFrame(){
		currentFrame += 1;
		if(currentFrame > transList.size()){
			currentFrame = transList.size();
		}
		
		return transList.get(currentFrame-1);
	}
	
	public void setCurrentFrame(int i){
		currentFrame = i;
	}
}
