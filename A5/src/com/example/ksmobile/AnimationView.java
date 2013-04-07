package com.example.ksmobile;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	Stroke stroke = null;
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
			stroke = new Stroke(nodeList.item(0));
			
//			for (int i = 0; i < nodeList.getLength(); i++) {
//				Node fstNode = nodeList.item(i);
//				Stroke stroke = new Stroke(fstNode);
//		
//			}
		} catch (IOException e) {
//			e.printStackTrace();
		} catch (SAXException e) {
//			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		
		Path path = new Path();
		path.moveTo(50, 50);
		for(int i = 0; i < 5;i +=1){
			path.lineTo(50+i*10, 50+i*15);
		}
		canvas.drawPath(path, paint);
		
		if(stroke != null){
			Log.e("Draw","DID DRAW");
			stroke.onDraw(canvas);
		}
	}

}
