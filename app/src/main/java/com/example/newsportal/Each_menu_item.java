package com.example.newsportal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adapters.Each_menu_Adaper;
import com.example.models.News24_data_model;
import com.example.utils.ApiResources;
import com.example.utils.NetworkInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.newsportal.R.color.colorPrimary;

public class Each_menu_item extends AppCompatActivity {
    List<News24_data_model> newslist;
    private RequestQueue requestQueue ;
    RecyclerView news_recycler;
    private ApiResources apiResources;
    String url,category_id,category_name , section;

    private ImageView back_image;
    TextView tootlbar_title;
    LinearLayout network_failed_part,not_found_part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_show);
        news_recycler = findViewById(R.id.category_wise_show_recycler_id);
        tootlbar_title = findViewById(R.id.toolbar_title_id);
        network_failed_part = findViewById(R.id.failed_layout_id);
        not_found_part = findViewById(R.id.no_data_part_id);

        apiResources = new ApiResources(this);

        Intent intent = getIntent();
        back_image=findViewById(R.id.img_back);
        category_name = intent.getStringExtra("category_name");
        category_id = intent.getStringExtra("category_id");
        section = intent.getStringExtra("section");


        tootlbar_title.setText(category_name);
        newslist = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(colorPrimary));
        }
        if (new NetworkInst(this).isNetworkAvailable()){
            get_news_data();

        }
        else
         {
            network_failed_part.setVisibility(View.VISIBLE);
        }



        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void get_news_data() {

        if (section.equals("A")){

            if (category_id.equals("1"))
            {
                url = apiResources.getVideoes_type_url();
            }

            else if (category_id.equals("2"))
            {
                url = apiResources.getIn_emission_type_url();
            }

        }
        else {
               url = apiResources.getMenu_wise_url() + category_id;
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0 ; i<response.length();i++) {
                                JSONObject myobject = response.getJSONObject(i);
                                News24_data_model data = new News24_data_model();
                                data.setNews_id(Integer.toString(myobject.getInt("news_id")));
                                data.setPublished_time(myobject.getString("publish_datetime"));
                                data.setHeadline(myobject.getString("title"));
                                data.setDetails(myobject.getString("description"));
                                data.setNews_type(myobject.getString("type"));
                                data.setThumbin_url(myobject.getString("thumbnail_url"));
                                newslist.add(data);
                            }
                            //isLoading=false;

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                        if (newslist.size()==0){

                            not_found_part.setVisibility(View.VISIBLE);

                        }

                        setuprecyclerview(newslist);


                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(request) ;

    }


    private void setuprecyclerview(List<News24_data_model> lstdata)
    {

        // Log.e("Problem is Here!" , " Calling again !");
        news_recycler.setNestedScrollingEnabled(false);

        Each_menu_Adaper myadapter = new Each_menu_Adaper(this,lstdata) ;

        news_recycler.setLayoutManager(new LinearLayoutManager(this));

        news_recycler.setAdapter(myadapter);

    }

}




