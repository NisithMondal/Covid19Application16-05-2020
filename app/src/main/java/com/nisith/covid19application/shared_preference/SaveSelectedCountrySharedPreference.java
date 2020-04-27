package com.nisith.covid19application.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.nisith.covid19application.R;

public class SaveSelectedCountrySharedPreference {

    private SharedPreferences sharedPreferences;
    private static final String COUNTRY_NAME_KEY = "country_name";
    private static final String COUNTRY_FLAG_ID_KEY = "country_flag_id";


    public SaveSelectedCountrySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("selected_country",Context.MODE_PRIVATE);
    }


    public void saveCountryInfo(String countryName, int countryFlagId){
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(COUNTRY_NAME_KEY,countryName);
       editor.putInt(COUNTRY_FLAG_ID_KEY,countryFlagId);
       editor.commit();
    }


    public String getSavedCountryName(){
        return sharedPreferences.getString(COUNTRY_NAME_KEY,"India");
    }

    public int getSavedCountryFlagId(){
        return sharedPreferences.getInt(COUNTRY_FLAG_ID_KEY, R.drawable.in);
    }


}
