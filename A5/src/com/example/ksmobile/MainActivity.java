package com.example.ksmobile;


import android.os.Bundle;
import android.app.Activity;
//import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements ColorPickerDialog.OnColorChangedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * List of button related function
	 * @param view
	 */
	public void colorButtonClicked(View view){
		ColorPickerDialog colorPicker = new ColorPickerDialog(this, this, 0);
		colorPicker.show();
	}
	
	public void playButtonClicked(View view){
		
	}
	
	public void fileButtonClicked(View view){
		//SHow a new dialog
	}

	@Override
	public void colorChanged(int color) {
		AnimationView animeView = (AnimationView)findViewById(R.id.animationView1);
		animeView.setBackgroundColor(color);
		
	}
	

}
