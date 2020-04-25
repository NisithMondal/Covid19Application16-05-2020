package com.nisith.covid19application.model;

public class EffectedCountryInfo {
    private String countryName;
    private String totalCaases;
    private String totalCasesPerMillion;
    private String newCases;
    private String activeCases;
    private String totalDeaths;
    private String newDeaths;
    private String totalRecovered;
    private String seriousCritical;
    private String deathsPerMillion;
    private String totalTest;
    private String totalTestPerMillion;
    private String recordDatePure;

    public EffectedCountryInfo(String countryName, String totalCaases, String totalCasesPerMillion, String newCases, String activeCases, String totalDeaths, String newDeaths, String totalRecovered,
                               String seriousCritical, String deathsPerMillion, String totalTest, String totalTestPerMillion, String recordDatePure) {
        this.countryName = countryName;
        this.totalCaases = totalCaases;
        this.totalCasesPerMillion = totalCasesPerMillion;
        this.newCases = newCases;
        this.activeCases = activeCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.seriousCritical = seriousCritical;
        this.deathsPerMillion = deathsPerMillion;
        this.totalTest = totalTest;
        this.totalTestPerMillion = totalTestPerMillion;
        this.recordDatePure = recordDatePure;
    }


    public String getCountryName() {
        return countryName;
    }

    public String getTotalCaases() {
        return totalCaases;
    }

    public String getTotalCasesPerMillion() {
        return totalCasesPerMillion;
    }

    public String getNewCases() {
        return newCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public String getSeriousCritical() {
        return seriousCritical;
    }

    public String getDeathsPerMillion() {
        return deathsPerMillion;
    }

    public String getTotalTest() {
        return totalTest;
    }

    public String getTotalTestPerMillion() {
        return totalTestPerMillion;
    }

    public String getRecordDatePure() {
        return recordDatePure;
    }
}
