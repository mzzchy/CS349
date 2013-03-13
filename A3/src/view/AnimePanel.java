package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimePanel extends JPanel implements  ActionListener, ChangeListener, KeyListener{
	
	JButton play;
	JButton pause;
	JSlider timeFrame;
	DrawPanel drawLink;
	private int SIZE = 0;
	boolean isInsert = false;
	int prevFrame = 0;
	
	private static final long serialVersionUID = 1L;

	public AnimePanel() {
		
		super(new BorderLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(new FlowLayout( FlowLayout.LEADING));
		
		play = new JButton("Play");
		play.setActionCommand("PLAY");
		play.addActionListener(this); 
		add(play );
		
		pause = new JButton("Pause");
		pause.setActionCommand("PAUSE");
		pause.addActionListener(this); 
		add(pause);
		
		timeFrame = new JSlider(JSlider.HORIZONTAL, 0, 0, 0); //int orientation,  min,  max, init value 100 sec
		timeFrame.setPaintTicks(true);
		timeFrame.setPreferredSize(new Dimension(30,30));
		timeFrame.addChangeListener(this);
		timeFrame.addKeyListener(this);
		add(timeFrame);
		
	}

	public void addActionLink(DrawPanel drawPanel){
		drawLink = drawPanel;
	}
	/**
	 * Slider action
	 */
	public void moveTimeForward(){
		int currentTime = timeFrame.getValue();
		currentTime += 1;
		
		if(currentTime > timeFrame.getMaximum()){
			timeFrame.setMaximum(currentTime);
			//Increase the max time and size 
			if(currentTime > SIZE && SIZE <= 370){ 
				SIZE += 1;
				timeFrame.setPreferredSize(new Dimension(30 + SIZE,30)); //base size of TICK to be visible
				SwingUtilities.updateComponentTreeUI(timeFrame);
			}

		}
		timeFrame.setValue(currentTime);
	}
	
	/**
	 * Button
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		drawLink.setCommand(cmd);
	}
	/**
	 * slider
	 */
	public int getCurrentFrame(){
		return timeFrame.getValue();
	}
	
	//only for pause mode
	@Override
	public void stateChanged(ChangeEvent event) {
		drawLink.setCommand("VIEW");
	
		if(isInsert && prevFrame < getCurrentFrame() ){ //hodling ctrl and dragging right
//			System.out.print(prevFrame+" "+getCurrentFrame()+"\n");
			//TODO:add data in frame
			drawLink.insertFrame();
//			int currentMax = timeFrame.getMaximum()+ 1;
//			timeFrame.setMaximum(currentMax);
//			if(SIZE <= 370){
//				SIZE += 1;
//				timeFrame.setPreferredSize(new Dimension(30 + SIZE,30)); //base size of TICK to be visible
//				SwingUtilities.updateComponentTreeUI(timeFrame);
//			}
			
		}
		prevFrame = getCurrentFrame();
	}
	
	public void respondToStateChange(boolean respond){
		if(respond){
			timeFrame.addChangeListener(this);
		}else{
			timeFrame.removeChangeListener(this);
		}
	}
	/**
	 * For inserting animation
	 */
	
	@Override
	public void keyPressed(KeyEvent event) {
//		System.out.print( event.getModifiersEx()); //ctrl - 128
		isInsert = (event.getModifiersEx() == 256 );
//		System.out.print(isInsert);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		isInsert = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

}
