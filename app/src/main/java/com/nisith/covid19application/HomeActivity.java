package com.nisith.covid19application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nisith.covid19application.model.AllEffectedCountriesModel;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.server_operation.FeatchEffectedCountriesDataFromServer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FeatchEffectedCountriesDataFromServer.OnServerResponseListener {

    private Button effectedCountriesButton;
    private Button loadDataButton;
    private Button indianStatesButton;
    private List<CountriesInfoModel> allEffectedCountriesInfoList = null;
    private boolean isOpenFlagSettingActivity = false;
    private static final int FLAG_SETTING_REQUECT_CODE = 123;
    private ImageView countryFlagImageView;
    private TextView countryNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpLayout();
        setClickListenerOnButtons();


        //////////////////
        performServerOperation();


    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Home");
        setSupportActionBar(appToolbar);
        setTitle("");
        effectedCountriesButton = findViewById(R.id.effected_countries_button);
        loadDataButton = findViewById(R.id.load_data_button);
        indianStatesButton = findViewById(R.id.indian_states_button);
        countryFlagImageView = findViewById(R.id.country_flag_image_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
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
            case R.id.set_country:
                Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
                if (allEffectedCountriesInfoList != null){
                    ArrayList<String> allEffectedCountriesNameList = getAllEffectedCountriesName(allEffectedCountriesInfoList);
                    intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME",allEffectedCountriesNameList);
                    startActivityForResult(intent,FLAG_SETTING_REQUECT_CODE);
                    Log.d("ABCD","allEffectedCountriesInfoList != null");
                }else {
                    performServerOperation();
                    isOpenFlagSettingActivity = true;
                    Log.d("ABCD","allEffectedCountriesInfoList == null");
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_SETTING_REQUECT_CODE && resultCode == RESULT_OK && data != null){
            String selectedCountryName = data.getStringExtra("SELECTED_COUNTRY_NAME");
            int selectedCountryFlagId = data.getIntExtra("SELECTED_COUNTRY_FLAG",-1);
            Picasso.get().load(selectedCountryFlagId).fit().centerCrop().into(countryFlagImageView);
            countryNameTextView.setText(selectedCountryName);
        }

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


    private void performServerOperation(){
        FeatchEffectedCountriesDataFromServer server = new FeatchEffectedCountriesDataFromServer(this);
        server.getAllEffectedCountriesDataFromServer();
    }


    @Override
    public void onServerResponse(String responseStatus, AllEffectedCountriesModel allEffectedCountriesModel) {
        Log.d("ABCD","server operation Finished");
        if (responseStatus.equalsIgnoreCase("success") && allEffectedCountriesModel != null){
            allEffectedCountriesInfoList = new ArrayList<>();
            allEffectedCountriesInfoList.addAll(allEffectedCountriesModel.getAllEffectedCountriesDetaildList());
            if (! allEffectedCountriesInfoList.isEmpty()){
                allEffectedCountriesInfoList.remove(0);
            }
            if (isOpenFlagSettingActivity){
                Intent intent = new Intent(HomeActivity.this,CountrySettingActivity.class);
                ArrayList<String> allEffectedCountriesNameList = getAllEffectedCountriesName(allEffectedCountriesInfoList);
                intent.putStringArrayListExtra("ALL_EFFECTED_COUNTRIES_NAME",allEffectedCountriesNameList);
                startActivityForResult(intent,FLAG_SETTING_REQUECT_CODE);
            }


        }
    }

    private ArrayList<String> getAllEffectedCountriesName(List<CountriesInfoModel> allEffectedCountriesInfoList){
        ArrayList<String> allEffectedCountriesNameList = new ArrayList<>();
        for (int i=0; i<allEffectedCountriesInfoList.size(); i++){
            allEffectedCountriesNameList.add(allEffectedCountriesInfoList.get(i).getCountryName());
        }
        return allEffectedCountriesNameList;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOpenFlagSettingActivity = false;
    }
}
