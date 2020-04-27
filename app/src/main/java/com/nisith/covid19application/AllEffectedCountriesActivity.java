package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nisith.covid19application.model.AllEffectedCountriesModel;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.server_operation.FeatchEffectedCountriesDataFromServer;

import java.util.ArrayList;
import java.util.List;

public class AllEffectedCountriesActivity extends AppCompatActivity implements AllCountriesRecyclerViewAdapter.OnCountryCardItemClickInterface,
        FeatchEffectedCountriesDataFromServer.OnServerResponseListener {

    private RecyclerView recyclerView;
    private AllCountriesRecyclerViewAdapter allCountriesRecyclerViewAdapter;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private RelativeLayout loadingDataLayout;
    private TextView errorMessageTextView;
    private Button retryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_effected_countries);
        setUpLayout();
        allEffectedCountriesInfoList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        allCountriesRecyclerViewAdapter = new AllCountriesRecyclerViewAdapter(allEffectedCountriesInfoList,this);
        recyclerView.setAdapter(allCountriesRecyclerViewAdapter);
        performServerOperation();
    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Effected Countries");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.all_countries_recycler_view);
        loadingDataLayout = findViewById(R.id.loading_data_layout);
        loadingDataLayout.setVisibility(View.GONE);
        errorMessageTextView = findViewById(R.id.error_message_text_view);
        errorMessageTextView.setVisibility(View.GONE);
        retryButton = findViewById(R.id.retry_button);
        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performServerOperation();
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_activity_menu,menu);
        final SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Enter Country Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filter filter = allCountriesRecyclerViewAdapter.getFilter();
                filter.filter(searchView.getQuery());
                return true;
            }
        });
        return true;
    }




    private void performServerOperation(){
        FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(this);
        server.getAllEffectedCountriesDataFromServer();
        loadingDataLayout.setVisibility(View.VISIBLE);
        errorMessageTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    @Override
    public void onCountryCardItemClick(int position,int countryFlagId) {
        Intent intent = new Intent(AllEffectedCountriesActivity.this,DetailedActivity.class);
        intent.putExtra("FLAG_ID",countryFlagId);
        startActivity(intent);

    }

    @Override
    public void onServerResponse(String responseStatus, AllEffectedCountriesModel allEffectedCountriesModel) {

        if (responseStatus.equalsIgnoreCase("success") && allEffectedCountriesModel != null){
            allEffectedCountriesInfoList.clear();
            allEffectedCountriesInfoList.addAll(allEffectedCountriesModel.getAllEffectedCountriesDetaildList());
            if (! allEffectedCountriesInfoList.isEmpty()){
                allEffectedCountriesInfoList.remove(0);//Because in '0' index there is no Country name
            }
            if (allCountriesRecyclerViewAdapter != null && allEffectedCountriesInfoList != null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allCountriesRecyclerViewAdapter.setAnotherAllEffectedCountriesInfoList(allEffectedCountriesInfoList);
                        allCountriesRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }else if (responseStatus.equalsIgnoreCase("error")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    errorMessageTextView.setText("Please Check Your Internet Connection and Try Again...");
                    retryButton.setVisibility(View.VISIBLE);
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDataLayout.setVisibility(View.GONE);
            }
        });

    }
}
