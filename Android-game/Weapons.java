package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Weapons {
     private int x; 
     private int y;
     private int xSpeed = 10;
     private int ySpeed = 10;
     private GameView gameView;
     private Bitmap bmp;
    
     public Weapons(GameView gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;
        Random r = new Random();
       	int xLow = 0;
       	int xHigh = 1000;
       	int R = r.nextInt(xHigh-xLow) + xLow;//creates a random position for the weapons to start on when game is started
       	
       	Random s = new Random();
       	int yLow = 0;
       	int yHigh = 1000;
       	int S = s.nextInt(yHigh-yLow) + yLow;
           this.x = R;
           this.y = S;
     }

     private void update() {
  	   int random = (int) (10*Math.random() + 0.5);
           if (x > gameView.getWidth() - bmp.getWidth() - xSpeed) {
                  xSpeed = -20 + random;
           }
           if (x + xSpeed< 0) {
                  xSpeed = 20 - random;
           }
           x = x + xSpeed;
           
           if(y > gameView.getHeight() - bmp.getHeight() - ySpeed) {
          	 ySpeed = -20 + random;
           }
           if(y + ySpeed < 0) {
          	 ySpeed = 20 - random;
           }
           y = y + ySpeed;
     }
     //draws weapon image onto canvas
     public void onDraw(Canvas canvas, boolean hasit, float hx, float hy) {
    	 if(hasit == false) {
    		 update();
    	 } 
    	 else {
    		 this.x = (int) hx - 70;
    		 this.y = (int) hy - 30;
    	 }
         canvas.drawBitmap(bmp, x , y, null);
     }
     
     public int getXPos() {
    	 return this.x;
     }
     
     public int getYPos() {
    	 return this.y;
     }
   
}
