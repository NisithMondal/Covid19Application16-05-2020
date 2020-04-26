package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

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

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private void performServerOperation(){
        FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(this);
        server.getAllEffectedCountriesDataFromServer();
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
            allEffectedCountriesInfoList.remove(0); //Because in '0' index there is no Country name
            Log.d("XYZ","List Size= "+allEffectedCountriesInfoList.size());
            if (allCountriesRecyclerViewAdapter != null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allCountriesRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
