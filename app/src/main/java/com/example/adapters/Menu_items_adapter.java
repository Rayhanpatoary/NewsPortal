package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.models.menu_data_model;
import com.example.newsportal.Each_menu_item;
import com.example.newsportal.R;

import java.util.List;

public class Menu_items_adapter extends RecyclerView.Adapter<Menu_items_adapter.Myviewholder>{

    private Context mContext ;
    private List<menu_data_model> menudata ;
    private RequestOptions option;

    public Menu_items_adapter(Context mContext, List<menu_data_model> Data) {
        this.mContext = mContext;
        this.menudata = Data;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public Menu_items_adapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.menu_item_single,viewGroup,false) ;
        final Menu_items_adapter.Myviewholder viewHolder = new Menu_items_adapter.Myviewholder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Menu_items_adapter.Myviewholder myviewholder, final int i) {
        myviewholder.title_text.setText(menudata.get(i).getTitle_text());

        myviewholder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), Each_menu_item.class);
                intent.putExtra("category_name",menudata.get(i).getTitle_text());
                intent.putExtra("category_id",menudata.get(i).getCategory_id());
                intent.putExtra("section","B");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menudata.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView title_text;
        LinearLayout container;

        Myviewholder(View view){
            super(view);
            title_text = view.findViewById(R.id.title_text_id);
            container = view.findViewById(R.id.lyt_menu_item_single);
        }
    }

}

