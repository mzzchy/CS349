package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimePanel extends JPanel implements  ActionListener, ChangeListener{
	
	private static final int MAX_TIME = 60;
	JButton play;
	JButton pause;
	JSlider timeFrame;
	DrawPanel link;
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
		
		timeFrame = new JSlider(JSlider.HORIZONTAL, 0, MAX_TIME, 0); //int orientation,  min,  max, init value 100 sec
		timeFrame.setMajorTickSpacing(10);
		timeFrame.setMinorTickSpacing(1);
		timeFrame.setPaintTicks(true);
		timeFrame.setPreferredSize(new Dimension(400,30));
		timeFrame.addChangeListener(this);
		add(timeFrame);
		
	}

	public void addActionLink(DrawPanel drawPanel){
		link = drawPanel;
	}
	/**
	 * Slider action
	 */
	//TODO: add forward time
	public void moveTimeForward(){
		int currentTime = timeFrame.getValue();
		currentTime += 1;
		if(currentTime > MAX_TIME){
			currentTime = MAX_TIME;
		}
		timeFrame.setValue(currentTime);
	}
	
	/**
	 * Button
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		link.setCommand(cmd);
	}
	/**
	 * slider
	 */
	@Override
	public void stateChanged(ChangeEvent event) {
		
	}


}
