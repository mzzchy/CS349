package com.example.ksmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FrameRateDialog extends Activity implements OnSeekBarChangeListener{

	public FrameRateDialog() {
		// TODO Auto-generated constructor stub
	}

	SeekBar seekBar;
//	Button setButton;
	TextView currentFrameRate;
	//5 t-0 60 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_rate_dialog);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setMax(55); //because min doesnt exit
		seekBar.setProgress(30);
		seekBar.setOnSeekBarChangeListener(this);
		
		currentFrameRate = (TextView) findViewById(R.id.currentFraneRate);
		//Be care ful about seek bar min value
//		setButton = (Button) findViewById(R.id.setButton);
	}

	@Override
	public void onProgressChanged(SeekBar seek, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		String text = (progress+5)+"";
		currentFrameRate.setText(text);
	}

	public void setButtonClicked(View view){
		//Get the current frame
		MainActivity.FPS = seekBar.getProgress()+5;
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
