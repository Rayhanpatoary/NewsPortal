package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class Each_menu_Adaper  extends RecyclerView.Adapter<Each_menu_Adaper.MyViewHolder> {

    private Context mContext ;
    private List<News24_data_model> listData ;

    private RequestOptions option;

    public Each_menu_Adaper(Context mContext, List<News24_data_model> news_data)
    {

        this.mContext = mContext;

        this.listData = news_data;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_24_7_single,viewGroup,false) ;
        final Each_menu_Adaper.MyViewHolder viewHolder = new Each_menu_Adaper.MyViewHolder(view) ;
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        Glide.with(mContext).load(listData.get(i).getThumbin_url()).apply(option).into(myViewHolder.news_image);

        myViewHolder.details_field.setText(listData.get(i).getDetails());

        myViewHolder.headline_field.setText(listData.get(i).getHeadline());

        myViewHolder.published_time.setText(listData.get(i).getPublished_time());

        if(listData.get(i).getNews_type().equals("video")){
            myViewHolder.video_simble_image.setVisibility(View.VISIBLE);
        }

        myViewHolder.share_news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =listData.get(i).getHeadline() + "\n" + "\n" +"I would like to share this. \n"
                        +" Here you can download this from playstore : \n" + "\n" + "https://play.google.com/store/apps/details?id=" +
                        BuildConfig.APPLICATION_ID ;
                String shareSub = "Share app";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), News_details.class);intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);intent.putExtra("news_id",listData.get(i).getNews_id());
                intent.putExtra("news_type",listData.get(i).getNews_type());
                intent.putExtra("headline_text",listData.get(i).getHeadline());
                intent.putExtra("published_time",listData.get(i).getPublished_time());
                intent.putExtra("image_url",listData.get(i).getThumbin_url());
                intent.putExtra("description_text",listData.get(i).getDetails());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        ImageView news_image;
        TextView headline_field,details_field,published_time;
        ImageView video_simble_image;
        ImageView share_news_button;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            container = itemView.findViewById(R.id.news24_container);

            news_image = itemView.findViewById(R.id.small_news_image_id);

            headline_field = itemView.findViewById(R.id.small_news_headline_id);

            video_simble_image = itemView.findViewById(R.id.video_simble_id);

            share_news_button = itemView.findViewById(R.id.share_news_button_id);

            details_field = itemView.findViewById(R.id.small_news_details_id);

            published_time = itemView.findViewById(R.id.published_time_field_id);

        }
    }
}
