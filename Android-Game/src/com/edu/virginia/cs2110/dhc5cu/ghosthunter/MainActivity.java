package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import java.text.SimpleDateFormat;
import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
	private GameView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gv = new GameView(this);
		//setContentView(R.layout.activity_main);
		setContentView(gv);
		MediaPlayer mp = MediaPlayer.create(this, R.raw.evil_laugh);
        mp.start();
        if (gv.win() == true) {
        	Toast.makeText(MainActivity.this, "You Win! Your time was " + gv.getTime(), Toast.LENGTH_SHORT).show();
        }
        if (gv.lose() == true) {
        	Toast.makeText(MainActivity.this, "You Lose!", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(MainActivity.this, WinActivity.class);
//        startActivity(i);
        }
       
        
		
		
	}
	
//	long timer = 0;
//	private 
//	
//    private SimpleDateFormat timerr = new SimpleDateFormat("mm:ss");
//	protected TextView timerDisplay;
//    Timer stopWatch = new Timer(1000, new ActionListener(){
//        public void actionPerformed(ActionEvent event){
//            timerDisplay.setText(TIME.format(new Date(timer++ * 1000)));
//        }
//    });
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


}
