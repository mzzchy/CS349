package controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import view.*;

public class SketchMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ToolBar toolBar = new ToolBar();
		AnimePanel slider = new AnimePanel();
		DrawArea drawArea = new DrawArea();
		
		JFrame frame = new JFrame("KSektch");
		frame.setSize(600, 480);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		frame.getContentPane().add(drawArea, BorderLayout.CENTER);
		frame.getContentPane().add(slider, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
