package com.example.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.models.menu_data_model;
import com.example.newsportal.R;

import java.util.List;

    public class menu_items_adapter  extends RecyclerView.Adapter {

    List menu_dataList;
    TextView title_text;


    public menu_items_adapter(List DataList) {
        this.menu_dataList=DataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("ACTIVITY:::","Come Here!");

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item_single, viewGroup, false);
        return new MyViewHolder(itemView);
    }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            menu_data_model data= (menu_data_model) menu_dataList.get(i);
            title_text = viewHolder.itemView.findViewById(R.id.title_text);
            title_text.setText(data.title_text);

        }


       /* @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        menu_data_model data= (menu_data_model) menu_dataList.get(i);
        //viewHolder.title_text.setText(data.title_text);

        viewHolder.title_text.setText(data.title_text);


    }      */

    @Override
    public int getItemCount() {
        return menu_dataList.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            //title_text = itemView.findViewById(R.id.title_text);

        }
    }
}
