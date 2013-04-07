package com.example.ksmobile;


import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
	
	public CharSequence[] listRaw(){
		try {
			return getBaseContext().getResources().getAssets().list("KSketch");
//			for(String s: allFilesInPackage){
//				Log.e("fileName", s);
//			}
			
		} catch (IOException e) {
			Log.e("ListFile","KSketch doesn't exist");
		}
		return null;
	}
	
	public void fileButtonClicked(View view){
		//SHow a new dialog
		try {
			final CharSequence[] items = getBaseContext().getResources().getAssets().list("KSketch");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Open file...");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
//			    	Log.e("Item", (String) items[item]);
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (IOException e) {
			Log.e("ListFile","KSketch doesn't exist");
		}
	}

	@Override
	public void colorChanged(int color) {
		AnimationView animeView = (AnimationView)findViewById(R.id.animationView1);
		animeView.setBackgroundColor(color);
		
	}

}
