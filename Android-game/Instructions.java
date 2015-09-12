package com.edu.virginia.cs2110.dhc5cu.ghosthunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Instructions extends Activity {
	// Splash screen timer
    private static int time = 4000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruct);
 Thread timer = new Thread() {
            public void run() {
            	try {
            		sleep(8000);
            	}
            	catch(InterruptedException e) {
            		e.printStackTrace();
            	}finally {
            		Intent i = new Intent(Instructions.this, MainActivity.class);
                startActivity(i);
            	}
            }
        }; timer.start();
    }
}
