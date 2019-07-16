package com.example.newsportal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.HomeAdapter;
import com.example.models.Home_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home_fragment extends Fragment {

    private RequestQueue requestQueue ;
    private List<Home_data_model> lst_news ;
    private RecyclerView recyclerView ;
    SwipeRefreshLayout home_refresh;
    private ApiResources apiResources;
    LinearLayout empty_view;
    private Button retry_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.home_recycler_id);
        home_refresh = view.findViewById(R.id.refresher);
        empty_view =view.findViewById(R.id.failed_layout_id);
        retry_button = view.findViewById(R.id.btn_empty_retry_id);

        lst_news = new ArrayList<>() ;
        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_refresh.setRefreshing(true);
                recyclerView.setVisibility(View.VISIBLE);
                lst_news.clear();
                get_init_data();
                home_refresh.setRefreshing(false);
            }
        });

        home_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setVisibility(View.VISIBLE);
                lst_news.clear();
                get_init_data();
                home_refresh.setRefreshing(false);

            }
        });

        get_init_data();







    }

    private void get_init_data(){
        internet_check();
    }



    private void jsonParse() {


        //String url = new ApiResources().getHome_url();
        apiResources = new ApiResources(getContext());

        String url = apiResources.getHome_url();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("latest_news");


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);

                                Home_data_model data = new Home_data_model();
                                data.setNews_id(Integer.toString(myobject.getInt("news_id")));
                                data.setHeadline(myobject.getString("title"));
                                data.setPublished_time(myobject.getString("publish_datetime"));
                                data.setDescription(myobject.getString("description"));
                                data.setType(myobject.getString("type"));
                                data.setThubnail_url(myobject.getString("thumbnail_url"));
                                data.setIndex_in_array(i);

                                lst_news.add(data);

                            }
                            jsonArray = response.getJSONArray("popular_news");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);
                                Home_data_model data = new Home_data_model();
                                data.setNews_id(Integer.toString(myobject.getInt("news_id")));
                                data.setHeadline(myobject.getString("title"));
                                data.setDescription(myobject.getString("description"));
                                data.setType(myobject.getString("type"));
                                data.setThubnail_url(myobject.getString("thumbnail_url"));
                                data.setIndex_in_array(i);
                                lst_news.add(data);

                            }

                            jsonArray = response.getJSONArray("featured_category_with_news");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);
                                Home_data_model data = new Home_data_model();
                                data.setNews_category(myobject.getString("title"));
                                lst_news.add(data);

                                JSONArray news_nested_array = myobject.getJSONArray("news");

                                for (int k =0 ;k<news_nested_array.length();k++){

                                    JSONObject myobject2 = news_nested_array.getJSONObject(k);
                                    Home_data_model data2 = new Home_data_model();
                                    data2.setNews_id(Integer.toString(myobject2.getInt("news_id")));
                                    data2.setHeadline(myobject2.getString("title"));
                                    //data2.setPublished_time(myobject2.getString("publish_datetime"));
                                    data2.setDescription(myobject2.getString("description"));
                                    data2.setType(myobject2.getString("type"));
                                    data2.setThubnail_url(myobject2.getString("thumbnail_url"));
                                    data2.setIndex_in_array(k);
                                    lst_news.add(data2);
                                }
                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        setuprecyclerview(lst_news);

                    }



                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(request) ;


    }
    private void setuprecyclerview(List<Home_data_model> lstAnime) {

        HomeAdapter myadapter = new HomeAdapter(getContext(),lstAnime) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myadapter);

    }

    void internet_check(){

        if (new NetworkInst(getContext()).isNetworkAvailable()){
            jsonParse();

        }else {
            recyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }

    }





}
