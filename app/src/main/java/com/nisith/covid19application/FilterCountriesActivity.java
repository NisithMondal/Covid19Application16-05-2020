package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nisith.covid19application.model.CountriesInfoModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterCountriesActivity extends AppCompatActivity implements FilterActivityRecyclerViewAdapter.OnCountryCardItemClickInterface {

    private RadioGroup filterCountryRadioGroup,orderByRadioGroup;
    private RadioButton totalCasesRadioButton,totalDeathsRadioButton,activeCasesRadioButton,totalTestRadioButton;
    private RadioButton ascendingOrderRadioButton,descendingOrderRadioButton;
    private Button filterButton;
    private View horizentalLine;
    TextView headingTextView;
    private RecyclerView recyclerView;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private FilterActivityRecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_countries);
        setUpLayout();
        setViewsVisibility();
        Intent intent = getIntent();
        allEffectedCountriesInfoList = new ArrayList<>();
        extractDataFromIntent(intent);
        recyclerViewAdapter = new FilterActivityRecyclerViewAdapter(allEffectedCountriesInfoList,this);
        setUpRecyclerView();
        setButtonListener();
        setDataInRecyclerView();
    }





    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Filter Countries");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        filterCountryRadioGroup = findViewById(R.id.radio_group_filter_country);
        orderByRadioGroup = findViewById(R.id.radio_group_order_by);
        totalCasesRadioButton = findViewById(R.id.total_cases_radio_button);
        totalDeathsRadioButton = findViewById(R.id.total_deaths_radio_button);
        activeCasesRadioButton = findViewById(R.id.total_active_cases_radio_button);
        totalTestRadioButton = findViewById(R.id.total_test_radio_button);
        ascendingOrderRadioButton = findViewById(R.id.ascending_order_radio_button);
        descendingOrderRadioButton = findViewById(R.id.descending_order_radio_button);
        filterButton = findViewById(R.id.filter_button);
        horizentalLine = findViewById(R.id.horizental_line);
        headingTextView = findViewById(R.id.heading_text_view);
        recyclerView = findViewById(R.id.recycler_view);
    }



    private void setUpRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void setViewsVisibility(){
        horizentalLine.setVisibility(View.VISIBLE);
        headingTextView.setVisibility(View.VISIBLE);
    }


    private void setButtonListener(){
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataInRecyclerView();

            }
        });
    }



    private void setDataInRecyclerView(){
        String filterByValue = getFilterCountryRadioGroupSelectedValue();
        String orderByValue = getOrderByRadioGroupSelectedValue();
        headingTextView.setText("Countries Filter By "+filterByValue+" in "+orderByValue);
        performShortingOperationOnArrayList(filterByValue,orderByValue, allEffectedCountriesInfoList);
        recyclerViewAdapter.notifyDataSetChanged();
    }







    private String getFilterCountryRadioGroupSelectedValue(){
        String result = "";
        switch (filterCountryRadioGroup.getCheckedRadioButtonId()){
            case R.id.total_cases_radio_button:
                result = "Total Cases";
                break;
            case R.id.total_deaths_radio_button:
                result = "Total Deaths";
                break;
            case R.id.total_active_cases_radio_button:
                result = "Active Cases";
                break;
            case R.id.total_test_radio_button:
                result = "Total Test";
                break;
        }
        return result;
    }


    private String getOrderByRadioGroupSelectedValue(){
        String result = "";
        switch (orderByRadioGroup.getCheckedRadioButtonId()){
            case R.id.ascending_order_radio_button:
                result = "Ascending Order";
                break;
            case R.id.descending_order_radio_button:
                result = "Descending Order";
                break;

        }
        return result;
    }




    @Override
    public void onCountryCardItemClick(int position, int countryFlagId) {

        Toast.makeText(this, "Card item is Clicked", Toast.LENGTH_SHORT).show();

    }



    private void extractDataFromIntent(Intent intent){
        String jsonString = intent.getStringExtra("JSON_STRING");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CountriesInfoModel>>(){}.getType();
        List<CountriesInfoModel> list = gson.fromJson(jsonString,type);
        if (list != null) {
            allEffectedCountriesInfoList.addAll(list);
        }
    }


    private void performShortingOperationOnArrayList(String filterType, String orderBy, List<CountriesInfoModel> allEffectedCountriesArrayList){
        MyComparator myComparator = new MyComparator(filterType,orderBy);
        Collections.sort(allEffectedCountriesArrayList,myComparator);
    }


}



























