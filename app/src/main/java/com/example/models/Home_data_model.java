package com.example.models;

public class Home_data_model {

    private String news_id, news_category, Headline , description,type,thubnail_url,published_time;

    private int index_in_array;

    public String getNews_category() {
        return news_category;
    }

    public void setNews_category(String news_category) {
        this.news_category = news_category;
    }

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

    public String getNews_id() { return news_id; }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }

    public Home_data_model(String id, String headline, String description, String type, String thubnail_url) {

        this.news_id = id;
        this.Headline = headline;
        this.description = description;
        this.type = type;
        this.thubnail_url = thubnail_url;
    }

    public Home_data_model() {

    }


}
