package com.nisith.covid19application.server_operation;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.nisith.covid19application.model.AllEffectedCountriesModel;
import com.nisith.covid19application.model.TotalWorldEffectedCasesModel;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeatchEffectedCountriesDataFromServer {

    private OnServerResponseListener onServerResponseListener;
    private OnTotalWorldCasesServerResponseListener onTotalWorldCasesServerResponseListener;
    private AllEffectedCountriesModel allEffectedCountriesModel = null;
    private TotalWorldEffectedCasesModel totalWorldEffectedCasesModel = null;
    private Context context;

    public interface OnServerResponseListener{
        void onServerResponse(String responseStatus,String errorMessage, AllEffectedCountriesModel allEffectedCountriesModel);
    }

    public interface OnTotalWorldCasesServerResponseListener{
        void onTotalWorldDataServerResponse(String responseStatus,String errorMessage, TotalWorldEffectedCasesModel totalWorldEffectedCasesModel);
    }


    public FeatchEffectedCountriesDataFromServer(Context context,OnTotalWorldCasesServerResponseListener onTotalWorldCasesServerResponseListener) {
        this.context = context;
        this.onTotalWorldCasesServerResponseListener = onTotalWorldCasesServerResponseListener;

    }

    public FeatchEffectedCountriesDataFromServer(Context context ,OnServerResponseListener onServerResponseListener) {
        this.context = context;
        this.onServerResponseListener = onServerResponseListener;
    }

    public void getAllEffectedCountriesDataFromServer(){
        //Get All Effected Countries Information

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/cases_by_country.php")
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "4cb9c3992cmsh8959d21ec207e07p1940fdjsnb047353be7d3")
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errorMessage = e.getMessage();
               if (! isInternetAvailable()){
                   errorMessage = "You are Offline. Please Check Your Internet Connection";
               }
                onServerResponseListener.onServerResponse("error",errorMessage,null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String serverResponse = response.body().string();
                    Gson gson = new Gson();
                    allEffectedCountriesModel = gson.fromJson(serverResponse, AllEffectedCountriesModel.class);
                    onServerResponseListener.onServerResponse("success","", allEffectedCountriesModel);
                }else {
                    onServerResponseListener.onServerResponse("not_success","", null);
                }
            }
        });

    }


    public void getTotalWorldCoronaEffectedCasesDataFromServer(){
        //Get Total World Information in single JSON String
        OkHttpClient OkHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/worldstat.php")
                .get()
                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "4cb9c3992cmsh8959d21ec207e07p1940fdjsnb047353be7d3")
                .build();
        OkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errorMessage = e.getMessage();
                if (! isInternetAvailable()){
                    errorMessage = "You are Offline. Please Check Your Internet Connection";
                }
                onTotalWorldCasesServerResponseListener.onTotalWorldDataServerResponse("error",errorMessage,null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String serverResponse = response.body().string();
                    Gson gson = new Gson();
                    totalWorldEffectedCasesModel = gson.fromJson(serverResponse, TotalWorldEffectedCasesModel.class);
                    onTotalWorldCasesServerResponseListener.onTotalWorldDataServerResponse("success","", totalWorldEffectedCasesModel);
                }else {
                    onTotalWorldCasesServerResponseListener.onTotalWorldDataServerResponse("not_success","",null);
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
