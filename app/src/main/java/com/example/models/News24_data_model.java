package com.example.models;

public class News24_data_model {
    private String news_id;
    private String Headline;
    private String details;
    private String news_type;
    private String thumbin_url;
    private String published_time;

    public News24_data_model(String id, String headline, String details, String news_type, String thumbin_url) {
        this.news_id = id;
        this.Headline = headline;
        this.details = details;
        this.news_type = news_type;
        this.thumbin_url = thumbin_url;
    }
    public News24_data_model(){

    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNews_type() {
        return news_type;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public String getThumbin_url() {
        return thumbin_url;
    }

    public void setThumbin_url(String thumbin_url) {
        this.thumbin_url = thumbin_url;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }
}
