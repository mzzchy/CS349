package com.example.ksmobile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		Intent intent = new Intent(this, ColorDialog.class);
		startActivity(intent);
	}
	
	private Runnable Timer_Tick = new Runnable() {
	    public void run() {
	    	AnimationView animeView = (AnimationView)findViewById(R.id.animeView);
			   animeView.invalidate();
			   if(animeView.isAnimationDone()){
				   timer.cancel();
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
		File sdcard = Environment.getExternalStorageDirectory();
		if (sdcard.isDirectory()){
		    String files[] = sdcard.list();
		    List<String> listItems = new ArrayList<String>(0);
		    for(String fileName: files){
		    	if(fileName.endsWith(".xml")){
		    		listItems.add(fileName);
		    		Log.e("", fileName);
		    	}
		    }
		    
		    final CharSequence[] charSequenceItems = listItems.toArray(new CharSequence[listItems.size()]);
		    
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Open file...");
			builder.setItems(charSequenceItems, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	AnimationView animeView = (AnimationView)findViewById(R.id.animeView);
					animeView.loadXMLFile(charSequenceItems[item].toString());
					animeView.invalidate();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	public void frameButtonClicked(View view){
		Intent intent = new Intent(this, FrameRateDialog.class);
		startActivity(intent);
	}
	

}
