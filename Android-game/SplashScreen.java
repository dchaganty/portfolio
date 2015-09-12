package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);//draw the splash screen
        
        Thread timer = new Thread() {
        	
            
            public void run() {
            	try {
            		sleep(3000);
            	}
            	catch(InterruptedException e) {
            		e.printStackTrace();
            	}finally {
            		Intent i = new Intent(SplashScreen.this, Instructions.class);
                startActivity(i);
            	}
            }
        }; timer.start();
    }
}
