package com.example.newsportal;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.TvdataAdapter;
import com.example.models.Category_wise_model;
import com.example.models.live_channel_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.String.valueOf;


public class tvnews_fragment extends Fragment {
    private RequestQueue requestQueue ;
    private RelativeLayout lPlay;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    private static String videoURL="" ;
    ProgressBar progressBar;
    public static SimpleExoPlayer player;
    public static MediaSource mediaSource=null;
    public static boolean isPlaying,isFullScr;
    public static ImageView imgFull;
    public static boolean isVideo=true;
    private int playerHeight ;
    private ApiResources apiResources;
    private static String video_type="";
    private List<Category_wise_model> lst_news1 ;
    private List<live_channel_data_model> tv_page_data_list ;
    private static String category1= " ";
    //private static String category2 = " ";
    //private static String category3 = " ";
    private String playing_video_title;
    private TextView playing_title_text,category_text1,category_text2,category_text3;
    private RecyclerView recyclerView_1,recyclerView_2;
    private LinearLayout fragment_part , empty_view ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvnews_fragment, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player_view);
        progressBar = view.findViewById(R.id.tv_progressBar);
        progressBar.setMax(100); // 100 maximum value for the progress value
        progressBar.setProgress(50);
        imgFull=view.findViewById(R.id.img_full_scr);
        lPlay = view.findViewById(R.id.play);
        playerHeight = lPlay.getLayoutParams().height;
        playing_title_text = view.findViewById(R.id.playing_video_title_id);
        lst_news1 = new ArrayList<>();
        tv_page_data_list = new ArrayList<>();
        fragment_part = view.findViewById(R.id.fragment_part_id);
        empty_view = view.findViewById(R.id.failed_layout_id);

        recyclerView_1 = view.findViewById(R.id.category_wise_recycler_id);

        if (new NetworkInst(getContext()).isNetworkAvailable()){
            get_data_from_json();
            empty_view.setVisibility(GONE);
        }else {

            fragment_part.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }




        setuprecyclerview(tv_page_data_list);

        initVideoPlayer(videoURL,getContext(),video_type);

        imgFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFullScr){
                    isFullScr=false;

                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    if (isVideo)
                    {
                        lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));

                    }else
                        {
                        lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));
                    }

                }else {

                    isFullScr=true;
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    if (isVideo){
                        lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

                    }else {
                        lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

                    }

                }

            }
        });
        //player.setVolume(0f);
    }


    private void get_data_from_json() {

        apiResources = new ApiResources(getContext());

        String url = apiResources.getTv_content_url();
        Log.e("Testing Tv content!!!:",valueOf(url));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject myobject1 = response.getJSONObject("tv_info");
                            playing_video_title = myobject1.getString("title");
                            videoURL = myobject1.getString("stream_url");
                            video_type = myobject1.getString("stream_from");
                            playing_title_text.setText(playing_video_title);
                            JSONArray myjsonarray = response.getJSONArray("featured_category_with_video_news");


                            for (int ind = 0;ind< myjsonarray.length();ind++) {
                                //lst_news1.clear();
                                JSONObject mobj1 = myjsonarray.getJSONObject(ind);
                                category1 = mobj1.getString("title");

                                JSONArray newsarray1 = mobj1.getJSONArray("news");

                                for (int i = 0; i < newsarray1.length(); i++) {

                                    JSONObject obj = newsarray1.getJSONObject(i);
                                    Category_wise_model data = new Category_wise_model();
                                    data.setNews_id(Integer.toString(obj.getInt("news_id")));
                                    data.setTitle(obj.getString("title"));
                                    data.setPublished_time(obj.getString("publish_datetime"));
                                    data.setNews_type(obj.getString("type"));
                                    data.setDescription(obj.getString("description"));
                                    data.setThumbnail_url(obj.getString("thumbnail_url"));
                                    //Log.e("data title value :" ,String.valueOf(data.getTitle()));
                                    lst_news1.add(data);

                                }
                                live_channel_data_model dt = new live_channel_data_model();
                                dt.setCategory_name(category1);
                               // Log.e("List value : ",valueOf(lst_news1.))
                                dt.setList_data(lst_news1);
                                tv_page_data_list.add(dt);
                                //lst_news1.clear();


                            }


                        } catch (JSONException e) {

                            e.printStackTrace();


                        }
                        setuprecyclerview(tv_page_data_list);

                    }



                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(request) ;

    }

    private void setuprecyclerview(List<live_channel_data_model> lstdata)
    {

        TvdataAdapter myadapter = new TvdataAdapter (getContext(),lstdata) ;

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView_1.setLayoutManager(horizontalLayoutManagaer);

        //recyclerView_1.addItemDecoration(new DividerItemDecoration(getContext(), 0));

        //recyclerView_1.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        recyclerView_1.setAdapter(myadapter);

    }



    private MediaSource hlsMediaSource(Uri uri, Context context){

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, ""), bandwidthMeter);

        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);

        String link = videoSource.toString();



        return videoSource;

    }

    public void initVideoPlayer(String url,Context context,String type){



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

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
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
            mediaSource = hlsMediaSource(uri,context);


        }else if (type.equals("youtube")){
            Log.e("youtube url  :: ",url);
           // extractYoutubeUrl(url,context,18);
        }
        else if (type.equals("youtube-live")){
            Log.e("youtube url  :: ",url);
           // extractYoutubeUrl(url,context,133);
        }
        else if (type.equals("rtmp")){
           // mediaSource=rtmpMediaSource(uri);
        }else {
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

    private void pausePlayer(){
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }
    private void startPlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    public void onPause() {

        super.onPause();
        pausePlayer();

    }

    @Override
    public void onResume() {
        super.onResume();
        startPlayer();
    }

    @Override
    public void onDestroy(){
        player.release();
        player = null;
        super.onDestroy();
    }




}
