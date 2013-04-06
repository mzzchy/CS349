package com.example.ksmobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class AnimationView extends View {

	public AnimationView(Context context) {
		super(context);
	}
	
	public AnimationView(Context context, AttributeSet attrs){
		super( context,  attrs); 
	}
	
	public AnimationView(Context context, AttributeSet attrs, int defStyle){
		super( context,  attrs,  defStyle); 
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		
		Path path = new Path();
		path.moveTo(50, 50);
		for(int i = 0; i < 5;i +=1){
			path.lineTo(50+i*10, 50+i*15);
		}
		canvas.drawPath(path, paint);
		
//		Log.v("hi");
	}

}
