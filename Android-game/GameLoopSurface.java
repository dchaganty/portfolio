package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameLoopSurface extends AsyncTask<Void, Void, Void> {
	private SurfaceHolder nsurfhold;
	private GameView gameview;
	private float x, y;
	private float hx, hy;//hunter position 

	//private boolean run = true;
	static final long FPS = 10;
	public boolean running;
	private Canvas canvas;
	private Context context;
	private Stopwatch sw;
	
	public GameLoopSurface(SurfaceHolder sh, GameView gv, Stopwatch sw) {
		nsurfhold = sh;
		gameview = gv;
		this.sw = sw;
		sw.run();
		
		hx = 500;//for hunter initial x position
		hy = 500;//for hunter initial y position
		gv.setOnTouchListener(new OnTouchListener() {
			//creates the touch screen ability to move player with your finger
			public boolean onTouch(View v, MotionEvent e) {
				x = e.getX();
				y = e.getY();
				hx = x + 30;//for hunter movement
				hy = y - 30;//for hunter movement
				
				switch(e.getAction() & MotionEvent.ACTION_MASK) {//check what actions occurred
				case MotionEvent.ACTION_DOWN: 
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_CANCEL: 
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				}
				return true;
			}

		});
	}
	public void setRunning(boolean run) {
		this.running = run;
	}
	@SuppressLint("WrongCall")
	@Override
	protected Void doInBackground(Void... params) {
		long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
		while(running) {
			Canvas canvas = null;
			startTime = System.currentTimeMillis();
            try {
                   canvas = gameview.getHolder().lockCanvas();
                   synchronized (gameview.getHolder()) {
                          gameview.onDraw(canvas, hx, hy);
                   }
            } finally {
                   if (canvas != null) {
                          gameview.getHolder().unlockCanvasAndPost(canvas);
                   }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                   if (sleepTime > 0)
                          Thread.sleep(sleepTime);
                   else
                          Thread.sleep(10);
            } catch (Exception e) {}
		}
		return null;
	}
	

}
