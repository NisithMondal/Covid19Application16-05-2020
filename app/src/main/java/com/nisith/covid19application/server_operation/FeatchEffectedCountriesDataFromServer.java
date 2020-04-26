package com.nisith.covid19application.server_operation;

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

    public interface OnServerResponseListener{
        void onServerResponse(String responseStatus, AllEffectedCountriesModel allEffectedCountriesModel);
    }

    public interface OnTotalWorldCasesServerResponseListener{
        void onServerResponse(String responseStatus, TotalWorldEffectedCasesModel totalWorldEffectedCasesModel);
    }


    public FeatchEffectedCountriesDataFromServer(OnTotalWorldCasesServerResponseListener onTotalWorldCasesServerResponseListener) {
        this.onTotalWorldCasesServerResponseListener = onTotalWorldCasesServerResponseListener;

    }

    public FeatchEffectedCountriesDataFromServer(OnServerResponseListener onServerResponseListener) {
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
                onServerResponseListener.onServerResponse("error",null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String serverResponse = response.body().string();
                    Gson gson = new Gson();
                    allEffectedCountriesModel = gson.fromJson(serverResponse, AllEffectedCountriesModel.class);
                    onServerResponseListener.onServerResponse("success", allEffectedCountriesModel);
                }else {
                    onServerResponseListener.onServerResponse("not_success", null);
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
                onTotalWorldCasesServerResponseListener.onServerResponse("error",null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String serverResponse = response.body().string();
                    Gson gson = new Gson();
                    totalWorldEffectedCasesModel = gson.fromJson(serverResponse, TotalWorldEffectedCasesModel.class);
                    onTotalWorldCasesServerResponseListener.onServerResponse("success", totalWorldEffectedCasesModel);
                }else {
                    onTotalWorldCasesServerResponseListener.onServerResponse("not_success",null);
                }

            }
        });
    }




}
