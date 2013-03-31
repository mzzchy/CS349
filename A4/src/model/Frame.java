package model;

import java.awt.Point;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Frame {
	/**
	 *  a list of displacement vector, with accumulation correspond to each frame
	 */
	ArrayList<Point> transList = new ArrayList<Point>(0);
	int currentFrame = 0;
	int startFrame = 0; //the start visible frame
	int endFrame = -1; //-1 as not existing and end
	
	public Frame(){
		Point empty = new Point();
		transList.add(empty);
	}
	
	public Frame(int initFrame){
		//????? + 1
		startFrame = initFrame;
		for(int i = 0; i < initFrame+1; i++){
			Point empty = new Point();
			transList.add(empty);
		}
		currentFrame = initFrame+1; //???
	}
	
	public void dragToMove(Point from, Point to, int current){
		if(current > transList.size()-1){ //add new frame
			Point trans = new Point(transList.get(transList.size()-1));
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
			transList.add(trans);
		}else{ //overwritten existing frame
			Point trans = transList.get(current);
			trans.translate((int)to.getX()-(int)from.getX(), (int)to.getY()-(int)from.getY());
		}
		currentFrame = current;
	}
	
	public void insertStaticFrame(){
		Point empty = transList.get(currentFrame);
		transList.add(currentFrame, empty); //+1???
		currentFrame += 1;
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
	
	public void setEndFrame(int i){ //object is not visible afterward
		endFrame = i;
//		System.out.print(endFrame +'\n');
	}
	
	//Whether currenf Frame
	public boolean isCurrentFrameVisible(){
		return (startFrame<= currentFrame && (endFrame == -1 ||currentFrame < endFrame));
	}
	
	/**
	 * XML
	 */
	
	public Element createElement(Document doc) {
		Element frameNode = doc.createElement("frame");
		//Start
		Element startNode = doc.createElement("start");
		startNode.appendChild(doc.createTextNode(""+startFrame));
		frameNode.appendChild(startNode);
		//End
		Element endNode = doc.createElement("end");
		endNode.appendChild(doc.createTextNode(""+endFrame));
		frameNode.appendChild(endNode);
		//Trans List
		Element transListNode = doc.createElement("transList");
		frameNode.appendChild(transListNode);
		for(Point p: transList){
			Element pointNode = doc.createElement("point");
			//TODO:
			pointNode.appendChild(doc.createTextNode(""+p.x+" "+p.y));
			transListNode.appendChild(pointNode);
		}
		
		return frameNode;
	}
	
	
}
