package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

//Store basic pixel info
public class Animation {
	
	ArrayList<Stroke> strokeList;
	Stroke currentStroke = null;
	
	String state = "PAUSE"; //or play and view
	int currentFrame = 0;
	int MAX_FRAME_COUNT = 0;
	public Animation(){
		strokeList = new ArrayList<Stroke>(0);
	}
	
	public void draw(Graphics g){
		for(Stroke s: strokeList){
			s.draw(g);
		}
		//How to judge frameTime?
		//When everyone has nothing to draw
		//everyone is at the last frame
		if(state == "PLAY"){
			currentFrame += 1;
		}
	}
	/**
	 * Getter and setter for state
	 */
	public void setCommand(String cmd){
		state = cmd;
		for(Stroke s: strokeList){
			s.setComman(cmd);
		}
	}
	
	public String getState() {
		return state;
	}

	/**
	 * Create/Overwritten frame
	 */
	
	public void setCurrentFrame(int i) {
		for(Stroke s: strokeList){
			s.setCurrentFrame(i);
		}
		currentFrame = i;
		
	}
	
	/**
	 * Animation play
	 */
	public boolean isAnimationDone(){
		return (currentFrame > MAX_FRAME_COUNT);
	}

	
	/**
	 * How to set a stroke
	 * 
	 */
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
	
	/**
	 * How to remove a stroke
	 * Either intersect with mouse, or intersect with mouse motion
	 * If erasing , set it as invisible
	 */

	//Enhancement
	//TODO: test if we should erase if instead of keeping it?
	//TODO: if do so, then we need to change frame file to use startFrame as an achor point
	public void removeStroke(Point p){
		Rectangle eraser = new Rectangle((int)p.getX(), (int) p.getY(),15,15);
		for(Stroke s: strokeList){
			if(s.isPointOnStroke(eraser)){
				s.setErased(currentFrame);
			}
		}
		
	}
	
	public void removeStroke(Point p1, Point p2){
		Line2D eraser = new Line2D.Double(p1, p2);
		for(Stroke s: strokeList){
			if(s.isLineIntersectStroke(eraser)){
				s.setErased(currentFrame);
			}
		}
	}
	
	
	/**
	 * Drag and select
	 */
	public void setSelectedStroke(Rectangle bound ){
		for(Stroke s: strokeList){
			if(s.isInsideRectangle(bound)){
				s.setSelect(true);
			}
		}
	}
	
	public void dragToMove(Point from, Point to, int current){
		for(Stroke s: strokeList){
			s.dragToMove(from, to, current);
		}
		if(current > MAX_FRAME_COUNT){
			MAX_FRAME_COUNT = current;	
		}
		currentFrame = current;
	}
	
	public void deSelectAll(){
		for(Stroke s: strokeList){
			s.setSelect(false);
		}
	}

	
}
