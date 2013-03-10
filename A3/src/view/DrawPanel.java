package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Graph;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Graph graph;
	Point currentPoint;
	String state = "DRAW"; //draw, erase, select
	
	public DrawPanel(){
		super();
		setBorder(BorderFactory.createLineBorder(Color.black));
		graph = new Graph();
		addMouseListener(this);
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
	}
	//Draw 
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 600, 400);
		g2.setColor(Color.black);
		graph.draw(g);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
			graph.endStroke(p);
		}else if(state == "ERASE"){
			graph.removeStroke(p);
		}
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		// TODO state
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
		}else if(state == "ERASE"){
			currentPoint = p;
			//graph.removeStroke(p);
		}
		repaint();
	}
		
	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.continueStroke(p);
		}else if(state == "ERASE"){
			graph.removeStroke(currentPoint,p);
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
		}
		repaint();
	}
	
	
	public void setCommand(String cmd){
		state = cmd;
		if(state == "DRAW" || state == "SELECT"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
		}else if(state == "ERASE"){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR ));
		}else if(state == "DRAG"){
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR ));
		}
		//System.out.print(cmd+"\n");
	}
	
	//Override for drag and select
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	@Override
	public void mouseExited(MouseEvent event) {
	}
	//Ignore for now
	@Override
	public void mouseMoved(MouseEvent event) {
	}
	
	
}
