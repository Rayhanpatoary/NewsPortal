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
import com.example.models.Related_news_model;
import com.example.newsportal.BuildConfig;
import com.example.newsportal.News_details;
import com.example.newsportal.R;

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.myViewHolder> {


    private Context mContext ;
    private List<Related_news_model> releted_data_list  ;
    private RequestOptions option;

    public RelatedNewsAdapter(Context mContext, List<Related_news_model> data_list){
        this.mContext = mContext;
        this.releted_data_list = data_list;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }


    @NonNull
    @Override
    public RelatedNewsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_24_7_single,viewGroup,false) ;
        final RelatedNewsAdapter.myViewHolder viewHolder = new RelatedNewsAdapter.myViewHolder(view) ;
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RelatedNewsAdapter.myViewHolder myViewHolder, final int i) {
         Glide.with(mContext).load(releted_data_list.get(i).getImage_url()).apply(option).into(myViewHolder.news_image);
         myViewHolder.title_field.setText(releted_data_list.get(i).getTitle());
         myViewHolder.published_time_field.setText(releted_data_list.get(i).getPublished_time());
         myViewHolder.details_field.setText(releted_data_list.get(i).getDescription());
        String type_text = releted_data_list.get(i).getType();

        myViewHolder.share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = releted_data_list.get(i).getTitle() + "\n" + "\n" +"I would like to share this. \n"
                        +" Here you can download this from playstore : \n" + "\n" + "https://play.google.com/store/apps/details?id=" +
                        BuildConfig.APPLICATION_ID ;
                String shareSub = "Share app";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        if (type_text.equals("video"))
        {
            //Log.e("Tag","Came Here");
            myViewHolder.video_symbol.setVisibility(View.VISIBLE);
        }


        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(v.getContext(), News_details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                intent.putExtra("news_id",releted_data_list.get(i).getId());
                //String id = releted_data_list.get(i).getNews_id();
                //Log.e("News Id : >>" , String.valueOf(id));
                intent.putExtra("news_type",releted_data_list.get(i).getType());
                intent.putExtra("headline_text",releted_data_list.get(i).getTitle());
                intent.putExtra("published_time",releted_data_list.get(i).getPublished_time());
                intent.putExtra("image_url",releted_data_list.get(i).getImage_url());
                intent.putExtra("description_text",releted_data_list.get(i).getDescription());
                mContext.startActivity(intent);
            }

        });

    }


    @Override
    public int getItemCount() {
        return releted_data_list.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView title_field,details_field,published_time_field;
        ImageView news_image;
        ImageView share_icon;
        ImageView video_symbol;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            container =itemView.findViewById(R.id.news24_container);
            news_image = itemView.findViewById(R.id.small_news_image_id);
            video_symbol = itemView.findViewById(R.id.video_simble_id);
            title_field=itemView.findViewById(R.id.small_news_headline_id);
            share_icon=itemView.findViewById(R.id.share_news_button_id);
            details_field=itemView.findViewById(R.id.small_news_details_id);
            published_time_field = itemView.findViewById(R.id.published_time_field_id);
        }
    }





}
