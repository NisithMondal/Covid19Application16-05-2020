package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.nisith.covid19application.model.CountriesInfoModel;

import java.util.ArrayList;

public class CountrySettingActivity extends AppCompatActivity implements CountryPickerRecyclerViewAdapter.OnCardItemClickListener {

    private RecyclerView recyclerView;
    private CountryPickerRecyclerViewAdapter adapter;
    private ArrayList<String> allEffectedCountriesName;
    private ArrayList<String> temporaryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_setting);
        setUpLayout();
        Intent intent = getIntent();
        allEffectedCountriesName = intent.getStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME");
        setUpRecyclerViewWithAdapter();
        temporaryArrayList = new ArrayList<>();

    }



    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Select Your Country");
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
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Enter Your Country Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filter filter = adapter.getFilter();
                filter.filter(searchView.getQuery());
                return true;
            }
        });
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
