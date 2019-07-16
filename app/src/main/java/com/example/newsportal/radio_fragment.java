package com.example.newsportal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.adapters.RadioAdapter;
import com.example.models.Radio_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link radio_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link radio_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */


    public class radio_fragment extends Fragment {
    private RequestQueue requestQueue ;
    RelativeLayout live_radio_button ;
    ImageView pause_play;
    TextView live_radio_title_field;
    RecyclerView recyclerView;
    String page_url;
    private static String live_ratio_title,live_thumbnil_url;
    private ApiResources apiResources;
    private LinearLayout failed_message;
    private LinearLayout radio_design_field;
    private List<Radio_data_model> clip_list;
    private static String live_radio_url = "";
    private RelativeLayout live_button_field;
    private ImageView radio_thumbnil_field;
    private RequestOptions option;
    //private static String live_radio_url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3";
    //private RelativeLayout radio_player1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_radio_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clip_list = new ArrayList<>();
        live_radio_button = view.findViewById(R.id.live_radio_button_id);
        apiResources = new ApiResources(getContext());
        live_radio_title_field = view.findViewById(R.id.txt_radio_title_id);
        failed_message = view.findViewById(R.id.faild_lyt_id);
        radio_design_field = view.findViewById(R.id.live_button_field_id);
        recyclerView = view.findViewById(R.id.audio_clip_recycler_id);
        radio_thumbnil_field = view.findViewById(R.id.live_radio_clip_image_id);
        //radio_player1 = view.findViewById(R.id.radio_player_id);
        //radio_player1.setVisibility(View.VISIBLE);

        get_init_data();
        //((MainActivity)getActivity()).media_initial(live_radio_url);


        live_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ((MainActivity) getActivity()).change_playing(live_radio_url, live_ratio_title, "Live Sreaming");

            }
        });
    }

    private void get_init_data()
    {
        if (new NetworkInst(getContext()).isNetworkAvailable()){
            //empty_view.setVisibility(View.GONE);
           // recyclerView.setVisibility(View.VISIBLE);
            jsonParse();

        }else {
            //recyclerView.setVisibility(View.GONE);
            radio_design_field.setVisibility(View.GONE);
            failed_message.setVisibility(View.VISIBLE);
        }
        //internet_check();
    }

    private void jsonParse(){
        String url =apiResources.getRadio_url();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject myobject = response.getJSONObject("radio_info");

                            live_ratio_title = myobject.getString("title");

                            live_radio_url = myobject.getString("stream_url");

                            live_thumbnil_url = myobject.getString("thumbnail_url");


                            ((MainActivity)getActivity()).change_playing(live_radio_url,live_ratio_title,"Live Sreaming");

                           //MainActivity myobj = new MainActivity();
                           // myobj.media_initial(live_radio_url);
                            //Log.d("Radio URL", live_radio_url);
                            live_radio_title_field.setText(live_ratio_title);
                            option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
                            Glide.with(getContext()).load(live_thumbnil_url).apply(option).into(radio_thumbnil_field);

                            JSONArray radio_array = response.getJSONArray("audio_clip");

                            for (int i = 0;i<radio_array.length();i++)
                            {

                                JSONObject obj = radio_array.getJSONObject(i);
                                Radio_data_model data = new Radio_data_model();
                                data.setAudio_clip_title(obj.getString("title"));
                                data.setUrl(obj.getString("url"));
                                data.setDuration(obj.getString("duration"));
                                clip_list.add(data);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setuprecyclerview(clip_list);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(request) ;

    }


    private void setuprecyclerview(List<Radio_data_model> lst) {

        RadioAdapter myadapter = new RadioAdapter(getContext(),lst);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myadapter);

    }

}