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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnSeekBarChangeListener{

	Timer timer = null;
	static int FPS = 30;
	SeekBar seekBar;
	AnimationView animeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		seekBar = (SeekBar) findViewById(R.id.anmieBar);
		seekBar.setMax(150);
		seekBar.setOnSeekBarChangeListener(this);
		
		animeView = (AnimationView)findViewById(R.id.animeView);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		animeView.setBackgroundColor(AnimationView.backgroundColor);
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
	    		
	    		animeView.invalidate();
	    		if(animeView.isAnimationDone()){
				   timer.cancel();
	    		}

	    }
	};
	
	public void playButtonClicked(View view){
		//Schedule a timer task
		if(!animeView.hasLoadXML()){
			return ;
		}
		//Just reset
		animeView.resetFrame();
		seekBar.setProgress(0);
		Log.e("FPS",""+FPS);
		timer = new Timer();
		timer.schedule(new TimerTask() {          
	        @Override
	        public void run() {
	        	runOnUiThread(Timer_Tick);
	        	seekBar.setProgress(seekBar.getProgress()+1);
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
					animeView.loadXMLFile(charSequenceItems[item].toString());
					animeView.invalidate();
					//Get the max from anime view?
//					seekBar.setMax(animeView.getAnimationLength());
//					Log.e("Seek",""+seekBar.getMax());
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
/**
 * Seekbar protocols
 */

	@Override
	public void onProgressChanged(SeekBar bar, int progress, boolean arg2) {
		//Ask it to set the progress
		animeView.setCurrentAnimation(progress);
		animeView.invalidate();
	}


	@Override
	public void onStartTrackingTouch(SeekBar bar) {
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar bar) {
		
	}
	

}
