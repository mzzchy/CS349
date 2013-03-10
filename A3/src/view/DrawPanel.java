package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import model.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Graph graph;
	private Point currentPoint;
	private String state = "DRAW"; //draw, erase, select, drag
	private Lasso lasso;
	private long startTime = 0;
	private long elpasedTime = 0;
	private Timer timer;
	private AnimePanel animeLink;
	private static final long  PER_FRAME_TIME = 100;
	public DrawPanel(){
		super();
		graph = new Graph();
		lasso = null;
		
		timer = new Timer(30, this); //delay is in milisecond
		timer.setInitialDelay(30);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
	}
	
	public void addActionLink(AnimePanel link){
		animeLink = link;
	}
	
	/**
	 * Draw 
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 600, 480);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3));
		graph.draw(g);
		if(lasso != null){
			lasso.draw(g);
		}
		g2.setTransform(new AffineTransform());
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
			graph.endStroke(p);
		}else if(state == "ERASE"){
			graph.removeStroke(p);
		}else if(state == "SELECT"){
			lasso = null;
		}
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent event) {

		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
		}else if(state == "ERASE"){
			currentPoint = p;
		}else if(state == "SELECT"){
			lasso = new Lasso(p);
		}else if(state == "DRAG"){
			//Pass in the current lasso area and select self
			//Do hit test find out objects that get selected
			currentPoint = p;
			graph.setSelectedStroke(lasso.getBound());
		}
		
		startTime = System.currentTimeMillis();
		
		repaint();
	}
		
	@Override
	public void mouseDragged(MouseEvent event) {

		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.continueStroke(p);
		}else if(state == "ERASE"){
			graph.removeStroke(currentPoint,p);
			currentPoint = p;
		}else if(state == "SELECT"){
			lasso.setPoint(p);
		}else if(state == "DRAG"){
			//Drag to move
			lasso.dragToMove(currentPoint,p);
			graph.dragToMove(currentPoint,p);
			elpasedTime += System.currentTimeMillis() - startTime;
			if(elpasedTime > PER_FRAME_TIME){ //Pass one sec
				animeLink.moveTimeForward();
				elpasedTime = 0;
			}
			startTime = System.currentTimeMillis();
			currentPoint = p;
		}
		
		repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.endStroke(p);
		}else if(state == "ERASE"){
			currentPoint = p;
		}else if(state == "SELECT"){
			lasso.setPoint(p);
		}else if(state == "DRAG"){
			graph.deSelectAll();
			currentPoint = p;
		}
		repaint();
	}
	
	//Override for drag and select
	@Override
	public void mouseMoved(MouseEvent event) {
		Point p = event.getPoint();
		if(state == "SELECT" && lasso != null &&lasso.isPointInRectangle(p)){
			setCommand("DRAG");
		}else if(state == "DRAG" &&lasso != null && !lasso.isPointInRectangle(p)){
			setCommand("SELECT");
		}
	}
	
	public void setCommand(String cmd){
		state = cmd;
		if(state == "DRAW"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
			lasso = null;
		}else if(state == "ERASE"){
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR ));
			lasso = null;
		}else if(state == "SELECT"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
		}else if(state == "DRAG"){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR ));
		}else if(state == "PLAY"){
			//Set the graph to play mode
			timer.start();
			graph.setCommand("PLAY");
		}
		//System.out.print(cmd+"\n");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
//		System.out.print("a");
		if(graph.isAnimationDone()){
//			System.out.print("\na");
			timer.stop();
			graph.setCommand("PAUSE");
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	@Override
	public void mouseExited(MouseEvent event) {
	}
	
}
