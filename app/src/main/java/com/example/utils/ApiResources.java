package com.example.utils;


import android.content.Context;

import com.example.newsportal.R;


public class ApiResources {

    String URL;

    String API_SECRECT_KEY;

    String home_url;

    String news24_url;

    String radio_url;

    String tv_content_url;

    String single_news_url ;

    String menu_list_url;

    String menu_wise_url;

    String videoes_type_url , in_emission_type_url;


    public ApiResources(Context context){

        URL = context.getResources().getString(R.string.api_url);

        API_SECRECT_KEY = "api_secret_key="+ context.getString(R.string.api_key);

        home_url = URL+"get_home_content?"+API_SECRECT_KEY;

        news24_url = URL+"get_news?"+API_SECRECT_KEY+"&page=";

        tv_content_url =URL+"get_tv_content?"+API_SECRECT_KEY;

        radio_url = URL+"get_radio_content?"+API_SECRECT_KEY;

        single_news_url = URL+"get_single_news_details?"+API_SECRECT_KEY;

        menu_list_url = URL+"get_all_category?"+API_SECRECT_KEY;

        menu_wise_url = URL+"get_news_by_category_id?"+API_SECRECT_KEY+"&id=";

        videoes_type_url = URL+"get_video_news?"+ API_SECRECT_KEY;

        in_emission_type_url = URL+"get_in_emission?"+API_SECRECT_KEY;

    }


    public String getHome_url() {
        return home_url;
    }

    public String getNews24_url() {
        return news24_url;
    }
    public String getTv_content_url() {

        return tv_content_url;
    }

    public String getRadio_url() {
        return radio_url;
    }

    public String getSingle_news_url(String id) {
        return single_news_url+"&id="+id ;
    }

    public String getMenu_list_url() {
        return menu_list_url;
    }

    public String getMenu_wise_url() {
        return menu_wise_url;
    }

    public String getVideoes_type_url() {
        return videoes_type_url;
    }

    public void setVideoes_type_url(String videoes_type_url) {
        this.videoes_type_url = videoes_type_url;
    }

    public String getIn_emission_type_url() {
        return in_emission_type_url;
    }

    public void setIn_emission_type_url(String in_emission_type_url) {
        this.in_emission_type_url = in_emission_type_url;
    }
}
