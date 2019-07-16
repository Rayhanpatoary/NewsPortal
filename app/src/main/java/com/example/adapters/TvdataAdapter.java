package com.example.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.models.live_channel_data_model;
import com.example.newsportal.R;

import java.util.List;

public class TvdataAdapter extends RecyclerView.Adapter<TvdataAdapter.mytvViewHolder> {

    private Context mContext ;
    private List<live_channel_data_model> tv_data_list  ;
    private RequestOptions option;

    public TvdataAdapter(Context mContext, List<live_channel_data_model> data_list){
        this.mContext = mContext;
        this.tv_data_list = data_list;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public mytvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.tv_news_single,viewGroup,false) ;
        final TvdataAdapter.mytvViewHolder viewHolder = new TvdataAdapter.mytvViewHolder(view) ;
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull mytvViewHolder mytvViewHolder, int i) {

            mytvViewHolder.category_title_field.setText(tv_data_list.get(i).getCategory_name());
            Category_wise_Adapter myadapter = new Category_wise_Adapter (mContext,tv_data_list.get(i).getList_data()) ;
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mytvViewHolder.category_video_clip_recycler.setLayoutManager(horizontalLayoutManagaer);
            //mytvViewHolder.category_video_clip_recycler.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
            mytvViewHolder.category_video_clip_recycler.setAdapter(myadapter);
    }

    @Override
    public int getItemCount() {
       return tv_data_list.size();
    }

    public static class mytvViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView category_title_field;
        RecyclerView category_video_clip_recycler;

        public mytvViewHolder(View view) {

            super(view);
            category_title_field = view.findViewById(R.id.category_title_text_id);
            category_video_clip_recycler = view.findViewById(R.id.category_recycler_id);
        }
    }

    /*
    private void setuprecyclerview(List<live_channel_data_model> lstdata)
    {

        Category_wise_Adapter myadapter = new Category_wise_Adapter (mContext,lstdata) ;

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        category_video_clip_recycler.setLayoutManager(horizontalLayoutManagaer);

        category_video_clip_recycler.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));


        category_video_clip_recycler.setAdapter(myadapter);

    } */

}