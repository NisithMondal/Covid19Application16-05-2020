package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nisith.covid19application.model.AllEffectedCountriesModel;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.model.TotalWorldEffectedCasesModel;
import com.nisith.covid19application.popup_alert_dialog.CountryPickerAlertDialog;
import com.nisith.covid19application.popup_alert_dialog.InternetErrorAlertDialog;
import com.nisith.covid19application.server_operation.FeatchEffectedCountriesDataFromServer;
import com.nisith.covid19application.shared_preference.SaveSelectedCountrySharedPreference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FeatchEffectedCountriesDataFromServer.OnServerResponseListener , FeatchEffectedCountriesDataFromServer.OnTotalWorldCasesServerResponseListener,
        HomeActivityRecyclerViewAdapter.OnGridViewClickEventListener, CountryPickerAlertDialog.OnCountryPickerDialogOptionSelectListener {


    private TextView updateDateTextView,reportTextView, totalCasesTextView,newCasesTextView, totalDeathsTextView,newDeathsTextView,activeCasesTextView,totalRecoveredTextView,
            totalTestsTextView,totalTestsPerMillionTextView,totalCasesPerMillionTextView,deathsPerMillionTextView,seriousConditionTextView;
    private TextView serverErrorMessageTextView,mostAffectedCountryTextView;
    private TextView marqueTextView;
    private Button retryButton,cancelButton;
    private String updatedDateOfServerData;
    private Button effectedCountriesButton;
    private Button loadDataButton;
    private Button allOverWorldCasesButton;
    private View horizentalLine;
    private Button searchReportByDateButton;
    private Button filterCountriesReport;
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
        setViewsVisibility();
        saveSelectedCountrySharedPreference = new SaveSelectedCountrySharedPreference(getApplicationContext());
        setClickListenerOnButtons();
        setActivityCountryNameAndFlags();
        marqueTextView.setSelected(true);
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
            horizentalLine.setVisibility(View.GONE);
            searchReportByDateButton.setVisibility(View.GONE);
            filterCountriesReport.setVisibility(View.GONE);
            mostAffectedCountryTextView.setVisibility(View.GONE);
            serverErrorMessageTextView.setText("You are Offline. Please Check Your Internet Connection and Retry.");

        }

    }













    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        CircleImageView toolbarImageView = appToolbar.findViewById(R.id.home_activity_toolbar_image_view);
        Picasso.get().load(R.drawable.corona_icon).fit().centerCrop().into(toolbarImageView);
        toolbarTextView.setText("COVID-19  UPDATES");
        setSupportActionBar(appToolbar);
        setTitle("");
        updateDateTextView = findViewById(R.id.update_date_text_view);
        marqueTextView = findViewById(R.id.marque_text_view);
        reportTextView = findViewById(R.id.report_text_view);
        mostAffectedCountryTextView = findViewById(R.id.most_effected_text_view);
        totalCasesTextView = findViewById(R.id.total_cases_text_view);
        newCasesTextView = findViewById(R.id.new_cases_text_view);
        totalDeathsTextView = findViewById(R.id.total_deaths_text_view);
        newDeathsTextView = findViewById(R.id.new_deaths_text_view);
        activeCasesTextView = findViewById(R.id.active_cases_text_view);
        totalRecoveredTextView = findViewById(R.id.total_recovered_text_view);
        totalTestsTextView = findViewById(R.id.total_tests_text_view);
        totalTestsPerMillionTextView = findViewById(R.id.total_tests_per_million_text_view);
        totalCasesPerMillionTextView = findViewById(R.id.total_cases_per_million_text_view);
        deathsPerMillionTextView = findViewById(R.id.deaths_per_million_text_view);
        seriousConditionTextView = findViewById(R.id.serious_condition_text_view);
        effectedCountriesButton = findViewById(R.id.effected_countries_button);
        loadDataButton = findViewById(R.id.load_data_button);
        searchReportByDateButton = findViewById(R.id.search_report_by_date_button);
        filterCountriesReport = findViewById(R.id.filter_countries_report_button);
        countryFlagImageView = findViewById(R.id.country_flag_image_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
        allOverWorldCasesButton = findViewById(R.id.world_status_effected);
        scrollView = findViewById(R.id.scroll_view);
        horizentalLine = findViewById(R.id.horizental_line);
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
        scrollView.setVisibility(View.VISIBLE);
        loadingDataRelativeLayout.setVisibility(View.GONE);
        horizentalLine.setVisibility(View.VISIBLE);
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
            case R.id.Select_country:
               openCountrySettingActivity();
                break;

            case R.id.privacy_policy:
                openApplicationPrivacyPolicy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    private void openCountrySettingActivity(){
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
            Toast.makeText(this, "Server operation is already going on...", Toast.LENGTH_SHORT).show();
        }
    }



    private void openApplicationPrivacyPolicy(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://technisith.blogspot.com/p/privacy-policy-covid-19-updates.html"));
        try {
        startActivity(intent);
        }catch (Exception e){
        Toast.makeText(this, "Not Open. Something Went Wrong", Toast.LENGTH_SHORT).show();
       }
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



    @Override
    public void onGridViewClick(int position, int flagId) {
        Intent intent = new Intent(HomeActivity.this,DetailedActivity.class);
        CountriesInfoModel countriesInfoModel = allEffectedCountriesInfoList.get(position);
        Gson gson = new Gson();
        String jsonString = gson.toJson(countriesInfoModel);
        intent.putExtra("JSON_STRING",jsonString);
        intent.putExtra("FLAG_ID",flagId);
        intent.putExtra("UPDATE_DATE",updatedDateOfServerData);
        intent.putExtra("INTENT_TYPE","type_country");
        startActivity(intent);

    }




    private void setHomeCountryDetailedViewsVisibility(int value){
        updateDateTextView.setVisibility(value);
        reportTextView.setVisibility(value);
        totalCasesTextView.setVisibility(value);
        newCasesTextView.setVisibility(value);
        totalDeathsTextView.setVisibility(value);
        newDeathsTextView.setVisibility(value);
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
                if (isInternetAvailable()) {
                    if (totalWorldEffectedCasesModelObject == null) {
                        FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(getApplicationContext(), (FeatchEffectedCountriesDataFromServer.OnTotalWorldCasesServerResponseListener) HomeActivity.this);
                        server.getTotalWorldCoronaEffectedCasesDataFromServer();
                        scrollView.setVisibility(View.INVISIBLE);
                        loadingDataRelativeLayout.setVisibility(View.VISIBLE);
                        isServerOperationAlreadyGoingOn = true;
                    } else {
                        Intent intent = new Intent(HomeActivity.this, DetailedActivity.class);
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(totalWorldEffectedCasesModelObject);
                        intent.putExtra("JSON_STRING", jsonString);
                        intent.putExtra("FLAG_ID", R.drawable.ic_white_world);
                        intent.putExtra("INTENT_TYPE", "type_world");
                        startActivity(intent);
                    }
                }else {
                    InternetErrorAlertDialog dialog = new InternetErrorAlertDialog();
                    dialog.show(getSupportFragmentManager(),"nisith");
                }
            }
        });


        searchReportByDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AffectedCountryHistorySearchActivity.class);
                ArrayList<String> allEffectedCountriesNameList = getAllEffectedCountriesName(allEffectedCountriesInfoList);
                intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME_ARRAY_LIST",allEffectedCountriesNameList);
                startActivity(intent);
            }
        });

        filterCountriesReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allEffectedCountriesInfoList != null){
                    Intent intent = new Intent(HomeActivity.this,FilterCountriesActivity.class);
                    Gson json = new Gson();
                    String jsonString = json.toJson(allEffectedCountriesInfoList);
                    intent.putExtra("JSON_STRING",jsonString);
                    startActivity(intent);
                }else {
                    Toast.makeText(HomeActivity.this, "Data Not Available", Toast.LENGTH_SHORT).show();
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
                if (allEffectedCountriesInfoList.isEmpty()) {
                    retryButton.setVisibility(View.VISIBLE);
                    serverErrorMessageTextView.setVisibility(View.VISIBLE);
                    serverErrorMessageTextView.setText("You are Offline. Please Check Your Internet Connection");
                }else {
                    retryButton.setVisibility(View.GONE);
                    serverErrorMessageTextView.setVisibility(View.GONE);
                }
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


    private void showCountryPickerAlertDialog(){
        CountryPickerAlertDialog alertDialog = new CountryPickerAlertDialog(this);
        alertDialog.show(getSupportFragmentManager(),"dialog");
    }


    @Override
    public void OnCountryPickerDialogOptionSelect(String selectionType) {
        if (selectionType.equalsIgnoreCase("SELECT")){
            // When country Picker Alert Dialog's "SELECT" text is clicked, then this condition will true.
            Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
            intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME", getAllEffectedCountriesName(allEffectedCountriesInfoList));
            startActivityForResult(intent, FLAG_SETTING_REQUEST_CODE);
        }
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
                        searchReportByDateButton.setVisibility(View.VISIBLE);
                        filterCountriesReport.setVisibility(View.VISIBLE);
                        mostAffectedCountryTextView.setVisibility(View.VISIBLE);
                        horizentalLine.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                        if (!saveSelectedCountrySharedPreference.isCountryPickerAlertDialogAlreadyShown()) {
                            //This alert dialog only shown at one time when after installing this app user first open this app.
                            showCountryPickerAlertDialog();
                            saveSelectedCountrySharedPreference.saveCountryPickerAlertDialogShowingState(true);
                        }
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
        }else if (responseStatus.equalsIgnoreCase("not_success")){
            scrollView.setVisibility(View.VISIBLE);
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

            final Intent intent = new Intent(HomeActivity.this,DetailedActivity.class);
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
        reportTextView.setText("Report of Corona Virus Affected People in "+countriesInfoModel.getCountryName());
        totalCasesTextView.setText("Total cases: "+countriesInfoModel.getTotalCases());
        newCasesTextView.setText("New Cases: "+countriesInfoModel.getNewCases());
        totalDeathsTextView.setText("Total Deaths: "+countriesInfoModel.getTotalDeaths());
        newDeathsTextView.setText("New Deaths: "+countriesInfoModel.getNewDeaths());
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
            String totalDeaths = countriesInfoModel.getTotalDeaths();
            double totalCasesValue = Double.parseDouble(totalCases.replaceAll(",",""));
            double totalDeathsValue = Double.parseDouble(totalDeaths.replaceAll(",",""));
            if (totalCasesValue >18000 && totalDeathsValue>1000){
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
