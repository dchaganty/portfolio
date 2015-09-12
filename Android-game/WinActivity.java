package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WinActivity extends SurfaceView{
	private SurfaceHolder holder;
	private GameLoopSurface gameLoopThread;
	private GameView gv;
	private String win;
	private Stopwatch sw;
	
	public WinActivity(Context context) {
		super(context);
		gameLoopThread = new GameLoopSurface(holder, gv, sw);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
			    gameLoopThread.setRunning(true);
				gameLoopThread.execute((Void[])null);
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		win = "You Win";
	}
	protected void onDraw(Canvas canvas, float hx, float hy) {
		canvas.drawColor(Color.BLACK);
	    //Drawing score
	    Paint paint = new Paint();
	    paint.setColor(Color.MAGENTA);
	    paint.setTextSize(60);
	    canvas.drawText(win, canvas.getWidth()/2, canvas.getHeight()/2, paint);
	}
}
