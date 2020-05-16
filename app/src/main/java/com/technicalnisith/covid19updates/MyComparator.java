package com.technicalnisith.covid19updates;

import com.technicalnisith.covid19updates.model.CountriesInfoModel;

import java.util.List;

class MyComparator  {
    private String orderBy;
    private String filterType;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private OnSortingThreadStopListener onSortingThreadStopListener;

    public interface OnSortingThreadStopListener {
        void onSortingThreadStop();
    }

    public MyComparator(String filterType, String orderBy, List<CountriesInfoModel> allEffectedCountriesInfoList, OnSortingThreadStopListener onSortingThreadStopListener){
        this.orderBy = orderBy;
        this.filterType = filterType;
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        this.onSortingThreadStopListener = onSortingThreadStopListener;
    }


    public void performFiltering(){

        if (orderBy.equalsIgnoreCase("Ascending Order")){
            if (filterType.equalsIgnoreCase("Total Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Cases");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Deaths");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Active Cases");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Test")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Test");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }

        }else if (orderBy.equalsIgnoreCase("Descending Order")){


            if (filterType.equalsIgnoreCase("Total Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Total Cases");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Total Deaths");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Active Cases");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Test")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Total Test");
                        onSortingThreadStopListener.onSortingThreadStop();
                    }
                });
                thread.start();

            }



        }

    }

    //Here we use Bubble sort approach for filtering all Affected countries.

    private void filterInDescendingOrder(String filterType){
        if (filterType.equalsIgnoreCase("Total Cases")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalCases1 = object1.getTotalCases().replace(",", "");
                    int totalCasesValue1;
                    try {
                        totalCasesValue1 = Integer.parseInt(totalCases1);

                    }catch (Exception e){
                        totalCasesValue1 = 0;
                    }
                    String totalCases2 = object2.getTotalCases().replace(",", "");
                    int totalCasesValue2;
                    try {
                        totalCasesValue2 = Integer.parseInt(totalCases2);
                    }catch (Exception e){
                        totalCasesValue2 = 0;
                    }

                    if (totalCasesValue1 < totalCasesValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }

        if (filterType.equalsIgnoreCase("Total Deaths")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalDeaths1 = object1.getTotalDeaths().replace(",", "");
                    int totalDeathsValue1;
                    try {
                        totalDeathsValue1 = Integer.parseInt(totalDeaths1);

                    }catch (Exception e){
                        totalDeathsValue1 = 0;
                    }
                    String totalDeaths2 = object2.getTotalDeaths().replace(",", "");
                    int totalDeathsValue2;
                    try {
                        totalDeathsValue2 = Integer.parseInt(totalDeaths2);
                    }catch (Exception e){
                        totalDeathsValue2 = 0;
                    }

                    if (totalDeathsValue1 < totalDeathsValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }



        if (filterType.equalsIgnoreCase("Active Cases")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String activeCases1 = object1.getActivCcases().replace(",", "");
                    int activeCasesValue1;
                    try {
                        activeCasesValue1 = Integer.parseInt(activeCases1);

                    }catch (Exception e){
                        activeCasesValue1 = 0;
                    }
                    String activeCases2 = object2.getActivCcases().replace(",", "");
                    int activeCasesValue2;
                    try {
                        activeCasesValue2 = Integer.parseInt(activeCases2);
                    }catch (Exception e){
                        activeCasesValue2 = 0;
                    }
                    if (activeCasesValue1 < activeCasesValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }


        if (filterType.equalsIgnoreCase("Total Test")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalTest1 = object1.getTotalTests().replace(",", "");
                    int totalTestValue1;
                    try {
                        totalTestValue1 = Integer.parseInt(totalTest1);

                    }catch (Exception e){
                        totalTestValue1 = 0;
                    }
                    String totalTest2 = object2.getTotalTests().replace(",", "");
                    int totalTestValue2;
                    try {
                        totalTestValue2 = Integer.parseInt(totalTest2);
                    }catch (Exception e){
                        totalTestValue2 = 0;
                    }
                    if (totalTestValue1 < totalTestValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }

    }



    private void filterInAscendingOrder(String filterType){
        if (filterType.equalsIgnoreCase("Total Cases")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalCases1 = object1.getTotalCases().replace(",", "");
                    int totalCasesValue1;
                    try {
                        totalCasesValue1 = Integer.parseInt(totalCases1);

                    }catch (Exception e){
                        totalCasesValue1 = 0;
                    }
                    String totalCases2 = object2.getTotalCases().replace(",", "");
                    int totalCasesValue2;
                    try {
                        totalCasesValue2 = Integer.parseInt(totalCases2);
                    }catch (Exception e){
                        totalCasesValue2 = 0;
                    }
                    if (totalCasesValue1 > totalCasesValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }

        if (filterType.equalsIgnoreCase("Total Deaths")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalDeaths1 = object1.getTotalDeaths().replace(",", "");
                    int totalDeathsValue1;
                    try {
                        totalDeathsValue1 = Integer.parseInt(totalDeaths1);

                    }catch (Exception e){
                        totalDeathsValue1 = 0;
                    }
                    String totalDeaths2 = object2.getTotalDeaths().replace(",", "");
                    int totalDeathsValue2;
                    try {
                        totalDeathsValue2 = Integer.parseInt(totalDeaths2);
                    }catch (Exception e){
                        totalDeathsValue2 = 0;
                    }
                    if (totalDeathsValue1 > totalDeathsValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }



        if (filterType.equalsIgnoreCase("Active Cases")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String activeCases1 = object1.getActivCcases().replace(",", "");
                    int activeCasesValue1;
                    try {
                        activeCasesValue1 = Integer.parseInt(activeCases1);

                    }catch (Exception e){
                        activeCasesValue1 = 0;
                    }
                    String activeCases2 = object2.getActivCcases().replace(",", "");
                    int activeCasesValue2;
                    try {
                        activeCasesValue2 = Integer.parseInt(activeCases2);
                    }catch (Exception e){
                        activeCasesValue2 = 0;
                    }
                    if (activeCasesValue1 > activeCasesValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }


        if (filterType.equalsIgnoreCase("Total Test")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    String totalTest1 = object1.getTotalTests().replace(",", "");
                    int totalTestValue1;
                    try {
                        totalTestValue1 = Integer.parseInt(totalTest1);

                    }catch (Exception e){
                        totalTestValue1 = 0;
                    }
                    String totalTest2 = object2.getTotalTests().replace(",", "");
                    int totalTestValue2;
                    try {
                        totalTestValue2 = Integer.parseInt(totalTest2);
                    }catch (Exception e){
                        totalTestValue2 = 0;
                    }
                    if (totalTestValue1 > totalTestValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }

    }





}
