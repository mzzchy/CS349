package com.example.ksmobile;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AnimationView extends View {
//	Stroke stroke = null;
	ArrayList<Stroke> animation = new ArrayList<Stroke>(0);
	static int backgroundColor = Color.WHITE;
	public AnimationView(Context context) {
		super(context);
	}
	
	public AnimationView(Context context, AttributeSet attrs){
		super( context,  attrs); 
	}
	
	public AnimationView(Context context, AttributeSet attrs, int defStyle){
		super( context,  attrs,  defStyle); 
	}
	//Works
	public void loadXMLFile(String fileName){
		Log.e("fileName",fileName);
		File xmlFile = new File(Environment.getExternalStorageDirectory().toString() + "/"+fileName);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();
			Log.e("Node",doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("stroke");
			
			//restart an animation, currently does not care about perisistence
			animation.clear();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Stroke stroke = new Stroke( nodeList.item(i));
				animation.add(stroke);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		} catch (SAXException e) {
//			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
//		setBackgroundColor(backgroundColor);
		if(animation != null &&animation.size()> 0){
			for(Stroke s: animation){
				s.onDraw(canvas);
			}
		}
	}
	

	public boolean isAnimationDone() {
		for(Stroke s: animation){
			if(!s.isStrokeDone()){
				return false;
			}
		}
		return true;
	}


	public boolean hasLoadXML() {
		return !animation.isEmpty();
	}

	public void resetFrame() {
		for(Stroke s: animation){
			s.resetCurrentFrame();
		}
	}
	
	public int getAnimationLength(){
		int max = -1;
		for(Stroke s: animation){
			max = Math.max(max, s.getFrameLength());
		}
		return max;
	}

	public void setCurrentAnimation(int current) {
		if(hasLoadXML()){
			for(Stroke s: animation){
			s.setCurrentFrame(current);
			}
		}
	}

}
