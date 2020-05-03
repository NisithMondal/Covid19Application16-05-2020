package com.nisith.covid19application;

import android.util.Log;

import com.nisith.covid19application.model.CountriesInfoModel;

import java.util.Comparator;
import java.util.List;

class MyComparator  {
    private String orderBy;
    private String filterType;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private OnThreadStop onThreadStop;

    public interface OnThreadStop{
        void onThreadStop();
    }

    public MyComparator(String filterType, String orderBy, List<CountriesInfoModel> allEffectedCountriesInfoList, OnThreadStop onThreadStop){
        this.orderBy = orderBy;
        this.filterType = filterType;
        this.allEffectedCountriesInfoList = allEffectedCountriesInfoList;
        this.onThreadStop = onThreadStop;
    }


    public void performFiltering(){

        if (orderBy.equalsIgnoreCase("Ascending Order")){
            if (filterType.equalsIgnoreCase("Total Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Cases");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Deaths");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Active Cases");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Test")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInAscendingOrder("Total Test");
                        onThreadStop.onThreadStop();
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
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Total Deaths");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Active Cases");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }else if (filterType.equalsIgnoreCase("Total Test")){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        filterInDescendingOrder("Total Test");
                        onThreadStop.onThreadStop();
                    }
                });
                thread.start();

            }



        }

    }


    private void filterInDescendingOrder(String filterType){
        if (filterType.equalsIgnoreCase("Total Cases")){
            for (int i =0;i<allEffectedCountriesInfoList.size()-1;i++) {
                for (int j = 0; j < allEffectedCountriesInfoList.size()-i-1; j++) {
                    CountriesInfoModel object1 = allEffectedCountriesInfoList.get(j);
                    CountriesInfoModel object2 = allEffectedCountriesInfoList.get(j + 1);
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalCases().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalCases().replace(",", ""));
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
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalDeaths().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalDeaths().replace(",", ""));
                    if (totalCasesValue1 < totalCasesValue2) {
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
                    int totalCasesValue1 = Integer.parseInt(object1.getActivCcases().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getActivCcases().replace(",", ""));
                    if (totalCasesValue1 < totalCasesValue2) {
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
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalTests().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalTests().replace(",", ""));
                    if (totalCasesValue1 < totalCasesValue2) {
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
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalCases().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalCases().replace(",", ""));
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
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalDeaths().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalDeaths().replace(",", ""));
                    if (totalCasesValue1 > totalCasesValue2) {
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
                    int totalCasesValue1 = Integer.parseInt(object1.getActivCcases().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getActivCcases().replace(",", ""));
                    if (totalCasesValue1 > totalCasesValue2) {
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
                    int totalCasesValue1 = Integer.parseInt(object1.getTotalTests().replace(",", ""));
                    int totalCasesValue2 = Integer.parseInt(object2.getTotalTests().replace(",", ""));
                    if (totalCasesValue1 > totalCasesValue2) {
                        allEffectedCountriesInfoList.set(j, object2);
                        allEffectedCountriesInfoList.set(j + 1, object1);
                    }
                }
            }
        }

    }





}
