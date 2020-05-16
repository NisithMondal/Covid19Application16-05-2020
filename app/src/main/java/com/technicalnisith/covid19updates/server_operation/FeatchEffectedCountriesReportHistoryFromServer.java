package com.technicalnisith.covid19updates.server_operation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.technicalnisith.covid19updates.model.EffectedCountriesSearchHistoryModel;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeatchEffectedCountriesReportHistoryFromServer {


    private Context context;
    private OnServerResponseListeaner serverResponseListeaner;

    public interface OnServerResponseListeaner{
        void onServerResponse(String responseStatus,String errorMessage, EffectedCountriesSearchHistoryModel effectedCountriesSearchHistoryModel);
    }

    public FeatchEffectedCountriesReportHistoryFromServer(AppCompatActivity appCompatActivity) {
        this.context = appCompatActivity.getApplicationContext();
        this.serverResponseListeaner = (OnServerResponseListeaner) appCompatActivity;


    }


    public void getEffectedCountriesReportHistory(String countryName, String date, String getReportType){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coronavirus-map.p.rapidapi.com/v1/spots/"+getReportType+"?date="+date+"&region="+countryName)
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "4cb9c3992cmsh8959d21ec207e07p1940fdjsnb047353be7d3")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errorMessage = e.getMessage();
                if (! isInternetAvailable()){
                    errorMessage = "You are Offline. Please Check Your Internet Connection and Try Again. This Application Needs Internet Connection To Work.";
                }
                serverResponseListeaner.onServerResponse("error",errorMessage,null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    EffectedCountriesSearchHistoryModel effectedCountriesSearchHistoryModel = gson.fromJson(jsonString, EffectedCountriesSearchHistoryModel.class);
                    serverResponseListeaner.onServerResponse("success","",effectedCountriesSearchHistoryModel);
                }else {
                    serverResponseListeaner.onServerResponse("not success","Server Error 502",null);
                }

            }
        });
    }


    private boolean isInternetAvailable() {
        //This method check if the internet is available or not
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
