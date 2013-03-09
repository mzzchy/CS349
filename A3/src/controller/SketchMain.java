package controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import view.*;

public class SketchMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EditPanel editPanel = new EditPanel();
		AnimePanel animePanel = new AnimePanel();
		DrawPanel drawPanel = new DrawPanel();
		editPanel.addActionLink(drawPanel);
		
		JFrame frame = new JFrame("KSektch");
		frame.setSize(600, 480);
		frame.getContentPane().add(editPanel, BorderLayout.NORTH);
		frame.getContentPane().add(drawPanel, BorderLayout.CENTER);
		frame.getContentPane().add(animePanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Connect other component together
	}

}
