package com.example.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.models.Radio_data_model;
import com.example.newsportal.MainActivity;
import com.example.newsportal.R;

import java.util.List;

public class RadioAdapter extends  RecyclerView.Adapter<RadioAdapter.MyViewHolder>
{
    private Context mContext ;
    private List<Radio_data_model> data_list  ;
    private RequestOptions option;

     public RadioAdapter(Context mContext, List<Radio_data_model> data_list) {
        this.mContext = mContext;
        this.data_list = data_list;
         option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.radio_clip_single,viewGroup,false) ;
        final RadioAdapter.MyViewHolder viewHolder = new RadioAdapter.MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.title_field.setText(data_list.get(i).getAudio_clip_title());
        myViewHolder.duration_field.setText(data_list.get(i).getDuration());
        myViewHolder.radio_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          ((MainActivity)mContext).change_playing(data_list.get(i).getUrl(),data_list.get(i).getAudio_clip_title(),data_list.get(i).getDuration());

            }
        });

    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_field , duration_field;
        LinearLayout radio_container;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title_field = view.findViewById(R.id.clip_title_id);
            duration_field = view.findViewById(R.id.clip_time);
            radio_container = view.findViewById(R.id.raio_container_id);
        }
    }
}