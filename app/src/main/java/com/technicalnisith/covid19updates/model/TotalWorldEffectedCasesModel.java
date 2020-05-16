package com.technicalnisith.covid19updates.model;

public class TotalWorldEffectedCasesModel {

    private String total_cases;
    private String new_cases;
    private String total_deaths;
    private String new_deaths;
    private String total_recovered;
    private String active_cases;
    private String serious_critical;
    private String total_cases_per_1m_population;
    private String deaths_per_1m_population;
    private String statistic_taken_at;

    public TotalWorldEffectedCasesModel(String total_cases, String new_cases, String total_deaths, String new_deaths, String total_recovered, String active_cases, String serious_critical, String total_cases_per_1m_population, String deaths_per_1m_population, String statistic_taken_at) {
        this.total_cases = total_cases;
        this.new_cases = new_cases;
        this.total_deaths = total_deaths;
        this.new_deaths = new_deaths;
        this.total_recovered = total_recovered;
        this.active_cases = active_cases;
        this.serious_critical = serious_critical;
        this.total_cases_per_1m_population = total_cases_per_1m_population;
        this.deaths_per_1m_population = deaths_per_1m_population;
        this.statistic_taken_at = statistic_taken_at;
    }

    public String getTotalCases() {
        return total_cases;
    }

    public String getNewCases() {
        return new_cases;
    }

    public String getTotalDeaths() {
        return total_deaths;
    }

    public String getNewDeaths() {
        return new_deaths;
    }

    public String getTotalRecovered() {
        return total_recovered;
    }

    public String getActiveCases() {
        return active_cases;
    }

    public String getSeriousCritical() {
        return serious_critical;
    }

    public String getTotalCasesPer1mPopulation() {
        return total_cases_per_1m_population;
    }

    public String getDeathsPer1mPopulation() {
        return deaths_per_1m_population;
    }

    public String getUpdatedDate() {
        return statistic_taken_at;
    }
}
