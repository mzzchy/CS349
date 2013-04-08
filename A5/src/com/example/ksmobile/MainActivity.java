package com.example.ksmobile;


import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
//import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	Timer timer = null;
	static int FPS = 30;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.e("Create", "BLAHBAL");
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
//		ColorPickerDialog colorPicker = new ColorPickerDialog(this, this, 0);
//		colorPicker.show();
		Intent intent = new Intent(this, ColorDialog.class);
		startActivity(intent);
	}
	
	private Runnable Timer_Tick = new Runnable() {
	    public void run() {
	    	AnimationView animeView = (AnimationView)findViewById(R.id.animeView);
			   animeView.invalidate();
//			   Log.e("Play","Y");
			   if(animeView.isAnimationDone()){
				   timer.cancel();
//				   timer = null;
//				   Log.e("Play","N");
			   }

	    }
	};
	
	public void playButtonClicked(View view){
		
		//Schedule a timer task
		AnimationView animeView = (AnimationView)findViewById(R.id.animeView);
		//Ask if animeVIew has data else do nothing
		if(!animeView.hasLoadXML()){
			return ;
		}
		animeView.resetFrame();
		Log.e("FPS",""+FPS);
		timer = new Timer();
		timer.schedule(new TimerTask() {          
	        @Override
	        public void run() {
	        	runOnUiThread(Timer_Tick);
	        }

	    }, 0, 1000/FPS);
		
	}
	
	
	public void fileButtonClicked(View view){
		//SHow a new dialog
		try {
			//Get file from the resources
			final CharSequence[] items = getBaseContext().getResources().getAssets().list("KSketch");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Open file...");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
//			    	Log.e("Item", (String) items[item]);
			    	AnimationView animeView = (AnimationView)findViewById(R.id.animeView);
					animeView.loadXMLFile(items[item].toString());
					animeView.invalidate();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (IOException e) {
			Log.e("ListFile","KSketch doesn't exist");
		}
	}

	public void frameButtonClicked(View view){
		Intent intent = new Intent(this, FrameRateDialog.class);
		startActivity(intent);
	}
	

}
