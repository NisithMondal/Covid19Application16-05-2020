package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nisith.covid19application.model.CountriesInfoModel;

import java.util.ArrayList;

public class CountrySettingActivity extends AppCompatActivity implements CountryPickerRecyclerViewAdapter.OnCardItemClickListener {

    private RecyclerView recyclerView;
    private CountryPickerRecyclerViewAdapter adapter;
    private ArrayList<String> allEffectedCountriesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_setting);
        setUpLayout();
        Intent intent = getIntent();
        allEffectedCountriesName = intent.getStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME");
        setUpRecyclerViewWithAdapter();

    }



    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Set Your Country");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);

    }

    private void setUpRecyclerViewWithAdapter(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CountryPickerRecyclerViewAdapter(this,allEffectedCountriesName);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onCardItemClicked(int position,String countryName, int countryFlagId) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("SELECTED_COUNTRY_NAME",countryName);
        resultIntent.putExtra("SELECTED_COUNTRY_FLAG",countryFlagId);
        setResult(RESULT_OK,resultIntent);
        finish();

    }
}
