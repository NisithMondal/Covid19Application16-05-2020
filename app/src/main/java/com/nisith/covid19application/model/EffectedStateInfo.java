package com.nisith.covid19application.model;

public class EffectedStateInfo {
    private String stateName;
    private String activeCases;
    private String confirmedCases;
    private String totalRecovered;
    private String totalDeaths;


    public EffectedStateInfo(String stateName, String activeCases, String confirmedCases, String totalRecovered, String totalDeaths) {
        this.stateName = stateName;
        this.activeCases = activeCases;
        this.confirmedCases = confirmedCases;
        this.totalRecovered = totalRecovered;
        this.totalDeaths = totalDeaths;
    }


    public String getStateName() {
        return stateName;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }
}
