package com.example.ksmobile;

import java.io.IOException;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
		
		AssetManager am = getContext().getAssets();
		try {
			InputStream is = am.open("KSketch/"+fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			Log.e("Node",doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("stroke");
//			NodeList nodeList = doc.getChildNodes();
			
			//restart an animation, currently does not care about perisistence
			animation.clear();
//			animation = new ArrayList<Stroke>(0);
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
		setBackgroundColor(backgroundColor);
		if(animation != null &&animation.size()> 0){
			for(Stroke s: animation){
				s.onDraw(canvas);
			}
		}
	}
	
//	public static void setRGB(int rgb){
//		setBackgroundColor(rgb);
//		invalidate();
//	}

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

}
