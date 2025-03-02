package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Money {
	 private int x; 
     private int y;
     private int xSpeed = 15;
     private int ySpeed = 15;
     private GameView gameView;
     private Bitmap bmp;
    
     public Money(GameView gameView, Bitmap bmp) {
           this.gameView=gameView;
           this.bmp=bmp;
           Random r = new Random();
       	int xLow = 0;
       	int xHigh = 1000;
       	int R = r.nextInt(xHigh-xLow) + xLow;
       	
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
     
     public void onDraw(Canvas canvas, boolean hasMoney, float hx, float hy) {
  	   if(hasMoney == false) {
		   update();

	   }
	   else {
		   this.x = (int) (hx - 10000);
		   this.y = (int) (hy - 10000);
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
