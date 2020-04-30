package com.nisith.covid19application.model;

import com.google.gson.annotations.Expose;

public class CountryInfoSearchHistoryModel {

    @Expose(serialize = true,deserialize = true)
    private long total_cases;
    @Expose(serialize = true,deserialize = true)
    private long deaths;
    @Expose(serialize = true,deserialize = true)
    private long recovered;
    @Expose(serialize = true,deserialize = true)
    private long critical;
    @Expose(serialize = true,deserialize = true)
    private long tested;
    @Expose(serialize = true,deserialize = true)
    private double death_ratio;
    @Expose(serialize = true,deserialize = true)
    private double recovery_ratio;
    @Expose(serialize = false,deserialize = false)
    private String date;

    public CountryInfoSearchHistoryModel(long total_cases, long deaths, long recovered, long critical, long tested, double death_ratio, double recovery_ratio) {
        this.total_cases = total_cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.critical = critical;
        this.tested = tested;
        this.death_ratio = death_ratio;
        this.recovery_ratio = recovery_ratio;
    }


    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public long getTotalCases() {
        return total_cases;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getCritical() {
        return critical;
    }

    public long getTested() {
        return tested;
    }

    public double getDeathRatio() {
        return death_ratio;
    }

    public double getRecoveryRatio() {
        return recovery_ratio;
    }
}
