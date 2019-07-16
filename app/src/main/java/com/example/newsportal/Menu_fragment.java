package com.example.newsportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.Menu_items_adapter;
import com.example.models.menu_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Menu_fragment extends Fragment {

    private RecyclerView recyclerView;
    private Menu_items_adapter menu_adepter;
    private Menu_items_adapter menu_adepter2;
    private List<menu_data_model> menu_list;
    private RequestQueue requestQueue ;
    private LinearLayout news_type1_field,news_type2_field,news_type3_field,news_type4_field;
    private LinearLayout item_type1_field,item_type2_field,item_type3_field,item_type4_field;
    private ApiResources apiResources;
    private TextView category1_name,category2_name,category3_name,category4_name;
    private LinearLayout empty_view,fragment_part;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        menu_list = new ArrayList<>();
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view2_id);
        news_type1_field=view.findViewById(R.id.news_type1_id);
        news_type2_field=view.findViewById(R.id.news_type2_id);
        news_type3_field = view.findViewById(R.id.news_type3_id);
        news_type4_field = view.findViewById(R.id.news_type4_id);
        category1_name = news_type1_field.findViewById(R.id.title_text_id);
        category2_name = news_type2_field.findViewById(R.id.title_text_id);
        category3_name = news_type3_field.findViewById(R.id.title_text_id);
        category4_name = news_type4_field.findViewById(R.id.title_text_id);
        item_type1_field = news_type1_field.findViewById(R.id.lyt_menu_item_single);
        item_type2_field = news_type2_field.findViewById(R.id.lyt_menu_item_single);
        item_type3_field = news_type3_field.findViewById(R.id.lyt_menu_item_single);
        item_type4_field = news_type4_field.findViewById(R.id.lyt_menu_item_single);
        menu_adepter2 = new Menu_items_adapter(getContext(),menu_list);
        empty_view = view.findViewById(R.id.failed_layout_id);
        fragment_part = view.findViewById(R.id.fragment_part_id);

        item_type1_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Each_menu_item.class);
                intent.putExtra("category_name","Video");
                intent.putExtra("category_id","1");
                intent.putExtra("section","A");
                startActivity(intent);
            }
        });
        item_type2_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Each_menu_item.class);
                intent.putExtra("category_name","In emission");
                intent.putExtra("category_id","2");
                intent.putExtra("section","A");
                startActivity(intent);
            }
        });
        item_type3_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));
            }
        });
        item_type4_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://spagreen.net")));
            }
        });


        if (new NetworkInst(getContext()).isNetworkAvailable()){

            category_data_prepare();
            category1_name.setText("Video");
            category2_name.setText("In emission");
            category3_name.setText("Link 1");
            category4_name.setText("Link2");

        }else {

            fragment_part.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);

        }


    }

    private void category_data_prepare(){
        apiResources = new ApiResources(getContext());

        String api_url = apiResources.getMenu_list_url();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, api_url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                menu_data_model data = new menu_data_model();
                                data.setCategory_id(Integer.toString(obj.getInt("category_id")));
                                data.setTitle_text(obj.getString("title"));
                                data.setDesc(obj.getString("desc"));
                                data.setFeature(Integer.toString(obj.getInt("featured")));
                                data.setStatus(Integer.toString(obj.getInt("status")));
                                menu_list.add(data);

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        setuprecyclerview(menu_list);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(request) ;

    }

    private void setuprecyclerview(List<menu_data_model> lstdata)
    {

        // Log.e("Problem is Here!" , " Calling again !");
        recyclerView.setNestedScrollingEnabled(false);

        Menu_items_adapter myadapter = new Menu_items_adapter(getContext(),lstdata) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myadapter);

    }



    }
