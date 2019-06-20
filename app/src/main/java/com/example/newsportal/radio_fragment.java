package com.example.newsportal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link radio_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link radio_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class radio_fragment extends Fragment {
    Button live_radio_button ;
    LinearLayout audio_player;
    TextView player_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radio_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        live_radio_button = view.findViewById(R.id.live_radio_button_id);


        live_radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ACTIVITY:::","Come Here222!");
            }
        });
    }
}