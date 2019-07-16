package com.example.newsportal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adapters.RelatedNewsAdapter;
import com.example.models.Related_news_model;
import com.example.utils.ApiResources;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.newsportal.R.color.colorPrimary;
import static com.example.newsportal.tvnews_fragment.mediaSource;
import static java.lang.String.valueOf;

public class News_details extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerView;

    SimpleExoPlayer exoPlayer;

    public static SimpleExoPlayer player;

    private static String videoURL="";

    private static String video_type="";

    private static String news_type="";

    private RequestOptions option;

    private RequestQueue requestQueue ;

    private TextView headline_text,description_text,published_time_text;

    private ImageView text_bews_image,share_icon_big;

    private RelativeLayout player_field;

    private ImageView video_news_image;

    RecyclerView recyclerView1;

    private static String api_url;

    private ProgressBar progressBar;

    ApiResources apiResources;

    public static boolean isPlaying,isFullScr;

    public static ImageView imgFull;

    List<Related_news_model> related_news_list;

    private String news_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        recyclerView1 = findViewById(R.id.related_news_recycler_field);

        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view);

        headline_text = findViewById(R.id.news_headline_field_id);

        text_bews_image = findViewById(R.id.text_news_image_field_id);

        video_news_image = findViewById(R.id.video_news_image_field_id);

        description_text = findViewById(R.id.news_details_field_id);

        player_field = findViewById(R.id.video_player_field_id);

        published_time_text = findViewById(R.id.published_time_id);

        share_icon_big = findViewById(R.id.share_icon_id);

        imgFull=findViewById(R.id.img_full_scr);

        related_news_list = new ArrayList<>();

        progressBar =findViewById(R.id.tv_progressBar);

        progressBar.setMax(100); // 100 maximum value for the progress value

        progressBar.setProgress(50);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary));
        }

        Intent intent = getIntent();

        news_id = intent.getStringExtra("news_id");
        news_type = intent.getStringExtra("news_type");
        final String news_headline =intent.getStringExtra("headline_text");
        String published_time = intent.getStringExtra("published_time");
        String image_url = intent.getStringExtra("image_url");
        String news_description = intent.getStringExtra("description_text");
        //String id = intent.getStringExtra("");
        if (news_type.equals("video")){
            player_field.setVisibility(View.VISIBLE);
            text_bews_image.setVisibility(View.GONE);
            description_text.setVisibility(GONE);
            video_news_image.setVisibility(GONE);
            Glide.with(this).load(image_url).apply(option).into(video_news_image);

        }
        else {
            player_field.setVisibility(View.GONE);

            Glide.with(this).load(image_url).apply(option).into(text_bews_image);
        }

        headline_text.setText(news_headline);

        published_time_text.setText(published_time);

        description_text.setText(news_description);

        get_related_news();
        initVideoPlayer(videoURL, this,video_type);

        share_icon_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =news_headline + "\n" + "\n" +"I would like to share this. \n"
                        +" Here you can download this from playstore : \n" + "\n" + "https://play.google.com/store/apps/details?id=" +
                        BuildConfig.APPLICATION_ID ;
                String shareSub = "Share app";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
               startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

    }

    private void get_related_news() {
        apiResources = new ApiResources(this);
        api_url = apiResources.getSingle_news_url(news_id);
       // api_url = "http://192.168.1.30/newsportal/api/get_single_news_details?api_secret_key=newsportalapikey&id=3";

        Log.e("Link is ",String.valueOf(api_url));
        apiResources = new ApiResources(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (news_type.equals("video"))
                            {

                                videoURL = response.getString("stream_url");
                                video_type = response.getString("stream_from");

                            }

                            JSONArray jsonArray = response.getJSONArray("related_news");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);
                                Related_news_model data = new Related_news_model();
                                data.setId(myobject.getString("news_id"));
                                data.setTitle(myobject.getString("title"));
                                data.setPublished_time(myobject.getString("publish_datetime"));
                                data.setDescription(myobject.getString("description"));
                                data.setType(myobject.getString("type"));
                                data.setImage_url(myobject.getString("thumbnail_url"));
                                related_news_list.add(data);
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        setuprecyclerview(related_news_list);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(request) ;

    }

    private void setuprecyclerview(List<Related_news_model> lstdata) {

        // Log.e("Problem is Here!" , " Calling again !");

        recyclerView1.setNestedScrollingEnabled(false);

        RelatedNewsAdapter myadapter = new RelatedNewsAdapter(this,lstdata) ;

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        recyclerView1.setAdapter(myadapter);

    }

    public void initVideoPlayer(String url,Context context, String type){


        progressBar.setVisibility(VISIBLE);

        if (player!=null){
            player.release();

        }

        // playerLayout.setVisibility(VISIBLE);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new
        AdaptiveTrackSelection.Factory(bandwidthMeter);


        TrackSelector trackSelector = new
                DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance((Context) context, trackSelector);
        player.setPlayWhenReady(true);
        exoPlayerView.setPlayer(player);

        exoPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                Log.e("Visibil", valueOf(visibility));
                if (visibility==0){

                    imgFull.setVisibility(VISIBLE);

                }else {

                    imgFull.setVisibility(GONE);

                }
            }
        });

        Uri uri = Uri.parse(url);

        if (type.equals("hls")){
           // mediaSource = hlsMediaSource(uri,context);


        }
        else if (type.equals("youtube"))
        {
            Log.e("youtube url  :: ",url);
            // extractYoutubeUrl(url,context,18);
        }
        else if (type.equals("youtube-live"))
        {
            Log.e("youtube url  :: ",url);
            // extractYoutubeUrl(url,context,133);
        }
        else if (type.equals("rtmp"))
        {
            // mediaSource=rtmpMediaSource(uri);
        }else
            {
            mediaSource=mediaSource(uri,context);
        }

        player.prepare(mediaSource, true, false);

        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playWhenReady && playbackState == Player.STATE_READY) {

                    isPlaying=true;
                    progressBar.setVisibility(View.GONE);

                    Log.e("STATE PLAYER:::", valueOf(isPlaying));

                }
                else if (playbackState==Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                    isPlaying=false;
                    Log.e("STATE PLAYER:::", valueOf(isPlaying));
                }
                else if (playbackState==Player.STATE_BUFFERING) {
                    isPlaying=false;
                    progressBar.setVisibility(VISIBLE);

                    Log.e("STATE PLAYER:::", valueOf(isPlaying));
                } else {
                    // player paused in any state
                    isPlaying=false;
                    Log.e("STATE PLAYER:::", valueOf(isPlaying));
                }

            }
        });


    }

    private MediaSource mediaSource(Uri uri,Context context){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);

    }


    private void startPlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    public void onDestroy(){
        player.release();
        player = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {

        super.onPause();
        player.release();

    }

    @Override
    public void onResume() {
        super.onResume();
        startPlayer();
    }


}
