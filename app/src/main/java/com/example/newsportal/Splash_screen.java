package com.example.newsportal;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.utils.Config;

public class Splash_screen extends AppCompatActivity {

    RelativeLayout splash_field;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar_id);
        splash_field = findViewById(R.id.splash_field_id);
        splash_field.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(Config.SPLASH_DURATION, 1000) {

            @Override
            public void onFinish() {

                Intent intent = new Intent(getBaseContext(), MainActivity.class);

                startActivity(intent);

                finish();

            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }
}
