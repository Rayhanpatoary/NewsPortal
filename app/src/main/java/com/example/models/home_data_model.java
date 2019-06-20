package com.example.models;

import android.util.Log;

public class home_data_model {


    public String getNews_category() {
        return news_category;
    }

    public void setNews_category(String news_category) {
        this.news_category = news_category;
    }

    public String news_category, Headline , description,type,thubnail_url;
    int index_in_array;

    public int getIndex_in_array() {
        return index_in_array;
    }

    public void setIndex_in_array(int index_in_array) {
        this.index_in_array = index_in_array;
    }



    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThubnail_url() {
        return thubnail_url;
    }

    public void setThubnail_url(String thubnail_url) {
        this.thubnail_url = thubnail_url;
    }

    public home_data_model(String headline, String description, String type, String thubnail_url) {
        Headline = headline;
        this.description = description;
        this.type = type;
        this.thubnail_url = thubnail_url;
    }

    public home_data_model() {

    }
}
