package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Blocks {
	ArrayList <Rectangle> blocks;
	
	public Blocks(){
		blocks = new ArrayList<Rectangle>(0);
		//Add rectangle in it
		Rectangle rect1 = new Rectangle(300,400,100,30,"rect1");
		rect1.setColor(Color.green);
		blocks.add(rect1);
		/*
		Rectangle rect2 = new Rectangle(0,0,0,0,"rect2");
		rect2.setColor(Color.green);
		blocks.add(rect2);
		
		Rectangle rect3 = new Rectangle(0,0,0,0,"rect3");
		rect3.setColor(Color.green);
		blocks.add(rect3);*/
	}
	
	public void addBlock(Rectangle aRect){
		blocks.add(aRect);
	}
	
	//Same function for calling
	public void paint(Graphics g){
		for(Rectangle aRect : blocks){
			aRect.paint(g);
		}
	}
}
