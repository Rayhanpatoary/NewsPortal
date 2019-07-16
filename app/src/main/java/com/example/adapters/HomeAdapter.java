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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.models.Home_data_model;
import com.example.newsportal.News_details;
import com.example.newsportal.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {



    private Context mContext ;
    private List<Home_data_model> homeData ;
    private RequestOptions option;

    public HomeAdapter(Context mContext, List<Home_data_model> homeData) {
        this.mContext = mContext;
        this.homeData = homeData;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.home_view_single,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        /*
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, AnimeActivity.class);
                i.putExtra("anime_name",mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("anime_studio",mData.get(viewHolder.getAdapterPosition()).getStudio());
                i.putExtra("anime_category",mData.get(viewHolder.getAdapterPosition()).getCategorie());
                i.putExtra("anime_nb_episode",mData.get(viewHolder.getAdapterPosition()).getNb_episode());
                i.putExtra("anime_rating",mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);

            }
        }); */

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //set animation
       // holder.view_container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        //#008577


        if (homeData.get(position).getNews_category()!= null)
        {
            holder.cagegory_text.setVisibility(View.VISIBLE);
            holder.cagegory_text.setText(homeData.get(position).getNews_category());
            holder.big_news_field.setVisibility(View.GONE);
            holder.small_news_field.setVisibility(View.GONE);
        }
       else if(homeData.get(position).getIndex_in_array()==0)
       {
            holder.big_news_field.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(homeData.get(position).getThubnail_url()).apply(option).into(holder.big_news_image);
            //Glide.with(mContext).load(homeData.get(position).getThubnail_url()).apply(option).into(holder.big_news_field.setBackgroundResource());
            holder.big_news_headline.setText(homeData.get(position).getHeadline());
            holder.small_news_field.setVisibility(View.GONE);
        }
        else{

            //set small news items
            holder.small_news_headline.setText(homeData.get(position).getHeadline());
            Glide.with(mContext).load(homeData.get(position).getThubnail_url()).apply(option).into(holder.small_image);
            String type_text = homeData.get(position).getType();
            if (type_text.equals("video"))
            {
                //Log.e("Tag","Came Here");

                holder.video_symbol.setVisibility(View.VISIBLE);
            }
        }
        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (homeData.get(position).getNews_category()== null)
                {
                    Intent i = new Intent(v.getContext(), News_details.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    i.putExtra("news_id",homeData.get(position).getNews_id());
                    i.putExtra("news_type",homeData.get(position).getType());
                    i.putExtra("published_time",homeData.get(position).getPublished_time());
                    i.putExtra("headline_text",homeData.get(position).getHeadline());
                    i.putExtra("image_url",homeData.get(position).getThubnail_url());
                    i.putExtra("description_text",homeData.get(position).getDescription());
                    mContext.startActivity(i);
                }

            }

        });



    }

    @Override
    public int getItemCount() {
        return homeData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout view_container;
        TextView cagegory_text;
        RelativeLayout big_news_field;
        ImageView big_news_image;
        TextView big_news_headline ;
        LinearLayout small_news_field;
        ImageView small_image;
        ImageView video_symbol;
        TextView small_news_headline;

        public MyViewHolder(View itemView) {

            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            cagegory_text = itemView.findViewById(R.id.category_text_id);
            big_news_field=itemView.findViewById(R.id.big_news_id);
            big_news_image = itemView.findViewById(R.id.big_news_image_id);
            big_news_headline = itemView.findViewById(R.id.news_big_headline);
            small_news_field = itemView.findViewById(R.id.small_news_id);
            small_image = itemView.findViewById(R.id.small_news_image_id);
            video_symbol = itemView.findViewById(R.id.video_simble_id);
            small_news_headline = itemView.findViewById(R.id.small_news_headline_id);

        }
    }

}
