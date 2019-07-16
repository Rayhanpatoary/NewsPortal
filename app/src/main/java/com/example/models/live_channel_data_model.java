package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class live_channel_data_model {
        String category_name;
        List<Category_wise_model> list_data = new ArrayList();

   public live_channel_data_model(){

    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Category_wise_model> getList_data() {
        return list_data;
    }

    public void setList_data(List<Category_wise_model> list_data) {
        this.list_data = list_data;
    }

}
