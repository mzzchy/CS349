package view;

import java.awt.Color;
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
	String state = "DRAW"; //draw, erase, select
	
	public DrawPanel(){
		super();
		setBorder(BorderFactory.createLineBorder(Color.black));
		graph = new Graph();
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	//Draw arear
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 600, 400);
		g2.setColor(Color.black);
		graph.draw(g);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		// TODO state
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
		}else if(state == "ERASE"){
//			stroke.removePoint(p);
		}
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO state
		//add point
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.startStroke(p);
			graph.endStroke(p);
		}else if(state == "ERASE"){
//			stroke.removePoint(p);
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
//			stroke.removePoint(p);
		}
		
		repaint();
	}
	
	
	
	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Clear state
		Point p = event.getPoint();
		if(state == "DRAW"){
			graph.endStroke(p);
		}
	}
	
	
	public void setCommand(String cmd){
		state = cmd;
	}
	//Ignore
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	@Override
	public void mouseExited(MouseEvent event) {
	}
	@Override
	public void mouseMoved(MouseEvent event) {
	}
	
	
}
