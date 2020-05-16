package com.technicalnisith.covid19updates.model;

import java.util.List;

public class AllEffectedCountriesModel {

    private List<CountriesInfoModel> countries_stat;
    private String statistic_taken_at;


    public AllEffectedCountriesModel(List<CountriesInfoModel> countries_stat, String statistic_taken_at) {
        this.countries_stat = countries_stat;
        this.statistic_taken_at = statistic_taken_at;
    }

    public List<CountriesInfoModel> getAllEffectedCountriesDetaildList() {
        return countries_stat;
    }

    public String getUpdatedDate() {
        return statistic_taken_at;
    }
}
