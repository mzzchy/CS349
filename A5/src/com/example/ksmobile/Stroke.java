package com.example.ksmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Stroke {
	
	Path path = new Path();
	Paint paint = new Paint();
	Frame frame = null;
	
	private class Frame{
		private int startFrame = 0;
		private int endFrame = -1;
		private int currentFrame = 0;
		private ArrayList<Point> transList = new ArrayList<Point>(0);
		
		Frame(Node node){
			NodeList nodeList = node.getChildNodes();
			//start
			startFrame = parseInt(nodeList.item(0));
//			currentFrame = startFrame;
			//end
			endFrame = parseInt(nodeList.item(1));
			//translist
			paresTrans(nodeList.item(2));
			
		}
		
		private int parseInt(Node node){
			NodeList nodeList = node.getChildNodes();
			String s= ((Node) nodeList.item(0)).getNodeValue();
			return Integer.parseInt(s);
		}
		
		private void paresTrans(Node node){
			NodeList nodeList = node.getChildNodes();
			for(int i = 0;i<nodeList.getLength();i++){
				transList.add( parsePoint(nodeList.item(i)));
			}
		}
		
		private Point parsePoint(Node node){
			   NodeList nodeList = node.getChildNodes();
//			      Log.e("x and y",((Node) nodeList.item(0)).getNodeValue());
				String [] s= ((Node) nodeList.item(0)).getNodeValue().split(" ");
				Point p =new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
//				try{
//					p = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
//				}catch(Exception e){
//					p = new Point();
//					Log.e("X and Y",s[0]+" "+s[1]);
//				}
				return p;
				
		}
		
		boolean isFrameVisible(){
			if(endFrame == -1 ){
				return (startFrame<= currentFrame); 
			}else{
				return (startFrame<= currentFrame && currentFrame < endFrame);
			}
		}
		
		Point getCurrentFrame(){
			if(currentFrame == transList.size()){
				return transList.get(currentFrame-1);
			}else
				return transList.get(currentFrame++);
		}
		
		
		int getFrameCount(){
			return transList.size();
		}

		public boolean isFrameDone() {
			return (currentFrame >= transList.size());
		}
		
	}
	
	
	public Stroke(Node node) {
		//Get point list first
		NodeList nodeList = node.getChildNodes();
		//1 is just PointList
		parsePath(nodeList.item(0));
		//2 is frame
		frame = new Frame(nodeList.item(1));
		
		//Do other settings
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
	}
	

	private void parsePath(Node node){
		NodeList nodeList = node.getChildNodes();
		Point start = parsePoint(nodeList.item(0));
		path.moveTo(start.x, start.y);
		
		for(int i = 1;i<nodeList.getLength();i++){
			Point next = parsePoint(nodeList.item(i));
			path.lineTo(next.x, next.y);
		}
		
	}
	
	private Point parsePoint(Node node){
	   NodeList nodeList = node.getChildNodes();
//	      Log.e("x and y",((Node) nodeList.item(0)).getNodeValue());
		String [] s= ((Node) nodeList.item(0)).getNodeValue().split(" ");
		Point p = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		return p;
		
	}
	
	public void onDraw(Canvas canvas){
		if(frame.isFrameVisible()){
			Point trans = frame.getCurrentFrame();
			canvas.translate(trans.x, trans.y);
			canvas.drawPath(path, paint);
			canvas.translate(-trans.x, -trans.y);
		}
	}


	public boolean isStrokeDone() {
		return frame.isFrameDone();
	}


	public void resetCurrentFrame() {
		frame.currentFrame = 0;
	}
	
	public int getFrameLength(){
		return frame.transList.size();
	}


	public void setCurrentFrame(int current) {
		current = Math.min(frame.transList.size()-1, current);
		frame.currentFrame = current;
	}
}
