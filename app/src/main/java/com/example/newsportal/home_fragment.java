package com.example.newsportal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.HomeAdapter;
import com.example.models.home_data_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class home_fragment extends Fragment {

    private RequestQueue requestQueue ;
    private List<home_data_model> lst_news ;
    private RecyclerView recyclerView ;

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

        lst_news = new ArrayList<>() ;
        jsonParse();






    }



    private void jsonParse() {


        String url = getString(R.string.Home_url_link);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("latest_news");


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);

                               home_data_model data = new home_data_model();

                                data.setHeadline(myobject.getString("title"));
                                data.setDescription(myobject.getString("description"));
                                data.setType(myobject.getString("type"));
                                data.setThubnail_url(myobject.getString("thumbnail_url"));
                                data.setIndex_in_array(i);

                                lst_news.add(data);

                            }
                            jsonArray = response.getJSONArray("popular_news");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject myobject = jsonArray.getJSONObject(i);

                                home_data_model data = new home_data_model();
                                data.setHeadline(myobject.getString("title"));
                                data.setDescription(myobject.getString("description"));
                                data.setType(myobject.getString("type"));
                                data.setThubnail_url(myobject.getString("thumbnail_url"));
                                data.setIndex_in_array(i);

                                lst_news.add(data);

                            }

                            jsonArray = response.getJSONArray("featured_category_with_news");
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject myobject = jsonArray.getJSONObject(i);
                                home_data_model data = new home_data_model();
                                data.setNews_category(myobject.getString("title"));
                                lst_news.add(data);

                                JSONArray news_nested_array = myobject.getJSONArray("news");

                                for (int k =0 ;k<=news_nested_array.length();k++){
                                    JSONObject myobject2 = news_nested_array.getJSONObject(i);
                                    home_data_model data2 = new home_data_model();
                                    data2.setHeadline(myobject2.getString("title"));
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
    private void setuprecyclerview(List<home_data_model> lstAnime) {

        HomeAdapter myadapter = new HomeAdapter(getContext(),lstAnime) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(myadapter);

    }





}
