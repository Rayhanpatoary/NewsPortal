package com.example.newsportal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.News24_Adapter;
import com.example.models.News24_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class news_fragment extends Fragment {

    private boolean isLoading=false;
    private RequestQueue requestQueue ;
    private List<News24_data_model> lst_24news ;
    private RecyclerView recyclerView ;
    SwipeRefreshLayout news24_refresh;
    ProgressBar progressBar;
    private ApiResources apiResources;
    private static int pageCount=1;
    LinearLayout empty_view;
    private Button retry_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.news24_recycler_id);
        news24_refresh = view.findViewById(R.id.news_refresher);
        progressBar = view.findViewById(R.id.item_progress_bar);
        empty_view =view.findViewById(R.id.failed_layout_id);
        retry_button = view.findViewById(R.id.btn_empty_retry_id);
        apiResources = new ApiResources(getContext());

        lst_24news = new ArrayList<News24_data_model>() ;

        get_init_data();

       retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news24_refresh.setRefreshing(true);
                lst_24news.clear();
                pageCount=1;
                get_init_data();
                news24_refresh.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {

                    pageCount++;

                    jsonParse(pageCount);

                    isLoading = true;

                    progressBar.setVisibility(View.VISIBLE);

                    Log.e("Tag","Came ! Came !");

                    //get_init_data();

                    //setuprecyclerview(lst_24news);

                }
            }
        });

        news24_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                lst_24news.clear();
                pageCount=1;
                get_init_data();
                news24_refresh.setRefreshing(false);
            }
        });

    }

    private void get_init_data(){
        if (new NetworkInst(getContext()).isNetworkAvailable()){
            empty_view.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            jsonParse(pageCount);

        }else {
            recyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }
        //internet_check();
    }

    private void jsonParse(int pageCount) {


        String url =apiResources.getNews24_url()+String.valueOf(pageCount);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        isLoading = false;
                        try {
                            for (int i = 0 ; i<response.length();i++) {
                                JSONObject myobject = response.getJSONObject(i);
                                News24_data_model data = new News24_data_model();
                                data.setNews_id(Integer.toString(myobject.getInt("news_id")));
                                data.setHeadline(myobject.getString("title"));
                                data.setDetails(myobject.getString("description"));
                                data.setNews_type(myobject.getString("type"));
                                data.setThumbin_url(myobject.getString("thumbnail_url"));
                                data.setPublished_time(myobject.getString("publish_datetime"));
                                lst_24news.add(data);
                            }
                            //isLoading=false;
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        setuprecyclerview(lst_24news);


                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(request) ;

    }

    private void setuprecyclerview(List<News24_data_model> lstdata)
    {

       // Log.e("Problem is Here!" , " Calling again !");
        recyclerView.setNestedScrollingEnabled(false);

        News24_Adapter myadapter = new News24_Adapter(getContext(),lstdata) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myadapter);

    }


}
