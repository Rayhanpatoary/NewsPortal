package com.example.newsportal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.example.newsportal.R.color.colorPrimary;

public class setting_activity extends AppCompatActivity {

    private LinearLayout share_button;
    private ImageView back_image;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_id);

        //setSupportActionBar(toolbar);

        share_button = (LinearLayout) findViewById(R.id.share_app_button_id);
        back_image = (ImageView) findViewById(R.id.img_back);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary));
        }

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_share_options();
            }
        });

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void show_share_options() {


        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Application Link";
        String shareSub = "Share app";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));


    }

}
