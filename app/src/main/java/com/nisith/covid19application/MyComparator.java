package com.nisith.covid19application;

import android.util.Log;

import com.nisith.covid19application.model.CountriesInfoModel;

import java.util.Comparator;

class MyComparator implements Comparator<CountriesInfoModel> {
    private String orderBy;
    private String filterType;

    public MyComparator(String filterType, String orderBy){
        this.orderBy = orderBy;
        this.filterType = filterType;
    }

    @Override
    public int compare(CountriesInfoModel o1, CountriesInfoModel o2) {
        Log.d("SDF","compare is Called");
        int result;
        String objectOneValue,objectTwoValue;
        if (orderBy.equalsIgnoreCase("Ascending Order")){
            if (filterType.equalsIgnoreCase("Total Cases")){
                objectOneValue = o1.getTotalCases().replace(",","");
                objectTwoValue = o2.getTotalCases().replace(",","");
                result = Integer.parseInt(objectOneValue) - Integer.parseInt(objectTwoValue);

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                objectOneValue = o1.getTotalDeaths().replace(",","");
                objectTwoValue = o2.getTotalDeaths().replace(",","");
                result = Integer.parseInt(objectOneValue) - Integer.parseInt(objectTwoValue);

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                objectOneValue = o1.getActivCcases().replace(",","");
                objectTwoValue = o2.getActivCcases().replace(",","");
                result = Integer.parseInt(objectOneValue) - Integer.parseInt(objectTwoValue);

            }else {
                objectOneValue = o1.getTotalTests().replace(",","");
                objectTwoValue = o2.getTotalTests().replace(",","");
                result = Integer.parseInt(objectOneValue) - Integer.parseInt(objectTwoValue);
            }

        }else {//Descending Order

            if (filterType.equalsIgnoreCase("Total Cases")){
                objectOneValue = o1.getTotalCases().replace(",","");
                objectTwoValue = o2.getTotalCases().replace(",","");
                result = Integer.parseInt(objectTwoValue) - Integer.parseInt(objectOneValue);

            }else if (filterType.equalsIgnoreCase("Total Deaths")){
                objectOneValue = o1.getTotalDeaths().replace(",","");
                objectTwoValue = o2.getTotalDeaths().replace(",","");
                result = Integer.parseInt(objectTwoValue) - Integer.parseInt(objectOneValue);

            }else if (filterType.equalsIgnoreCase("Active Cases")){
                objectOneValue = o1.getActivCcases().replace(",","");
                objectTwoValue = o2.getActivCcases().replace(",","");
                result = Integer.parseInt(objectTwoValue) - Integer.parseInt(objectOneValue);

            }else {
                objectOneValue = o1.getTotalTests().replace(",","");
                objectTwoValue = o2.getTotalTests().replace(",","");
                result = Integer.parseInt(objectTwoValue) - Integer.parseInt(objectOneValue);
            }

        }

        return result;
    }


}
