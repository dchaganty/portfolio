package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopSurface gameLoopThread;
	
	private int score = 0;
	//GHOSTS
	private Bitmap ghostpic;
	private Ghost ghost1;
	private Ghost ghost2;
	private Ghost ghost3;
	private Ghost ghost4;
	private Ghost ghost5;
	private Ghost ghost6;
	
	//GHOST COLLISIONS
	private boolean ghost1killed = false;
	private boolean ghost2killed = false;
	private boolean ghost3killed = false;
	private boolean ghost4killed = false;
	private boolean ghost5killed = false;
	private boolean ghost6killed = false;

	//BACKGROUND
	private Bitmap scaled;
	
	//HUNTER
	private Bitmap characterh;
	private Hunter hunter;
	
	//WEAPONS
	private Bitmap swordpic;
	private Weapons weapon;
	private Bomb bomb;
	private Bitmap bomb1;
	private boolean explode;
	private Repellent rp;
	private Bitmap bmpr;
	private boolean used;
	
	
	//WEAPON COLLISIONS
	private boolean hasWeapon;
	
	//KEEPING SCORE
	private int count = 0;
	private int countGhosts = 0;
	private int health = 6;
	//MONEY
	private Bitmap spin_money;
	private Money money1;
	private Money money2;
	private Money money3;
	private Money money4;
	private Money money5;
	
	private boolean hasMoney1;
	private boolean hasMoney2;
	private boolean hasMoney3;
	private boolean hasMoney4;
	private boolean hasMoney5;
	
	private Stopwatch s = new Stopwatch();
	private String time;	

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopSurface(holder, this, s);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
			    Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.dungeon);
			    scaled = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), true);
			    gameLoopThread.setRunning(true);
				gameLoopThread.execute((Void[])null);
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		ghostpic = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);//ghosts
		
		ghost1 = new Ghost(this, ghostpic);
		ghost2 = new Ghost(this, ghostpic);
		ghost3 = new Ghost(this, ghostpic);
		ghost4 = new Ghost(this, ghostpic);
		ghost5 = new Ghost(this, ghostpic);
		ghost6 = new Ghost(this, ghostpic);
		
		characterh = BitmapFactory.decodeResource(getResources(), R.drawable.hunter);//hunter
		swordpic = BitmapFactory.decodeResource(getResources(), R.drawable.sword);//weapons
		spin_money= BitmapFactory.decodeResource(getResources(), R.drawable.money);//money

		weapon = new Weapons(this, swordpic);
		hunter = new Hunter(this, characterh);
		money1 = new Money(this, spin_money);
		money2 = new Money(this, spin_money);
		money3 = new Money(this, spin_money);
		money4 = new Money(this, spin_money);
		money5 = new Money(this, spin_money);
		
		s = new Stopwatch();
		s.run();
		bomb1 = BitmapFactory.decodeResource(getResources(), R.drawable.bomb2);
		bomb = new Bomb(this, bomb1, hunter);
		bmpr = BitmapFactory.decodeResource(getResources(), R.drawable.repellent);
		rp = new Repellent(this, bmpr, hunter);
		used = false;
	}
	

	@SuppressLint("WrongCall")
	protected void onDraw(Canvas canvas, float hx, float hy) {
		canvas.drawColor(Color.BLACK);
	    canvas.drawBitmap(scaled, 0, 0, null); // draw the background

	    //Drawing score
	    Paint paint = new Paint();
	    paint.setColor(Color.WHITE);
	    paint.setTextSize(30);
	    canvas.drawText("Coins earned: "+ count, canvas.getWidth()/2-235, 50, paint);
	    canvas.drawText("Ghosts killed: "+ countGhosts , canvas.getWidth()/2+20, 50, paint);
	    canvas.drawText("Health: " + health, canvas.getWidth()/2 - 235, 145, paint );
	    canvas.drawText("Time: " + getTime(), canvas.getWidth()/2 + 20, 145, paint);
	    //canvas.drawText("Time: " + stopWatch, canvas.getWidth()/2 - 235, 100, paint);
	    
	    //Drawing ghosts
		ghost1.onDraw(canvas, ghost1killed, hx, hy, used, hunter);
		ghost2.onDraw(canvas, ghost2killed, hx, hy, used, hunter);
		ghost3.onDraw(canvas, ghost3killed, hx, hy, used, hunter);
		ghost4.onDraw(canvas, ghost4killed, hx, hy, used, hunter);
		ghost5.onDraw(canvas, ghost5killed, hx, hy, used, hunter);
		ghost6.onDraw(canvas, ghost6killed, hx, hy, used, hunter);

		weapon.onDraw(canvas, hasWeapon, hx, hy);
		hunter.onDraw(canvas, hx, hy);
		
		//Drawing money
		money1.onDraw(canvas, hasMoney1, hx, hy);
		money2.onDraw(canvas, hasMoney2, hx, hy);
		money3.onDraw(canvas, hasMoney3, hx, hy);
		money4.onDraw(canvas, hasMoney4, hx, hy);
		money5.onDraw(canvas, hasMoney5, hx, hy);
		
		//Drawing Bomb
		bomb.onDraw(canvas, explode, bomb.getXPos(), bomb.getYPos());
		rp.onDraw(canvas, used, rp.getXPos(), rp.getYPos());
		
		detectCollision();
		exploded();
		repelled();
		killGhosts();
		
	}
	
	public String getTime() {
		int[] currentTime = s.getTime();
		time = currentTime[1] + " : " + currentTime[2];
		return time;
	}
	
	public void detectCollision() {
		//sword collision
		if(hunter.getXPos() >= weapon.getXPos() && hunter.getXPos() <= weapon.getXPos() + swordpic.getWidth() && 
				hunter.getYPos() >= weapon.getYPos() && hunter.getYPos() <= weapon.getYPos() + swordpic.getHeight() && count == 5) {
			hasWeapon = true;
//			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.weapon);
//	        mp.start();
		}
		else {
			hasWeapon = false;
		}
		
		//money collision
		if(hunter.getXPos() >= money1.getXPos() && hunter.getXPos() <= money1.getXPos() + spin_money.getWidth() && 
				hunter.getYPos() >= money1.getYPos() && hunter.getYPos() <= money1.getYPos() + spin_money.getHeight()) {
			hasMoney1 = true;
			count++;
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.success);
	        mp.start();
		}
		else {
			hasMoney1 = false;
		}
		if(hunter.getXPos() >= money2.getXPos() && hunter.getXPos() <= money2.getXPos() + spin_money.getWidth() && 
				hunter.getYPos() >= money2.getYPos() && hunter.getYPos() <= money2.getYPos() + spin_money.getHeight()) {
			hasMoney2 = true;
			count++;
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.success);
	        mp.start();
		}
		else {
			hasMoney2 = false;
		}
		if(hunter.getXPos() >= money3.getXPos() && hunter.getXPos() <= money3.getXPos() + spin_money.getWidth() && 
				hunter.getYPos() >= money3.getYPos() && hunter.getYPos() <= money3.getYPos() + spin_money.getHeight()) {
			hasMoney3 = true;
			count++;
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.success);
	        mp.start();
		}
		else {
			hasMoney3 = false;
		}
		if(hunter.getXPos() >= money4.getXPos() && hunter.getXPos() <= money4.getXPos() + spin_money.getWidth() && 
				hunter.getYPos() >= money4.getYPos() && hunter.getYPos() <= money4.getYPos() + spin_money.getHeight()) {
			hasMoney4 = true;
			count++;
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.success);
	        mp.start();
		}
		else {
			hasMoney4 = false;
		}
		if(hunter.getXPos() >= money5.getXPos() && hunter.getXPos() <= money5.getXPos() + spin_money.getWidth() && 
				hunter.getYPos() >= money5.getYPos() && hunter.getYPos() <= money5.getYPos() + spin_money.getHeight()) {
			hasMoney5 = true;
			count++;
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.success);
	        mp.start();
		}
		else {
			hasMoney5 = false;
		}
		
		// ghost kill hunter
		if (hasWeapon == false && hunter.getXPos() >= ghost1.getXPos() && hunter.getXPos() <= ghost1.getXPos() + ghostpic.getWidth() && 
					hunter.getYPos() >= ghost1.getYPos() && hunter.getYPos() <= ghost1.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if(hasWeapon == false && hunter.getXPos() >= ghost2.getXPos() && hunter.getXPos() <= ghost2.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost2.getYPos() && hunter.getYPos() <= ghost2.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if(hasWeapon == false && hunter.getXPos() >= ghost3.getXPos() && hunter.getXPos() <= ghost3.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost3.getYPos() && hunter.getYPos() <= ghost3.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if(hasWeapon == false && hunter.getXPos() >= ghost4.getXPos() && hunter.getXPos() <= ghost4.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost4.getYPos() && hunter.getYPos() <= ghost4.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if(hasWeapon == false && hunter.getXPos() >= ghost5.getXPos() && hunter.getXPos() <= ghost5.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost5.getYPos() && hunter.getYPos() <= ghost5.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if(hasWeapon == false && hunter.getXPos() >= ghost6.getXPos() && hunter.getXPos() <= ghost6.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost6.getYPos() && hunter.getYPos() <= ghost6.getYPos() + ghostpic.getHeight()) {
			health --;
		}
		if (health <= 0) {
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.you_lose);
	        mp.start();
			gameLoopThread.setRunning(false);
		
			int duration = Toast.LENGTH_SHORT;
			Toast.makeText(this.getContext(), "You Lose", duration).show();
			//stop game
			//GAME OVER YOU LOSE
		}
		if (countGhosts == 6) {
			MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.you_win_sound_effect);
	        mp.start();
			gameLoopThread.setRunning(false);
			
			//stop game
		}
		//bomb collision
		if (explode == true)  {
			bomb.setX(-11111);
			bomb.setY(-10000);
			bomb.setExplode(true);
		}
		
		if (used == true)  {
			rp.setX(-11111);
			rp.setY(-10000);
			rp.setUsed(true);
		}
		
	}
	
	public boolean exploded() {
		if (hunter.getXPos() >= bomb.getXPos() && hunter.getXPos() <= (bomb.getXPos() + bomb.getWidth())  && 
				hunter.getYPos() >= bomb.getYPos() && hunter.getYPos() <= (bomb.getYPos() + bomb.getHeight())) {
			
		explode = true;
		return true; }
		else return false;
	}
	
	public boolean repelled() {
		if (hunter.getXPos() >= rp.getXPos() && hunter.getXPos() <= (rp.getXPos() + rp.getWidth())  && 
				hunter.getYPos() >= rp.getYPos() && hunter.getYPos() <= (rp.getYPos() + rp.getHeight())) {
			
		used = true;
		return true; }
		else return false;
	}
	
	
	public void killGhosts() {
		if((hunter.getXPos() >= ghost1.getXPos() && hunter.getXPos() <= ghost1.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost1.getYPos() && hunter.getYPos() <= ghost1.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true &&
				ghost1.getXPos() >= bomb.getXPos() - 250 && ghost1.getXPos()<= bomb.getXPos() + 250 
					&& ghost1.getYPos() >= bomb.getYPos() - 250 && ghost1.getYPos() <= bomb.getYPos() +250 )) {
			ghost1killed = true;
			countGhosts++;
			hasWeapon = false;
		}
		if((hunter.getXPos() >= ghost2.getXPos() && hunter.getXPos() <= ghost2.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost2.getYPos() && hunter.getYPos() <= ghost2.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true && 
				ghost2.getXPos() >= bomb.getXPos() - 250 && ghost2.getXPos()<= bomb.getXPos() + 250 
					&& ghost2.getYPos() >= bomb.getYPos() - 250 && ghost2.getYPos() <= bomb.getYPos() +250 )) {
			ghost2killed = true;
			countGhosts++;
			hasWeapon = false;
		}
		if((hunter.getXPos() >= ghost3.getXPos() && hunter.getXPos() <= ghost3.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost3.getYPos() && hunter.getYPos() <= ghost3.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true && ghost3.getXPos() >= bomb.getXPos() - 250 && ghost3.getXPos()<= bomb.getXPos() + 250 
					&& ghost3.getYPos() >= bomb.getYPos() - 250 && ghost3.getYPos() <= bomb.getYPos() +250)) {
			ghost3killed = true;
			countGhosts++;
			hasWeapon = false;
		}
		if((hunter.getXPos() >= ghost4.getXPos() && hunter.getXPos() <= ghost4.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost4.getYPos() && hunter.getYPos() <= ghost4.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true && ghost4.getXPos() >= bomb.getXPos() - 250 && ghost4.getXPos()<= bomb.getXPos() + 250 
					&& ghost4.getYPos() >= bomb.getYPos() - 250 && ghost4.getYPos() <= bomb.getYPos() +250 )) {
			ghost4killed = true;
			countGhosts++;
			hasWeapon = false;
		}
		if((hunter.getXPos() >= ghost5.getXPos() && hunter.getXPos() <= ghost5.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost5.getYPos() && hunter.getYPos() <= ghost5.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true && ghost5.getXPos() >= bomb.getXPos() - 250 && ghost5.getXPos()<= bomb.getXPos() + 250 
					&& ghost5.getYPos() >= bomb.getYPos() - 250 && ghost5.getYPos() <= bomb.getYPos() + 250 )) {
			ghost5killed = true;
			countGhosts++;
			hasWeapon = false;
		}
		if((hunter.getXPos() >= ghost6.getXPos() && hunter.getXPos() <= ghost6.getXPos() + ghostpic.getWidth() && 
				hunter.getYPos() >= ghost6.getYPos() && hunter.getYPos() <= ghost6.getYPos() + ghostpic.getHeight() && hasWeapon == true) || (exploded() == true && ghost6.getXPos() >= bomb.getXPos() - 250 && ghost6.getXPos()<= bomb.getXPos() + 250 
				&& ghost6.getYPos() >= bomb.getYPos() - 250 && ghost6.getYPos() <= bomb.getYPos() + 250) ) {
			ghost6killed = true;
			countGhosts++;
			hasWeapon = false;
		}
	}
	
	public boolean win() {
		if (ghost1killed == true && ghost2killed == true && ghost3killed == true && ghost4killed == true && ghost5killed == true && ghost6killed == true && health >0) {
			return true;
		}
		else return false;
	}
	public boolean lose() {
		if (health <=  0) 
			return true;
		else return false;
	}
	
}


