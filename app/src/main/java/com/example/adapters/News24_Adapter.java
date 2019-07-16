package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.models.News24_data_model;
import com.example.newsportal.BuildConfig;
import com.example.newsportal.News_details;
import com.example.newsportal.R;

import java.util.List;

public class News24_Adapter extends RecyclerView.Adapter<News24_Adapter.news24ViewHolder> {

    private Context mContext ;
    private List<News24_data_model> news24_data  ;
    private RequestOptions option;

    public News24_Adapter(Context mContext, List<News24_data_model> news24_data) {

        this.mContext = mContext;
        this.news24_data = news24_data;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public news24ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_24_7_single,parent,false) ;
        final news24ViewHolder viewHolder = new news24ViewHolder(view) ;

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(news24ViewHolder holder, final int i)
    {

        //holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        Glide.with(mContext).load(news24_data.get(i).getThumbin_url()).apply(option).into(holder.news_image);
        holder.headline_field.setText(news24_data.get(i).getHeadline());
        holder.details_field.setText(news24_data.get(i).getDetails());
        holder.published_time_field.setText(news24_data.get(i).getPublished_time());

        holder.share_news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = news24_data.get(i).getHeadline() + "\n" + "\n" +"I would like to share this. \n"
                        +" Here you can download this from playstore : \n" + "\n" + "https://play.google.com/store/apps/details?id=" +
                        BuildConfig.APPLICATION_ID ;
                String shareSub = "Share app";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), News_details.class);intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);intent.putExtra("news_id",news24_data.get(i).getNews_id());
                intent.putExtra("news_type",news24_data.get(i).getNews_type());
                intent.putExtra("headline_text",news24_data.get(i).getHeadline());
                intent.putExtra("published_time",news24_data.get(i).getPublished_time());
                intent.putExtra("image_url",news24_data.get(i).getThumbin_url());
                intent.putExtra("description_text",news24_data.get(i).getDetails());
                mContext.startActivity(intent);

            }
        });

        if (news24_data.get(i).getNews_type().equals("video"))
        {
            //Log.e("Tag","Came Here");
            holder.video_simble_image.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return news24_data.size();
    }

    public static class news24ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        ImageView news_image;
        TextView headline_field,details_field;
        ImageView video_simble_image;
        ImageView share_news_button;
        TextView published_time_field;


        public news24ViewHolder(View view) {

            super(view);
            container = view.findViewById(R.id.news24_container);
            news_image = view.findViewById(R.id.small_news_image_id);
            headline_field = view.findViewById(R.id.small_news_headline_id);
            video_simble_image = view.findViewById(R.id.video_simble_id);
            share_news_button = view.findViewById(R.id.share_news_button_id);
            details_field = view.findViewById(R.id.small_news_details_id);
            published_time_field = view.findViewById(R.id.published_time_field_id);


        }
    }
}
