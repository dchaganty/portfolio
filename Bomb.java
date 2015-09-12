package com.edu.virginia.cs2110.dhc5cu.ghosthunter;


import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bomb {

	private GameView gv;
	private Bitmap bomb;
	private Hunter h;
	private float x, y;
	private int width, height;
	private boolean explode = false;

	public Bomb(GameView gameView, Bitmap bmp, Hunter h) {
		this.gv = gameView;
		this.bomb = bmp;
		this.h = h;
		this.width = bomb.getWidth();
		this.height = bomb.getHeight();
		this.x = gv.getWidth()/2;
		this.y = gv.getHeight()/2;

	}

	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	//draws the bomb image onto the screen
	public void onDraw(Canvas canvas, boolean explode, float x, float y) {

		if (explode == false) {
			update(); //keeps updating the bomb's position

		} else {
		//draws the bomb off the screen if it is collided with
			this.x = (int)x - 10000; 
			this.y = (int)y - 10000;
		}
		canvas.drawBitmap(bomb, x, y, null);
	}
	
	public float getXPos() {
		return this.x;
	}
	public float getYPos() {
		return this.y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setExplode(boolean b) {
		explode = b;
	}

	public void update() {
		this.x = gv.getWidth()/2;
		this.y = gv.getHeight()/2;
	}

}
