package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.model.TotalWorldEffectedCasesModel;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private ImageView flagImageView;
    private TextView countryNameTextView, updateDateTextView,reportTextView, totalCasesTextView, totalDeathsTextView,activeCasesTextView,totalRecoveredTextView,
                      totalTestsTextView,totalTestsPerMillionTextView,totalCasesPerMillionTextView,deathsPerMillionTextView,seriousConditionTextView;
    private String updatedDateOfServerData;
    private int flagId;
    private CountriesInfoModel countriesInfoModel;
    private TotalWorldEffectedCasesModel totalWorldEffectedCasesModelObject;
    private String intentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        setUpLayout();
        extractDataFromIntent();
        setDataOnViews();


    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("All Details");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flagImageView = findViewById(R.id.flag_image_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
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

    }





    private void extractDataFromIntent(){
        Intent intent = getIntent();
        Gson gson = new Gson();
        intentType = intent.getStringExtra("INTENT_TYPE");
        flagId = intent.getIntExtra("FLAG_ID", -1);
        String jsonString = intent.getStringExtra("JSON_STRING");
        if (intentType.equalsIgnoreCase("type_country")) {

            updatedDateOfServerData = intent.getStringExtra("UPDATE_DATE");
            countriesInfoModel = gson.fromJson(jsonString, CountriesInfoModel.class);

        }else if (intentType.equalsIgnoreCase("type_world")){
            totalWorldEffectedCasesModelObject = gson.fromJson(jsonString,TotalWorldEffectedCasesModel.class);
        }
    }


    private void setDataOnViews() {

        if (intentType.equalsIgnoreCase("type_country")) {
            countryNameTextView.setText(countriesInfoModel.getCountryName());
            if (flagId != -1) {
                Picasso.get().load(flagId).fit().into(flagImageView);
            } else {
                flagImageView.setImageResource(R.drawable.ic_defalt_flag);
            }
            updateDateTextView.setText("Update on " + updatedDateOfServerData);
            reportTextView.setText("Report of Corona Virus Effected People in " + countriesInfoModel.getCountryName());
            totalCasesTextView.setText("Total cases: " + countriesInfoModel.getTotalCases());
            totalDeathsTextView.setText("Total Deaths: " + countriesInfoModel.getTotalDeaths());
            activeCasesTextView.setText("Active Cases: " + countriesInfoModel.getActivCcases());
            totalRecoveredTextView.setText("Total Recovered: " + countriesInfoModel.getTotalRecovered());
            totalTestsTextView.setText("Total Test: " + countriesInfoModel.getTotalTests());
            totalCasesPerMillionTextView.setText("Total Cases Per 1 Million Population: " + countriesInfoModel.getTotalCasesPer1mPopulation());
            totalTestsPerMillionTextView.setText("Total Tests Per 1 Million Population: " + countriesInfoModel.getTestsPer1mPopulation());
            deathsPerMillionTextView.setText("Deaths Per 1 Million Population: " + countriesInfoModel.getDeathsPer1mPopulation());
            seriousConditionTextView.setText("Serious Condition: " + countriesInfoModel.getSeriousCritical());

        }else if (intentType.equalsIgnoreCase("type_world")){
            countryNameTextView.setVisibility(View.GONE);
            flagImageView.setImageResource(flagId);
            updateDateTextView.setText("Update on " + totalWorldEffectedCasesModelObject.getUpdatedDate());
            reportTextView.setText("Report of Corona Virus Effected People Through Out the World");
            totalCasesTextView.setText("Total cases: " + totalWorldEffectedCasesModelObject.getTotalCases());
            totalDeathsTextView.setText("Total Deaths: " + totalWorldEffectedCasesModelObject.getTotalDeaths());
            activeCasesTextView.setText("Active Cases: " + totalWorldEffectedCasesModelObject.getActiveCases());
            totalRecoveredTextView.setText("Total Recovered: " + totalWorldEffectedCasesModelObject.getTotalRecovered());
            totalTestsTextView.setText("New Cases: " + totalWorldEffectedCasesModelObject.getNewCases());
            totalCasesPerMillionTextView.setText("Total Cases Per 1 Million Population: " + totalWorldEffectedCasesModelObject.getTotalCasesPer1mPopulation());
            totalTestsPerMillionTextView.setText("Total New Deaths: " + totalWorldEffectedCasesModelObject.getNewDeaths());
            deathsPerMillionTextView.setText("Deaths Per 1 Million Population: " + totalWorldEffectedCasesModelObject.getDeathsPer1mPopulation());
            seriousConditionTextView.setText("Serious Condition: " + totalWorldEffectedCasesModelObject.getSeriousCritical());
        }
    }


}
