package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Graph graph;
	private Point currentPoint;
	private String state = "DRAW"; //draw, erase, select
	private Lasso lasso;
	
	public DrawPanel(){
		super();
		graph = new Graph();
		lasso = null;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
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
//		else if(state == "DRAG"){
//			System.out.print("a");
//		}
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
			//Do hit test find out objects that get selected
			currentPoint = p;
		}
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
			graph.removeStroke(currentPoint,p);
			currentPoint = p;
		}else if(state == "SELECT"){
			lasso.setPoint(p);
		}else if(state == "DRAG"){
			lasso.dragToMove(currentPoint,p);
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
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR ));
			lasso = null;
		}else if(state == "SELECT"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
		}else if(state == "DRAG"){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR ));
		}
		//System.out.print(cmd+"\n");
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	@Override
	public void mouseExited(MouseEvent event) {
	}

	
	
	
}
