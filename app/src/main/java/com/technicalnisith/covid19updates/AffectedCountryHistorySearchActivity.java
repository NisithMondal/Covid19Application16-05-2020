package com.technicalnisith.covid19updates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technicalnisith.covid19updates.model.CountryInfoSearchHistoryModel;
import com.technicalnisith.covid19updates.model.EffectedCountriesSearchHistoryModel;
import com.technicalnisith.covid19updates.popup_alert_dialog.AffectedCountryReportDisplayDialog;
import com.technicalnisith.covid19updates.server_operation.FeatchEffectedCountriesReportHistoryFromServer;
import com.technicalnisith.covid19updates.shared_preference.SaveSelectedCountrySharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AffectedCountryHistorySearchActivity extends AppCompatActivity
        implements FeatchEffectedCountriesReportHistoryFromServer.OnServerResponseListeaner, DatePickerDialog.OnDateSetListener, AffectedCountryHistorySearchRecyclerViewAdapter.OnCardItemClickListener {


    private ImageView flagImageView;
    private TextView selectCountryTextView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonDayReport,radioButtonWeekReport,radioButtonMonth;
    private TextView selectDateTextView;
    private Button searchButton;
    private TextView headingTextView;
    private RecyclerView recyclerView;
    private static final int FLAG_SETTING_REQUEST_CODE = 167;
    private ArrayList<String> allEffectedCountriesNameList;
    private int selectedCountryFlagId;
    private RelativeLayout loadingDataLayout;
    private NestedScrollView nestedScrollView;
    private View horizentalLine;
    private TextView countryNameTextView;
    //For recycler view
    private List<CountryInfoSearchHistoryModel> allAffectedCountryReportList;
    private AffectedCountryHistorySearchRecyclerViewAdapter recyclerViewAdapter;
    private SaveSelectedCountrySharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_country_history_search);
        setUpLayout();
        sharedPreference = new SaveSelectedCountrySharedPreference(getApplicationContext());
        setViewsVisibility();
        Intent intent = getIntent();
        allEffectedCountriesNameList = intent.getStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME_ARRAY_LIST");
        setButtonClickListener();
        allAffectedCountryReportList = new ArrayList<>();
        setRecyclerViewWithAdapter();



    }



    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Search Report By Date");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        countryNameTextView = findViewById(R.id.country_name_text_view);
        flagImageView = findViewById(R.id.flag_image_view);
        selectCountryTextView = findViewById(R.id.select_country_text_view);
        radioGroup = findViewById(R.id.radio_group);
        radioButtonDayReport = findViewById(R.id.radio_button_day_report);
        radioButtonWeekReport = findViewById(R.id.radio_button_week_report);
        radioButtonMonth = findViewById(R.id.radio_button_month_report);
        selectDateTextView = findViewById(R.id.select_date_text_view);
        searchButton = findViewById(R.id.search_button);
        horizentalLine = findViewById(R.id.line_view);
        headingTextView = findViewById(R.id.heading_text_view);
        recyclerView = findViewById(R.id.recycler_view);
        loadingDataLayout = findViewById(R.id.loading_data_layout);
        nestedScrollView = findViewById(R.id.nested_scroll_view);

    }



    private void setViewsVisibility(){
        if (sharedPreference.getSavedCountryNameForAffectedCountryHistorySearchActivity().equalsIgnoreCase("not_select")) {
            countryNameTextView.setVisibility(View.INVISIBLE);
            flagImageView.setVisibility(View.INVISIBLE);
        }else {
            countryNameTextView.setVisibility(View.VISIBLE);
            flagImageView.setVisibility(View.VISIBLE);
            setCountryNameAndFlagFromSharedPrefearence();

        }
        headingTextView.setVisibility(View.INVISIBLE);
        loadingDataLayout.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
        horizentalLine.setVisibility(View.INVISIBLE);

    }

    private void setCountryNameAndFlagFromSharedPrefearence(){
        countryNameTextView.setText(sharedPreference.getSavedCountryNameForAffectedCountryHistorySearchActivity());
        int flagId = sharedPreference.getSavedCountryFlagIdForAffectedCountryHistorySearchActivity();
        if (flagId != -1){
            Picasso.get().load(flagId).centerCrop().fit().into(flagImageView);
        }else {
            flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
    }




    private void setRecyclerViewWithAdapter(){
        recyclerViewAdapter = new AffectedCountryHistorySearchRecyclerViewAdapter("India",allAffectedCountryReportList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }



    private void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,currentYear,currentMonth,currentDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }






    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Called when date is selected by the User
        String selectedDate = dayOfMonth+"-"+(month+1)+"-"+year;
        selectDateTextView.setText(selectedDate);

    }


   private void setButtonClickListener(){
        selectCountryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffectedCountryHistorySearchActivity.this,CountrySettingActivity.class);
                intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME",allEffectedCountriesNameList);
                startActivityForResult(intent,FLAG_SETTING_REQUEST_CODE);
            }
        });

        selectDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryNameTextViewValue = countryNameTextView.getText().toString();
                String dateValue = selectDateTextView.getText().toString();
                if (countryNameTextViewValue.equalsIgnoreCase("")){
                    Toast.makeText(AffectedCountryHistorySearchActivity.this, "Select Country Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dateValue.equalsIgnoreCase("Select Date")){
                    Toast.makeText(AffectedCountryHistorySearchActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                String getReportType = getCheckedRadioButtonValue();
                performServerOperation(countryNameTextViewValue,dateValue,getReportType);

            }
        });

   }



   private String getCheckedRadioButtonValue(){
       String getReportType = "week";
       switch (radioGroup.getCheckedRadioButtonId()){
           case R.id.radio_button_day_report:
               getReportType = "day";
               break;

           case R.id.radio_button_month_report:
               getReportType = "month";
               break;
       }
       return getReportType;


   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_SETTING_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            String selectedCountryName = data.getStringExtra("SELECTED_COUNTRY_NAME");
            selectedCountryFlagId = data.getIntExtra("SELECTED_COUNTRY_FLAG",-1);
            if (selectedCountryFlagId != -1){
                Picasso.get().load(selectedCountryFlagId).centerCrop().fit().into(flagImageView);
            }else {
                flagImageView.setImageResource(R.drawable.ic_defalt_flag);
            }
            flagImageView.setVisibility(View.VISIBLE);
            countryNameTextView.setVisibility(View.VISIBLE);
            countryNameTextView.setText(selectedCountryName);
            if (sharedPreference != null){
                sharedPreference.saveCountryInfoForAffectedCountryHistorySearchActivity(countryNameTextView.getText().toString(),selectedCountryFlagId);
            }
        }
    }



    private void performServerOperation(String countryName, String date,String getReportType){
        FeatchEffectedCountriesReportHistoryFromServer server = new FeatchEffectedCountriesReportHistoryFromServer(this);
        server.getEffectedCountriesReportHistory(countryName,date,getReportType);
        loadingDataLayout.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);

    }


    @Override
    public void onServerResponse(String responseStatus, final String errorMessage, EffectedCountriesSearchHistoryModel effectedCountriesSearchHistoryModel) {
        if (responseStatus.equalsIgnoreCase("success") && effectedCountriesSearchHistoryModel != null){
            if (effectedCountriesSearchHistoryModel.getStatus() == 200) {
                allAffectedCountryReportList.clear();
                allAffectedCountryReportList.addAll(effectedCountriesSearchHistoryModel.getReportList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewAdapter.setCountryName(countryNameTextView.getText().toString());
                        recyclerViewAdapter.notifyDataSetChanged();
                        horizentalLine.setVisibility(View.VISIBLE);
                        headingTextView.setVisibility(View.VISIBLE);
                        headingTextView.setText("Date Wise Report On Covid-19 in "+countryNameTextView.getText().toString());
                    }
                });
            }
        }else if (responseStatus.equalsIgnoreCase("error")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AffectedCountryHistorySearchActivity.this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }else if (responseStatus.equalsIgnoreCase("not success")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AffectedCountryHistorySearchActivity.this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDataLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);

            }
        });

    }


    @Override
    public void onCardItemClick(int position, String countryName, int flagId) {
        AffectedCountryReportDisplayDialog dialog = new AffectedCountryReportDisplayDialog(allAffectedCountryReportList.get(position),countryName,flagId);
        dialog.show(getSupportFragmentManager(),"covid 19");
        dialog.getView();
    }





}
