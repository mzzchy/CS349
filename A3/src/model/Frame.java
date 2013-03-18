package model;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Frame {
	/**
	 *  a list of displacement vector, with accumulation correspond to each frame
	 */
	ArrayList<AffineTransform> affineList = new ArrayList<AffineTransform>(0);
	int currentFrame = 0;
	int startFrame = 0; //the start visible frame
	int endFrame = -1; //-1 as not existing and end
	
	public Frame(){
		
		AffineTransform emptyAffine = new AffineTransform();
		affineList.add(emptyAffine);
	}
	
	public Frame(int initFrame){
		//????? + 1
		startFrame = initFrame;
//		System.out.print(startFrame+"\n");
		for(int i = 0; i < initFrame+1; i++){
			
			AffineTransform emptyAffine = new AffineTransform();
			affineList.add(emptyAffine);
			
		}
		currentFrame = initFrame+1; //???
	}
	
	
	public void insertStaticFrame(){
		//+1???
		AffineTransform emptyAffine = new AffineTransform();
		affineList.add(currentFrame,emptyAffine);
		currentFrame += 1;
		
	}
	
	//Get the extract affineTransform from lasso and apply that
	public void applyAffine(AffineTransform newAffine, int current) {
		if(current > affineList.size()-1){ //add new frame
			AffineTransform affine = new AffineTransform(affineList.get(affineList.size()-1));
			affine.concatenate(newAffine);
			affineList.add(affine);
		}else{ //overwritten existing frame
			AffineTransform affine =affineList.get(current);
			affine.concatenate(newAffine);
			
		}
		currentFrame = current;
		
	}
	
	
	//Give current affineTransform
	public AffineTransform getCurrentTransform(){
		currentFrame += 1;
		if(currentFrame > affineList.size()){
			currentFrame = affineList.size();
		}
		
		return affineList.get(currentFrame-1);
	}
	
	public void setCurrentFrame(int i){
		currentFrame = i;
	}
	
	public void setEndFrame(int i){ //object is not visible afterward
		endFrame = i;
//		System.out.print(endFrame +'\n');
	}
	
	//Whether currenf Frame
	public boolean isCurrentFrameVisible(){
//		System.out.print("start"+startFrame+"current"+currentFrame+"\n");
		//Not erased
		if(endFrame == -1 ){
			return (startFrame<= currentFrame); 
		}else{
			return (startFrame<= currentFrame && currentFrame < endFrame);
		}
	}

	
	
}
