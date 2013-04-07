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
//	private ArrayList<Point> pointList;
	Path path = new Path();
	Paint paint = new Paint();
	public Stroke(Node node) {
		//Get point list first
		NodeList nodeList = node.getChildNodes();
		//1 is just PointList
		parsePath(nodeList.item(0));
		//2 is frame
		
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
		
		canvas.drawPath(path, paint);
	}
}
