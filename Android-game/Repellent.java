package com.edu.virginia.cs2110.dhc5cu.ghosthunter;


import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Repellent {

	private GameView gv;
	private Bitmap bmp;
	private Hunter h;
	private float x, y;
	private int width, height;
	private boolean used;
	private int count = 0;
 
	public Repellent(GameView gameView, Bitmap bmp, Hunter h) {
		this.gv = gameView;
		this.bmp = bmp;
		this.h = h;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.x = 15;
		this.y = 15;
		this.used = false;
	}

	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public void onDraw(Canvas canvas, boolean used, float x, float y) {

		if (used == false) {
			update();

		} else {
			this.x = (int)x - 10000;
			this.y = (int)y - 10000;
		}
		canvas.drawBitmap(bmp, x, y, null);
		count++;
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
	public void setUsed(boolean b) {
		used = b;
	}

	public void update() {
		if (count % 200 < 50) {
			this.x = 15;
			this.y = 15;
		}
		else if (count % 200 < 100) {
			this.x = 15;
			this.y = gv.getHeight() - 100;
		}
		else if (count % 200 < 150) {
			this.x = gv.getWidth() - 100;
			this.y = gv.getHeight() - 100;
		}
		else {
			this.x = gv.getWidth() - 100;
			this.y = 15;
		}
	}

}
