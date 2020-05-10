package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;

public class PlashScreenActivity extends AppCompatActivity {

    private final long plashScreenOpeningTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash_screen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(plashScreenOpeningTime);
                Intent intent = new Intent(PlashScreenActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        thread.start();

    }
}
