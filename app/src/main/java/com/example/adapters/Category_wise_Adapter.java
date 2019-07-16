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
import com.example.models.Category_wise_model;
import com.example.newsportal.News_details;
import com.example.newsportal.R;

import java.util.List;

public class Category_wise_Adapter extends RecyclerView.Adapter<Category_wise_Adapter.MyViewHolder> {

    private Context mContext ;
    private List<Category_wise_model> category_data_list  ;
    private RequestOptions option;
    public Category_wise_Adapter(Context mContext, List<Category_wise_model> data_list)
    {
        this.mContext = mContext;
        this.category_data_list = data_list;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.video_clip_single,viewGroup,false) ;
        final Category_wise_Adapter.MyViewHolder viewHolder = new Category_wise_Adapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.news_title.setText(category_data_list.get(i).getTitle());

        Glide.with(mContext).load(category_data_list.get(i).getThumbnail_url()).apply(option).into(myViewHolder.news_image);


           // Log.e("Tag","Came Here");
            //myViewHolder.video_simble_image.setVisibility(View.VISIBLE);

        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), News_details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("news_id",category_data_list.get(i).getNews_id());
                intent.putExtra("news_type",category_data_list.get(i).getNews_type());
                intent.putExtra("headline_text",category_data_list.get(i).getTitle());
                intent.putExtra("published_time",category_data_list.get(i).getPublished_time());
                intent.putExtra("image_url",category_data_list.get(i).getThumbnail_url());
                intent.putExtra("description_text",category_data_list.get(i).getDescription());
                mContext.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return category_data_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout container ;
        ImageView news_image;
        TextView news_title;
        ImageView video_simble_image;
        TextView published_time_field;

        public MyViewHolder(View view)
        {

            super(view);
            news_image = view.findViewById(R.id.video_clip_image_id);
            news_title = view.findViewById(R.id.video_clip_headline);
            container = view.findViewById(R.id.video_clip_container_id);
            video_simble_image = view.findViewById(R.id.video_simble_id);
        }

    }

}
