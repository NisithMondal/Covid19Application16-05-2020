package com.nisith.covid19application.model;

public class CountryInfoSearchHistoryModel {

    private long total_cases;
    private long deaths;
    private long recovered;
    private long critical;
    private long tested;
    private double death_ratio;
    private double recovery_ratio;

    public CountryInfoSearchHistoryModel(long total_cases, long deaths, long recovered, long critical, long tested, double death_ratio, double recovery_ratio) {
        this.total_cases = total_cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.critical = critical;
        this.tested = tested;
        this.death_ratio = death_ratio;
        this.recovery_ratio = recovery_ratio;
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
