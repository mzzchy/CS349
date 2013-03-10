package controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import view.*;

public class SketchMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Init
		DrawPanel drawPanel = new DrawPanel();
		AnimePanel animePanel = new AnimePanel();
		EditPanel editPanel = new EditPanel();
		//Link together
		editPanel.addActionLink(drawPanel);
		animePanel.addActionLink(drawPanel);
		drawPanel.addActionLink(animePanel);
		
		JFrame frame = new JFrame("KSektch");
		frame.setSize(600, 480);
		frame.getContentPane().add(editPanel, BorderLayout.NORTH);
		frame.getContentPane().add(drawPanel, BorderLayout.CENTER);
		frame.getContentPane().add(animePanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
