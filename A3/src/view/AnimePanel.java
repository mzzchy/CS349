package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class AnimePanel extends JPanel {

	JButton play;
	JButton pause;
	JSlider timeSlider;
	
	private static final long serialVersionUID = 1L;

	public AnimePanel() {
		
		super(new BorderLayout());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setSize(600,50);
		
		timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0); //int orientation,  min,  max, init value
		play = new JButton("Play");
		pause = new JButton("Pause");
		
		add(play,BorderLayout.LINE_START );
		add(pause);
		add(timeSlider,BorderLayout.LINE_END);
		
	}

}
