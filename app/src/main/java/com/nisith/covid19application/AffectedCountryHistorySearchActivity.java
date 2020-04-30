package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nisith.covid19application.model.CountryInfoSearchHistoryModel;
import com.nisith.covid19application.model.EffectedCountriesSearchHistoryModel;
import com.nisith.covid19application.server_operation.FeatchEffectedCountriesReportHistoryFromServer;

public class AffectedCountryHistorySearchActivity extends AppCompatActivity implements FeatchEffectedCountriesReportHistoryFromServer.OnServerResponseListeaner {


    private ImageView flagImageView;
    private TextView selectCountryTextView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonDayReport,radioButtonWeekReport,radioButtonMonth;
    private EditText searchDateEditText;
    private Button searchButton;
    private TextView headingTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_country_history_search);
        setUpLayout();
    }


    private void setUpLayout(){
        flagImageView = findViewById(R.id.flag_image_view);
        selectCountryTextView = findViewById(R.id.select_country_text_view);
        radioButtonDayReport = findViewById(R.id.radio_button_day_report);
        radioButtonWeekReport = findViewById(R.id.radio_button_week_report);
        radioButtonMonth = findViewById(R.id.radio_button_month_report);
        searchDateEditText = findViewById(R.id.search_date_edit_text);
        searchButton = findViewById(R.id.search_button);
        headingTextView = findViewById(R.id.heading_text_view);
        recyclerView = findViewById(R.id.recycler_view);

    }


    private void performServerOperation(){
        FeatchEffectedCountriesReportHistoryFromServer server = new FeatchEffectedCountriesReportHistoryFromServer(this);
        server.getEffectedCountriesReportHistory();
    }


    @Override
    public void onServerResponse(String responseStatus, String errorMessage, EffectedCountriesSearchHistoryModel effectedCountriesSearchHistoryModel) {
        if (responseStatus.equalsIgnoreCase("success") && effectedCountriesSearchHistoryModel != null){



        }else if (responseStatus.equalsIgnoreCase("error")){
            Toast.makeText(this, "Error: "+errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
