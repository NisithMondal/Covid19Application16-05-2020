package com.nisith.covid19application.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EffectedCountriesSearchHistoryModel {
    private int status;
    private String type;
    private JsonObject data;

    public EffectedCountriesSearchHistoryModel(int status, String type, JsonObject data) {
        this.status = status;
        this.type = type;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public List<CountryInfoSearchHistoryModel> getReportList() {
        List<CountryInfoSearchHistoryModel> itemList = new ArrayList<>();
        Set<String> keys = data.keySet();
        Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                     .create();
        int position = 0;
        for (String key : keys) {
            JsonObject jsonObject = data.getAsJsonObject(key);
            itemList.add(gson.fromJson(jsonObject, CountryInfoSearchHistoryModel.class));
            itemList.get(position).setDate(key);// here key contains date
            position++;


        }



        return itemList;
    }
}
