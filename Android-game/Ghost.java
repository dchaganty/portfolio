package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ghost {

	private float x;
	private float y;
	private int xSpeed = 10;
	private int ySpeed = 10;
	private GameView gameView;
	private Bitmap bmp;
	private int count = 60;

	public Ghost(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		Random r = new Random();
		int xLow = 0;
		int xHigh = 1000;
		int R = r.nextInt(xHigh - xLow) + xLow;

		Random s = new Random();
		int yLow = 0;
		int yHigh = 1000;
		int S = s.nextInt(yHigh - yLow) + yLow; //generates random positions for the ghosts to start at 
		this.x = R;
		this.y = S;
	}

	//updates the positions of the ghosts as the game continues and makes them move randomly across the screen
	private void update() {
		int random = (int) (10 * Math.random() + 0.5);
		if (x > gameView.getWidth() - bmp.getWidth() - xSpeed) {
			xSpeed = -20 + random;
		}
		if (x + xSpeed < 0) {
			xSpeed = 20 - random;
		}
		x = x + xSpeed;

		if (y > gameView.getHeight() - bmp.getHeight() - ySpeed) {
			ySpeed = -20 + random;
		}
		if (y + ySpeed < 0) {
			ySpeed = 20 - random;
		}
		y = y + ySpeed;
	}

	//draws the ghost images on the screen
	public void onDraw(Canvas canvas, boolean killed, float hx, float hy, boolean rp, Hunter h) {
		if (rp && count > 0 && !killed) {
			this.x = (canvas.getWidth()/2 + h.getXPos()) % canvas.getWidth();
			this.y = (canvas.getHeight()/2 + h.getYPos()) % canvas.getHeight();
			count--;
		}
		else if (!killed) {
			update();

		} else {
			this.x = hx - 10000;
			this.y = hy - 10000;
		}
		canvas.drawBitmap(bmp, x, y, null);
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

}
