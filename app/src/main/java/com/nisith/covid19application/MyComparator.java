package com.nisith.covid19application;

import com.nisith.covid19application.model.CountriesInfoModel;

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
