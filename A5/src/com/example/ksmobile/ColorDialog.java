package com.example.ksmobile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ColorDialog extends Activity {

	int rgb = Color.WHITE;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pick_color_dialog);
	}
	
	public void onColorViewClicked(View view){
		
		switch(view.getId()){
			case R.id.whiteView: rgb = Color.WHITE; break;
			case R.id.redView: rgb = Color.RED; break;
			case R.id.yellowView: rgb = Color.YELLOW; break;
			case R.id.greenView: rgb = Color.GREEN; break;
			case R.id.blueView: rgb = Color.BLUE; break;
			case R.id.purpleView: rgb = Color.MAGENTA; break;
			default: break;
		}
		Log.e("Color", ""+rgb);
		AnimationView.backgroundColor = rgb;
		finish();
	}
	
}
