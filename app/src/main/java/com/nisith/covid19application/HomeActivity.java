package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private Button effectedCountriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpLayout();
        setClickListenerOnButtons();
    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Home");
        setSupportActionBar(appToolbar);
        setTitle("");
        effectedCountriesButton = findViewById(R.id.effected_countries_button);
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
            case R.id.setting:
                Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
                intent.putExtra("ALL_EFFECTED_COUNTRIES_NAME",getResources().getStringArray(R.array.countries_name));
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setClickListenerOnButtons(){

        effectedCountriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AllEffectedCountriesActivity.class);
                startActivity(intent);
            }
        });


    }






}
