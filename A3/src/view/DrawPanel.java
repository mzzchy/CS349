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
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

import model.*;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	private static final long serialVersionUID = 1L;

	private Animation animation;
	private Point currentPoint;
	private String drawState = "DRAW"; //draw, erase, select, drag
	private Lasso lasso;
	private long startTime = 0;
	private long elpasedTime = 0;
	private Timer timer = null;
	private AnimePanel animeLink;
	private static final long  PER_FRAME_TIME = 30;
	
	public DrawPanel(){
		super();
		animation = new Animation();
		lasso = null;
		
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
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3));
		animation.draw(g);
		if(lasso != null){
			lasso.draw(g);
		}
		g2.setTransform(new AffineTransform());
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		Point p = event.getPoint();
		if(drawState == "DRAW"){
			animation.startStroke(p, animeLink.getCurrentFrame());
			animation.endStroke(p);
		}else if(drawState == "ERASE"){
			animation.removeStroke(p);
		}else if(drawState == "SELECT"){
			lasso = null;
		}
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent event) {

		Point p = event.getPoint();
		if(drawState == "DRAW"){
			lasso = null;
			animation.startStroke(p,animeLink.getCurrentFrame());
		}else if(drawState == "ERASE"){
			lasso = null;
			currentPoint = p;
		}else if(drawState == "SELECT"){
			lasso = new Lasso(p);
		}else if(drawState == "DRAG"){
			currentPoint = p;
			animation.setSelectedStroke(lasso);
			animeLink.respondToStateChange(false);
		}
		
		startTime = System.currentTimeMillis();
		
		repaint();
	}
		
	@Override
	public void mouseDragged(MouseEvent event) {

		Point p = event.getPoint();
		if(drawState == "DRAW"){
			animation.continueStroke(p);
		}else if(drawState == "ERASE"){
			animation.removeStroke(currentPoint,p);
//			currentPoint = p;
		}else if(drawState == "SELECT"){
			lasso.addPoint(p);
		}else if(drawState == "DRAG"){
			//Drag to move
			elpasedTime += System.currentTimeMillis() - startTime;
			if(lasso.hasObject() && elpasedTime > PER_FRAME_TIME){ //Pass one sec
				//Check for button
				if(SwingUtilities.isLeftMouseButton(event)){ //translate
					AffineTransform trans = lasso.getTransAffine(currentPoint, p);
					animation.applyAffine(trans, animeLink.getCurrentFrame());
				}else if(SwingUtilities.isMiddleMouseButton(event)){ //rotate
					AffineTransform rotate = lasso.getRotateAffine(currentPoint,p);
					animation.applyAffine(rotate, animeLink.getCurrentFrame());
				}else if(SwingUtilities.isRightMouseButton(event)){ //scale
					AffineTransform scale = lasso.getScaleAffine(currentPoint, p);
					animation.applyAffine(scale, animeLink.getCurrentFrame());
				}
				
				animeLink.moveTimeForward();
				elpasedTime = 0;
				currentPoint = p;
			}
			startTime = System.currentTimeMillis();
			
		}
		
		repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		
		Point p = event.getPoint();
		if(drawState == "DRAW"){
			animation.endStroke(p);
		}else if(drawState == "ERASE"){
			currentPoint = p;
		}else if(drawState == "SELECT"){
			lasso.setLassoEnd();
		}else if(drawState == "DRAG"){
			animation.deSelectAll(lasso);
			currentPoint = p;
			animeLink.respondToStateChange(true);
		}
		repaint();
	}
	
	//Override for drag and select
	@Override
	public void mouseMoved(MouseEvent event) {
		Point p = event.getPoint();
		if(drawState == "SELECT" && lasso != null &&lasso.isPointInLasso(p)){
			setCommand("DRAG");
		}else if(drawState == "DRAG" &&lasso != null && !lasso.isPointInLasso(p)){
			setCommand("SELECT");
		}
	}
	
	public void setCommand(String cmd){
		
		if(cmd == "DRAW"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
			lasso = null;
			drawState = cmd;
		}else if(cmd == "ERASE"){
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR ));
			lasso = null;
			drawState = cmd;
		}else if(cmd == "SELECT"){
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
			drawState = cmd;
		}else if(cmd == "DRAG"){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR ));
			drawState = cmd;
		}else if(cmd == "PLAY"){
			animation.setCommand("PLAY");
			animation.setCurrentFrame(animeLink.getCurrentFrame());
			animeLink.respondToStateChange(false);
			lasso = null;
			//Start a timer
			timer = new Timer((int) PER_FRAME_TIME, this); //delay is in milisecond
			timer.setInitialDelay(30);
			timer.start();
			
		}else if(cmd == "PAUSE"){
			animation.setCommand("PAUSE");
			if(timer != null){
				timer.stop();
			}
			timer = null;
		}else if(cmd == "VIEW"){
			lasso = null;
			animation.setCurrentFrame(animeLink.getCurrentFrame());
			animation.setCommand("VIEW");
			repaint();
		}else if(cmd == "SAVE"){
			//Ask animation to save everything
			try {
				animation.saveXML();
			} catch (ParserConfigurationException e) {
				System.out.print(e);
			}
		}
	}
	
	public String getAnimationState(){
		return animation.getState();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
		animeLink.moveTimeForward();
		animation.setCurrentFrame(animeLink.getCurrentFrame());
		if(animation.isAnimationDone()){
			animeLink.respondToStateChange(true);
			setCommand("PAUSE");
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	@Override
	public void mouseExited(MouseEvent event) {
	}

	public void insertFrame() {
		animation.insertFrame();
	}
	
}
