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
			case R.id.blueView:
				rgb = R.color.blue;
				break;
			case R.id.redView:
				rgb = R.color.red;
				break;
			case R.id.yellowView:
				rgb = R.color.yellow;
				break;
			case R.id.greenView:
				rgb = R.color.green;
				
				break;
			default:
				break;
		}
		Log.e("Color", ""+rgb);
		AnimationView.backgroundColor = rgb;
		
	}
	
}
