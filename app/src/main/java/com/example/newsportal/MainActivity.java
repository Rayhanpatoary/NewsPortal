package com.example.newsportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import static com.example.newsportal.R.color.colorPrimary;
import static com.example.newsportal.R.color.normal_tint;

public class MainActivity extends AppCompatActivity {
    private LinearLayout home_button;
    private LinearLayout news_button;
    private LinearLayout tv_button;
    private LinearLayout radio_button;
    private LinearLayout menu_button;
    private RelativeLayout radio_player;

    private ImageView home_image;
    private ImageView news_image;
    private ImageView tv_image;
    private ImageView radio_image;
    private ImageView menu_image;

    private TextView home_text;

    private TextView news_text;
    private TextView tv_text;
    private TextView radio_text;
    private TextView menu_text;

   // private Toolbar toolbar;
    private Button toolbar_tv;
    private TextView title_text;
    private ImageButton setting_button;
    private ImageButton radio_cancel_button;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_button = findViewById(R.id.button_home);
        news_button = findViewById(R.id.button_news);
        tv_button = findViewById(R.id.button_tv);
        radio_button = findViewById(R.id.button_radio);
        menu_button = findViewById(R.id.button_menu);
        home_image = findViewById(R.id.home_image);
        news_image = findViewById(R.id.news_image);
        tv_image = findViewById(R.id.tv_image);
        radio_image = findViewById(R.id.radio_image);
        menu_image = findViewById(R.id.menu_image);
        home_text = findViewById(R.id.home_text);
        news_text = findViewById(R.id.news_text);
        tv_text = findViewById(R.id.tv_text);
        radio_text = findViewById(R.id.radio_text);
        menu_text = findViewById(R.id.menu_text);
        toolbar_tv = findViewById(R.id.live_tv_in_action_id);
        title_text = findViewById(R.id.action_title_id);
        setting_button = findViewById(R.id.setting_button);
        radio_player = findViewById(R.id.radio_player_id);
        radio_cancel_button = findViewById(R.id.radio_player_cancel_id);


        //setActionBar(toolbar);

        load_home_fragment();

        //getActionBar().setTitle("News Portal App1");


        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               load_home_fragment();
            }
        });

        news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_news_fragment();
            }
        });

        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_tv_fragment();
            }
        });

        radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               load_radio_fragment();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_menu_fragment();
            }
        });

        toolbar_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_tv_fragment();
            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_setting_activity();
            }
        });

        radio_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_player.setVisibility(View.GONE);

            }
        });


    }

   void open_setting_activity(){
       Intent i = new Intent(this,setting_activity.class);
       startActivity(i);
   }

   void load_home_fragment(){
        title_text.setText("News Portal App");
        make_all_button_normal_color();
        home_text.setTextColor(this.getResources().getColor(colorPrimary));
        toolbar_tv.setVisibility(View.VISIBLE);
        home_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new home_fragment());

    }
    void load_news_fragment(){
        make_all_button_normal_color();
        title_text.setText("News 24/7");
        news_text.setTextColor(this.getResources().getColor(colorPrimary));
        news_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new news_fragment());
    }

    void load_tv_fragment(){
        make_all_button_normal_color();
        title_text.setText("Live Tv");
        tv_text.setTextColor(this.getResources().getColor(colorPrimary));
        tv_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new tvnews_fragment());
    }
    void load_radio_fragment(){
        radio_player.setVisibility(View.VISIBLE);
        make_all_button_normal_color();
        title_text.setText("Radio");
        radio_text.setTextColor(this.getResources().getColor(colorPrimary));
        radio_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new radio_fragment());
    }

    void load_menu_fragment(){
        make_all_button_normal_color();
        title_text.setText("Menu");
        setting_button.setVisibility(View.VISIBLE);
        menu_text.setTextColor(this.getResources().getColor(colorPrimary));
        menu_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new menu_fragment());
    }

    void make_all_button_normal_color(){
        toolbar_tv.setVisibility(View.GONE);
        setting_button.setVisibility(View.GONE);
        home_text.setTextColor(this.getResources().getColor(normal_tint));
        news_text.setTextColor(this.getResources().getColor(normal_tint));
        tv_text.setTextColor(this.getResources().getColor(normal_tint));
        radio_text.setTextColor(this.getResources().getColor(normal_tint));
        menu_text.setTextColor(this.getResources().getColor(normal_tint));

        home_image.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.normal_tint), android.graphics.PorterDuff.Mode.SRC_IN);
        news_image.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.normal_tint), android.graphics.PorterDuff.Mode.SRC_IN);
        tv_image.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.normal_tint), android.graphics.PorterDuff.Mode.SRC_IN);
        radio_image.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.normal_tint), android.graphics.PorterDuff.Mode.SRC_IN);
        menu_image.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.normal_tint), android.graphics.PorterDuff.Mode.SRC_IN);

    }

    private boolean loadFragment(Fragment fragment){

        if (fragment!=null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();

            return true;
        }
        return false;

    }
}
