package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Hunter {
	private float xPos;
	private float yPos;
    private GameView gameView;
    private Bitmap character;
   
    public Hunter(GameView gameView, Bitmap char1) {
          this.gameView=gameView;
          this.character=char1;
          
    }

   
    public void onDraw(Canvas canvas, float hx, float hy) {
    	this.xPos = hx;
    	this.yPos = hy;
    	canvas.drawBitmap(character, xPos-character.getWidth()/2, yPos-character.getHeight()/2, null);

    }
    
    public float getXPos() {
    	return xPos;
    }
    
    public float getYPos() {
    	return yPos;
    }
}
