package com.nisith.covid19application.model;

public class CountriesInfoModel {
    private String country_name;
    private String cases;
    private String deaths;
    private String region;
    private String total_recovered;
    private String new_deaths;
    private String new_cases;
    private String serious_critical;
    private String active_cases;
    private String total_cases_per_1m_population;
    private String deaths_per_1m_population;
    private String total_tests;
    private String tests_per_1m_population;

    public CountriesInfoModel(String country_name, String cases, String deaths, String region, String total_recovered, String new_deaths, String new_cases, String serious_critical, String active_cases, String total_cases_per_1m_population, String deaths_per_1m_population, String total_tests, String tests_per_1m_population) {
        this.country_name = country_name;
        this.cases = cases;
        this.deaths = deaths;
        this.region = region;
        this.total_recovered = total_recovered;
        this.new_deaths = new_deaths;
        this.new_cases = new_cases;
        this.serious_critical = serious_critical;
        this.active_cases = active_cases;
        this.total_cases_per_1m_population = total_cases_per_1m_population;
        this.deaths_per_1m_population = deaths_per_1m_population;
        this.total_tests = total_tests;
        this.tests_per_1m_population = tests_per_1m_population;
    }


    public String getCountryName() {
        return country_name;
    }

    public String getTotalCases() {
        return cases;
    }

    public String getTotalDeaths() {
        return deaths;
    }

    public String getRegion() {
        return region;
    }

    public String getTotalRecovered() {
        return total_recovered;
    }

    public String getNewDeaths() {
        return new_deaths;
    }

    public String getNewCases() {
        return new_cases;
    }

    public String getSeriousCritical() {
        return serious_critical;
    }

    public String getActivCcases() {
        return active_cases;
    }

    public String getTotalCasesPer1mPopulation() {
        return total_cases_per_1m_population;
    }

    public String getDeathsPer1mPopulation() {
        return deaths_per_1m_population;
    }

    public String getTotalTests() {
        return total_tests;
    }

    public String getTestsPer1mPopulation() {
        return tests_per_1m_population;
    }
}
