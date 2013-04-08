package com.example.ksmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FrameRateDialog extends Activity implements OnSeekBarChangeListener{

	static int currentProgress = 25;
	SeekBar seekBar;
	TextView currentFrameRate;
	//5 t-0 60 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_rate_dialog);
		seekBar = (SeekBar) findViewById(R.id.anmieBar);
		seekBar.setMax(55); //because min doesnt exit
		seekBar.setProgress(currentProgress);
		seekBar.setOnSeekBarChangeListener(this);
		
		currentFrameRate = (TextView) findViewById(R.id.currentFraneRate);
		String text = (currentProgress+5)+"";
		currentFrameRate.setText(text);
	}

	@Override
	public void onProgressChanged(SeekBar seek, int progress, boolean arg2) {
		String text = (progress+5)+"";
		currentFrameRate.setText(text);
	}

	public void setButtonClicked(View view){
		//Get the current frame
		currentProgress = seekBar.getProgress();
		MainActivity.FPS = currentProgress+5;
		finish();
	}
	
	public void cancelButtonClicked(View view){
		finish();
	}
	
	
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		
	}

}
