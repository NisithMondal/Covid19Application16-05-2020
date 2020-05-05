package com.nisith.covid19application.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.nisith.covid19application.R;

public class SaveSelectedCountrySharedPreference {

    private SharedPreferences sharedPreferences;
    private static final String HOME_ACTIVITY_COUNTRY_NAME_KEY = "country_name";
    private static final String HOME_ACTIVITY_COUNTRY_FLAG_ID_KEY = "country_flag_id";
    private static final String AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_NAME_KEY = "another_country_name";
    private static final String AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_FLAG_ID_KEY = "another_country_flag_id";
    private static final String COUNTRY_PICKER_ALERT_DIALOG_SHOWING_STATE_KEY = "country_picker_alert_dialog_showing_state";


    public SaveSelectedCountrySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("selected_country",Context.MODE_PRIVATE);
    }


    public void saveCountryInfo(String countryName, int countryFlagId){
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(HOME_ACTIVITY_COUNTRY_NAME_KEY,countryName);
       editor.putInt(HOME_ACTIVITY_COUNTRY_FLAG_ID_KEY,countryFlagId);
       editor.commit();
    }

    public void saveCountryInfoForAffectedCountryHistorySearchActivity(String countryName, int countryFlagId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_NAME_KEY,countryName);
        editor.putInt(AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_FLAG_ID_KEY,countryFlagId);
        editor.commit();

    }

    public void saveCountryPickerAlertDialogShowingState(boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(COUNTRY_PICKER_ALERT_DIALOG_SHOWING_STATE_KEY,value);
        editor.commit();
    }

    public boolean isCountryPickerAlertDialogAlreadyShown(){
        return sharedPreferences.getBoolean(COUNTRY_PICKER_ALERT_DIALOG_SHOWING_STATE_KEY,false);
    }

    public String getSavedCountryName(){
        return sharedPreferences.getString(HOME_ACTIVITY_COUNTRY_NAME_KEY,"India");
    }

    public int getSavedCountryFlagId(){
        return sharedPreferences.getInt(HOME_ACTIVITY_COUNTRY_FLAG_ID_KEY, R.drawable.in);
    }


    public String getSavedCountryNameForAffectedCountryHistorySearchActivity(){
        return sharedPreferences.getString(AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_NAME_KEY,"not_select");
    }

    public int getSavedCountryFlagIdForAffectedCountryHistorySearchActivity(){
        return sharedPreferences.getInt(AFFECTED_COUNTRY_HISTORY_ACTIVITY_COUNTRY_FLAG_ID_KEY,1);
    }

}
