package com.example.newsportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.utils.Config;
import com.example.utils.NetworkInst;

import java.io.IOException;

import static com.example.newsportal.R.color.colorPrimary;
import static com.example.newsportal.R.color.normal_tint;

public class MainActivity extends AppCompatActivity {
    private LinearLayout home_button;
    private LinearLayout news_button;
    private LinearLayout tv_button;
    private LinearLayout radio_button;
    private LinearLayout menu_button;
    private RelativeLayout radio_player;

    private ImageView live_radio_play_symble;
    private ImageView home_image;
    private ImageView news_image;
    private ImageView tv_image;
    private ImageView radio_image;
    private ImageView menu_image;
    private static ImageButton button_play;
    private TextView home_text;

    private TextView news_text;
    private TextView tv_text;
    private TextView radio_text;
    private TextView menu_text;
    private TextView clip_name_field;
    private TextView playing_audio_title;

   // private Toolbar toolbar;
    private Button toolbar_tv;
    private TextView title_text,clip_time_duration;
    private ImageButton setting_button;
    private ImageButton radio_cancel_button;
    private static MediaPlayer mediaPlayer;
    private LinearLayout radio_layout;
    private FrameLayout fragment_container_field;
    private ProgressBar delay_progress_field;
    //private static String stream = " ";
   // private static String stream = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3";



    private static boolean prepared=false,started = false;

    ProgressBar progressBar;

    @SuppressLint({"ResourceAsColor", "WrongViewCast", "NewApi"})
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        button_play = findViewById(R.id.img_player_play);
        fragment_container_field = findViewById(R.id.fragment_container);
        delay_progress_field = findViewById(R.id.delay_progressbar_id);

        button_play.setEnabled(false);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //media_initial(stream);

        progressBar = findViewById(R.id.progress_bar_collapse);

        clip_time_duration = findViewById(R.id.txt_playing_audio_duration);

        clip_name_field = findViewById(R.id.txt_playing_audio_name);

        load_home_fragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary));
        }

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

        setting_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                open_setting_activity();
            }
        });

        radio_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radio_player.setVisibility(View.GONE);
                mediaPlayer.stop();

            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started){
                    started=false;
                    mediaPlayer.pause();
                    button_play.setImageResource(R.drawable.ic_play_arrow_white);
                }
                else {

                    started=true;
                    mediaPlayer.start();
                    button_play.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });

    }

    private void delay_for_set(){
        fragment_container_field.setVisibility(View.GONE);
        delay_progress_field.setVisibility(View.VISIBLE);
        new CountDownTimer(Config.DELAY_DURATION, 1000) {

            @Override
            public void onFinish() {

                delay_progress_field.setVisibility(View.GONE);
                fragment_container_field.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }


    public void change_playing(String strm, final String title, final String duration) {
        radio_player.setVisibility(View.VISIBLE);

        if (prepared) {
            mediaPlayer.stop();
            started=false;
            button_play.setImageResource(R.drawable.ic_play_arrow_white);
        }


        String stream2 = strm;
        //"http://stream.zenolive.com/8wv4d8g4344tv";
        media_initial(stream2);

        clip_name_field.setText("Loading ...");

        clip_time_duration.setText("Loading ...");

        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(Config.SPLASH_DURATION, 1000) {

            @Override
            public void onFinish() {
                clip_name_field.setText(title);

                clip_time_duration.setText(duration);

                progressBar.setVisibility(View.GONE);

                    started=true;
                   // mediaPlayer.start();
                    button_play.setImageResource(R.drawable.ic_pause_black_24dp);



            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }

    public void media_initial(String stream)
    {
        new PlayerTask().execute(stream);

        Log.e("The Radio is :", stream);

    }

   void open_setting_activity(){

       Intent i = new Intent(this, setting_activity.class);
       startActivity(i);

   }

   void load_home_fragment(){

        title_text.setText("News Portal App");
        make_all_button_normal_color();
        home_text.setTextColor(this.getResources().getColor(colorPrimary));
        toolbar_tv.setVisibility(View.VISIBLE);
        home_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new Home_fragment());
        delay_for_set();


    }

    void load_news_fragment(){

        make_all_button_normal_color();
        title_text.setText("News 24/7");
        news_text.setTextColor(this.getResources().getColor(colorPrimary));
        news_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new news_fragment());
        delay_for_set();

    }

    void load_tv_fragment(){

        make_all_button_normal_color();
        title_text.setText("Live Tv");
        tv_text.setTextColor(this.getResources().getColor(colorPrimary));
        tv_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new tvnews_fragment());
        delay_for_set();

    }

    void load_radio_fragment(){
        if (new NetworkInst(this).isNetworkAvailable()){
            radio_player.setVisibility(View.VISIBLE);
        }

        make_all_button_normal_color();
        title_text.setText("Radio");
        radio_text.setTextColor(this.getResources().getColor(colorPrimary));
        radio_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new radio_fragment());
        delay_for_set();
        }

    void load_menu_fragment(){
        make_all_button_normal_color();
        title_text.setText("Menu");
        setting_button.setVisibility(View.VISIBLE);
        menu_text.setTextColor(this.getResources().getColor(colorPrimary));
        menu_image.setColorFilter(ContextCompat.getColor(getBaseContext(), colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        loadFragment(new Menu_fragment());
        delay_for_set();
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

    private class PlayerTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                //mediaPlayer.prepareAsync();
                prepared = true;
            } catch (IOException e) {

                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {

            }
            catch (IllegalStateException e) {

            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mediaPlayer.start();
            button_play.setEnabled(true);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

        if(started){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (started){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (prepared){
            mediaPlayer.release();

        }
    }
}
