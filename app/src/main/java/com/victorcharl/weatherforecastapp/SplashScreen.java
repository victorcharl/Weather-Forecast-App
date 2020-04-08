package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread th = new Thread() {
            @Override
            public void run()
            {
                try {
                    sleep(3000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent mainAct = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainAct);
                    finish();
                }
            }
        };
        th.start();
    }
}
