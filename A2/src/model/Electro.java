package model;

public class Electro extends Rectangle{
	
	boolean electroOn;
	
	public Electro(int rX, int rY, int rWidth, int rHeight, String rId) {
		super(rX, rY, rWidth, rHeight, rId);
		electroOn = false;
	}

	public void setElectro(boolean on){
		electroOn = on;
	}
	
	
}
