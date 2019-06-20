package com.example.newsportal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.menu_items_adapter;
import com.example.models.menu_data_model;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link menu_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link menu_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu_fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private menu_items_adapter menu_adepter;
    private menu_items_adapter menu_adepter2;
    private List menu_list =new ArrayList<>();
    private List menu_category_list =new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_id);
        recyclerView2 = view.findViewById(R.id.recycler_view2_id);

        menu_adepter = new menu_items_adapter(menu_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(menu_adepter);
        list_data_prepare();

        menu_adepter2 = new menu_items_adapter(menu_category_list);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView2.setAdapter(menu_adepter2);
        category_data_prepare();

    }
    private void list_data_prepare(){
        menu_data_model data=new menu_data_model("Video");
        menu_list.add(data);
        data = new menu_data_model("Sample text 2");
        menu_list.add(data);
        data=new menu_data_model("Sample text3");
        menu_list.add(data);
        data = new menu_data_model("Sample text 4");
        menu_list.add(data);
        data=new menu_data_model("Sample text5");
        menu_list.add(data);
        data = new menu_data_model("Sample text 6");
        menu_list.add(data);
        data = new menu_data_model("Sample text 2");
        menu_list.add(data);
        data=new menu_data_model("Sample text3");
        menu_list.add(data);
        data = new menu_data_model("Sample text 4");
        menu_list.add(data);
        data=new menu_data_model("Sample text5");
        menu_list.add(data);
        data = new menu_data_model("Sample text 6");
        menu_list.add(data);

    }
    private void category_data_prepare(){

        menu_data_model data=new menu_data_model("Politics");
        menu_category_list.add(data);
        data = new menu_data_model("International");
        menu_category_list.add(data);
        data = new menu_data_model("Police & Justice");
        menu_category_list.add(data);
        data = new menu_data_model("Economics");
        menu_category_list.add(data);
        data = new menu_data_model("Entertainment");
        menu_category_list.add(data);
        data = new menu_data_model("Sports");
        menu_category_list.add(data);

    }



    }
