package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	 * Create/Overwritten/Insert frame
	 */
	
	public void setCurrentFrame(int i) {
		for(Stroke s: strokeList){
			s.setCurrentFrame(i);
		}
		currentFrame = i;
		
	}
	
	public void insertFrame(){
		for(Stroke s: strokeList){
			s.insertFrame();
		}
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
	public void setSelectedStroke(Lasso lasso ){
		Polygon bound = lasso.getBound();
		for(Stroke s: strokeList){
			if(s.isInsidePolygon(bound)){
				s.setSelect(true);
				lasso.setObjectIn(true);
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

	public void deSelectAll(Lasso lasso){
		for(Stroke s: strokeList){
			s.setSelect(false);
		}
//		lasso.setObjectIn(false);
	}
	
	/**
	 * Save in XML
	 * @param fileName
	 * @throws ParserConfigurationException 
	 */

	public void saveAs(String fileName) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		//Creat root element
		Document doc = docBuilder.newDocument();
		doc.appendChild(createElement(doc));
		
		//Save in fileName
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName+".xml"));
//			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			System.out.print("Init transform error");
		} catch (TransformerException e) {
			System.out.print("Transform parse error");
		}
		
		
		System.out.println("File saved!");
	}
	
	public Element createElement(Document doc){
		Element rootElement = doc.createElement("KSketch");
		
		for(Stroke s: strokeList){
			rootElement.appendChild(s.createElement(doc));
		}
		
		return rootElement;
	}
}
