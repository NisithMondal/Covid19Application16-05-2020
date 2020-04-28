package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nisith.covid19application.model.AllEffectedCountriesModel;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.model.TotalWorldEffectedCasesModel;
import com.nisith.covid19application.popup_alert_dialog.InternetErrorAlertDialog;
import com.nisith.covid19application.server_operation.FeatchEffectedCountriesDataFromServer;
import com.nisith.covid19application.shared_preference.SaveSelectedCountrySharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FeatchEffectedCountriesDataFromServer.OnServerResponseListener , FeatchEffectedCountriesDataFromServer.OnTotalWorldCasesServerResponseListener {


    private TextView updateDateTextView,reportTextView, totalCasesTextView, totalDeathsTextView,activeCasesTextView,totalRecoveredTextView,
            totalTestsTextView,totalTestsPerMillionTextView,totalCasesPerMillionTextView,deathsPerMillionTextView,seriousConditionTextView;
    private TextView serverErrorMessageTextView;
    private Button retryButton,cancelButton;
    private String updatedDateOfServerData;
    private Button effectedCountriesButton;
    private Button loadDataButton;
    private Button allOverWorldCasesButton,indianStatesButton;
    private NestedScrollView scrollView;
    private RelativeLayout loadingDataRelativeLayout;
    private List<CountriesInfoModel> allEffectedCountriesInfoList = null;
    private boolean isOpenFlagSettingActivity = false;
    private static final int FLAG_SETTING_REQUEST_CODE = 123;
    private ImageView countryFlagImageView;
    private TextView countryNameTextView;
    private SaveSelectedCountrySharedPreference saveSelectedCountrySharedPreference;
    private boolean isServerOperationAlreadyGoingOn = false;
    private TotalWorldEffectedCasesModel totalWorldEffectedCasesModelObject = null;
    //For grid view
    private List<Integer> mostEffectedCountriesIndexList;
    private RecyclerView recyclerView;
    private HomeActivityRecyclerViewAdapter homeActivityRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpLayout();
        setClickListenerOnButtons();
        saveSelectedCountrySharedPreference = new SaveSelectedCountrySharedPreference(getApplicationContext());
        setViewsVisibility();
        setActivityCountryNameAndFlags();
        setHomeCountryDetailedViewsVisibility(View.INVISIBLE);
        allEffectedCountriesInfoList = new ArrayList<>();
        mostEffectedCountriesIndexList = new ArrayList<>();
        setUpRecyclerViewWithAdapter();

        if (isInternetAvailable()) {
            performServerOperation();
        }else {
            serverErrorMessageTextView.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            serverErrorMessageTextView.setText("You are Offline. Please Check Your Internet Connection");
        }

    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Home");
        setSupportActionBar(appToolbar);
        setTitle("");
        updateDateTextView = findViewById(R.id.update_date_text_view);
        reportTextView = findViewById(R.id.report_text_view);
        totalCasesTextView = findViewById(R.id.total_cases_text_view);
        totalDeathsTextView = findViewById(R.id.total_deaths_text_view);
        activeCasesTextView = findViewById(R.id.active_cases_text_view);
        totalRecoveredTextView = findViewById(R.id.total_recovered_text_view);
        totalTestsTextView = findViewById(R.id.total_tests_text_view);
        totalTestsPerMillionTextView = findViewById(R.id.total_tests_per_million_text_view);
        totalCasesPerMillionTextView = findViewById(R.id.total_cases_per_million_text_view);
        deathsPerMillionTextView = findViewById(R.id.deaths_per_million_text_view);
        seriousConditionTextView = findViewById(R.id.serious_condition_text_view);
        effectedCountriesButton = findViewById(R.id.effected_countries_button);
        loadDataButton = findViewById(R.id.load_data_button);
        indianStatesButton = findViewById(R.id.indian_states_button);
        countryFlagImageView = findViewById(R.id.country_flag_image_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
        allOverWorldCasesButton = findViewById(R.id.world_status_effected);
        indianStatesButton = findViewById(R.id.indian_states_button);
        scrollView = findViewById(R.id.scroll_view);
        loadingDataRelativeLayout = findViewById(R.id.loading_data_layout);
        serverErrorMessageTextView = findViewById(R.id.error_message_text_view);
        retryButton = findViewById(R.id.retry_button);
        cancelButton = findViewById(R.id.cancel_button);
        recyclerView = findViewById(R.id.recycler_view);

    }


    private void setUpRecyclerViewWithAdapter(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        homeActivityRecyclerViewAdapter = new HomeActivityRecyclerViewAdapter(mostEffectedCountriesIndexList, allEffectedCountriesInfoList, this);
        recyclerView.setAdapter(homeActivityRecyclerViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setViewsVisibility(){
        if (saveSelectedCountrySharedPreference != null){
            String countryName = saveSelectedCountrySharedPreference.getSavedCountryName();
            if (countryName.equalsIgnoreCase("India")){
                indianStatesButton.setVisibility(View.VISIBLE);
            }else {
                indianStatesButton.setVisibility(View.GONE);
            }
        }
        scrollView.setVisibility(View.VISIBLE);
        loadingDataRelativeLayout.setVisibility(View.GONE);
    }


    private void setActivityCountryNameAndFlags(){
        int flagId = saveSelectedCountrySharedPreference.getSavedCountryFlagId();
        String countryName = saveSelectedCountrySharedPreference.getSavedCountryName();
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(countryFlagImageView);
        }else {
            countryFlagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
        countryNameTextView.setText(countryName);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.set_country:
                Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
                if (! isServerOperationAlreadyGoingOn) {
                    if (allEffectedCountriesInfoList.size()>0) {
                        ArrayList<String> allEffectedCountriesNameList = getAllEffectedCountriesName(allEffectedCountriesInfoList);
                        intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME", allEffectedCountriesNameList);
                        startActivityForResult(intent, FLAG_SETTING_REQUEST_CODE);
                    } else {
                        performServerOperation();
                        isOpenFlagSettingActivity = true;
                    }
                }else {
                    //Server operation is Already Going On
                    Toast.makeText(this, "Server Operation Already Going On...", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_SETTING_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            String selectedCountryName = data.getStringExtra("SELECTED_COUNTRY_NAME");
            int selectedCountryFlagId = data.getIntExtra("SELECTED_COUNTRY_FLAG",-1);
            saveCountryNameAndFlagOnSharedPreference(selectedCountryName,selectedCountryFlagId);
            setActivityCountryNameAndFlags();
            setViewsVisibility();
            setHomeCountryDetaildOnViews(allEffectedCountriesInfoList);
            setHomeCountryDetailedViewsVisibility(View.VISIBLE);

        }

    }


    private void setHomeCountryDetailedViewsVisibility(int value){
        updateDateTextView.setVisibility(value);
        reportTextView.setVisibility(value);
        totalCasesTextView.setVisibility(value);
        totalDeathsTextView.setVisibility(value);
        activeCasesTextView.setVisibility(value);
        totalRecoveredTextView.setVisibility(value);
        totalTestsTextView.setVisibility(value);
        totalCasesPerMillionTextView.setVisibility(value);
        totalTestsPerMillionTextView.setVisibility(value);
        deathsPerMillionTextView.setVisibility(value);
        seriousConditionTextView.setVisibility(value);
    }



    private void setClickListenerOnButtons(){

        effectedCountriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AllEffectedCountriesActivity.class);
                if (isInternetAvailable()) {
                    startActivity(intent);
                }else {
                    InternetErrorAlertDialog dialog = new InternetErrorAlertDialog();
                    dialog.show(getSupportFragmentManager(),"nisith");
                }
            }
        });

        loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    performServerOperation();
                }else {
                    InternetErrorAlertDialog dialog = new InternetErrorAlertDialog();
                    dialog.show(getSupportFragmentManager(),"nisith");
                }
            }
        });

        allOverWorldCasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalWorldEffectedCasesModelObject == null) {
                    FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(getApplicationContext(), (FeatchEffectedCountriesDataFromServer.OnTotalWorldCasesServerResponseListener) HomeActivity.this);
                    server.getTotalWorldCoronaEffectedCasesDataFromServer();
                    scrollView.setVisibility(View.INVISIBLE);
                    loadingDataRelativeLayout.setVisibility(View.VISIBLE);
                    serverErrorMessageTextView.setVisibility(View.GONE);
                    retryButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                    isServerOperationAlreadyGoingOn = true;
                }else {
                    Intent intent = new Intent(HomeActivity.this,DetailedActivity.class);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(totalWorldEffectedCasesModelObject);
                    intent.putExtra("JSON_STRING",jsonString);
                    intent.putExtra("FLAG_ID",R.drawable.ic_white_world);
                    intent.putExtra("INTENT_TYPE","type_world");
                    startActivity(intent);
                }
            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performServerOperation();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.VISIBLE);
                serverErrorMessageTextView.setVisibility(View.VISIBLE);
                serverErrorMessageTextView.setText("You are Offline. Please Check Your Internet Connection");
                retryButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void performServerOperation(){
        FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(getApplicationContext(),(FeatchEffectedCountriesDataFromServer.OnServerResponseListener) this);
        server.getAllEffectedCountriesDataFromServer();
        scrollView.setVisibility(View.INVISIBLE);
        loadingDataRelativeLayout.setVisibility(View.VISIBLE);
        serverErrorMessageTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        isServerOperationAlreadyGoingOn = true;
    }




    @Override
    public void onServerResponse(String responseStatus, final String errorMessage, AllEffectedCountriesModel allEffectedCountriesModel) {
        if (responseStatus.equalsIgnoreCase("success") && allEffectedCountriesModel != null){
            allEffectedCountriesInfoList.clear();
            allEffectedCountriesInfoList.addAll(allEffectedCountriesModel.getAllEffectedCountriesDetaildList());
            updatedDateOfServerData = allEffectedCountriesModel.getUpdatedDate();
            if (! allEffectedCountriesInfoList.isEmpty()){
                allEffectedCountriesInfoList.remove(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setHomeCountryDetaildOnViews(allEffectedCountriesInfoList);
                        setHomeCountryDetailedViewsVisibility(View.VISIBLE);
                        mostEffectedCountriesIndexList.clear();
                        mostEffectedCountriesIndexList.addAll(getMostEffectedCountriesIndexList(allEffectedCountriesInfoList));
                        homeActivityRecyclerViewAdapter.notifyDataSetChanged();
                        scrollView.setVisibility(View.VISIBLE);
                    }
                });

            }
            if (isOpenFlagSettingActivity){
                Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
                ArrayList<String> allEffectedCountriesNameList = getAllEffectedCountriesName(allEffectedCountriesInfoList);
                intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME",allEffectedCountriesNameList);
                startActivityForResult(intent, FLAG_SETTING_REQUEST_CODE);
            }

        } else if (responseStatus.equalsIgnoreCase("error")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    serverErrorMessageTextView.setVisibility(View.VISIBLE);
                    serverErrorMessageTextView.setText(errorMessage);
                    retryButton.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.VISIBLE);
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDataRelativeLayout.setVisibility(View.GONE);
            }
        });
        isServerOperationAlreadyGoingOn = false;
    }



    @Override
    public void onTotalWorldDataServerResponse(String responseStatus, final String errorMessage, TotalWorldEffectedCasesModel totalWorldEffectedCasesModel) {
        if (responseStatus.equalsIgnoreCase("success") && totalWorldEffectedCasesModel != null){
            this.totalWorldEffectedCasesModelObject = totalWorldEffectedCasesModel;
            Intent intent = new Intent(HomeActivity.this,DetailedActivity.class);
            Gson gson = new Gson();
            String jsonString = gson.toJson(totalWorldEffectedCasesModelObject);
            intent.putExtra("JSON_STRING",jsonString);
            intent.putExtra("FLAG_ID",R.drawable.ic_white_world);
            intent.putExtra("INTENT_TYPE","type_world");
            startActivity(intent);

        }else if (responseStatus.equalsIgnoreCase("error")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scrollView.setVisibility(View.VISIBLE);
                loadingDataRelativeLayout.setVisibility(View.GONE);
            }
        });
        isServerOperationAlreadyGoingOn = false;
    }


    private ArrayList<String> getAllEffectedCountriesName(List<CountriesInfoModel> allEffectedCountriesInfoList){
        ArrayList<String> allEffectedCountriesNameList = new ArrayList<>();
        for (int i=0; i<allEffectedCountriesInfoList.size(); i++){
            allEffectedCountriesNameList.add(allEffectedCountriesInfoList.get(i).getCountryName());
        }
        return allEffectedCountriesNameList;
    }




    private void setHomeCountryDetaildOnViews(List<CountriesInfoModel> allEffectedCountriesInfoList){
        String countryName = saveSelectedCountrySharedPreference.getSavedCountryName();
        for (CountriesInfoModel countriesInfoModel : allEffectedCountriesInfoList){
            if (countriesInfoModel.getCountryName().equalsIgnoreCase(countryName)){
                setDataOnViews(countriesInfoModel);
                break;
            }
        }
    }



    private void setDataOnViews(CountriesInfoModel countriesInfoModel){
        updateDateTextView.setText("Update on "+updatedDateOfServerData);
        reportTextView.setText("Report of Corona Virus Effected People in "+countriesInfoModel.getCountryName());
        totalCasesTextView.setText("Total cases: "+countriesInfoModel.getTotalCases());
        totalDeathsTextView.setText("Total Deaths: "+countriesInfoModel.getTotalDeaths());
        activeCasesTextView.setText("Active Cases: "+countriesInfoModel.getActivCcases());
        totalRecoveredTextView.setText("Total Recovered: "+countriesInfoModel.getTotalRecovered());
        totalTestsTextView.setText("Total Test: "+countriesInfoModel.getTotalTests());
        totalCasesPerMillionTextView.setText("Total Cases Per 1 Million Population: "+countriesInfoModel.getTotalCasesPer1mPopulation());
        totalTestsPerMillionTextView.setText("Total Tests Per 1 Million Population: "+countriesInfoModel.getTestsPer1mPopulation());
        deathsPerMillionTextView.setText("Deaths Per 1 Million Population: "+countriesInfoModel.getDeathsPer1mPopulation());
        seriousConditionTextView.setText("Serious Condition: "+countriesInfoModel.getSeriousCritical());
    }



    private List<Integer> getMostEffectedCountriesIndexList(List<CountriesInfoModel> allEffectedCountriesInfoList){
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < allEffectedCountriesInfoList.size(); i++) {
            CountriesInfoModel countriesInfoModel = allEffectedCountriesInfoList.get(i);
            String totalCases = countriesInfoModel.getTotalCases();
            double totalCasesValue = Double.parseDouble(totalCases.replaceAll(",",""));
            if (totalCasesValue >20000){
                indexList.add(i);
            }
        }
        return indexList;
    }



    private boolean isInternetAvailable() {
        //This method check if the internet is available or not
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        isOpenFlagSettingActivity = false;
    }


    private void saveCountryNameAndFlagOnSharedPreference(String countryName, int flagId){
        if (saveSelectedCountrySharedPreference != null){
            saveSelectedCountrySharedPreference.saveCountryInfo(countryName,flagId);
        }
    }


}
