package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nisith.covid19application.model.CountriesInfoModel;
import com.nisith.covid19application.popup_alert_dialog.FilterCountryActivityDisplayDetailedDialog;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterCountriesActivity extends AppCompatActivity implements FilterActivityRecyclerViewAdapter.OnCountryCardItemClickInterface,
        MyComparator.OnSortingThreadStopListener, SortListedFragment.OnCloseFragmentListener {

    private RadioGroup filterCountryRadioGroup,orderByRadioGroup;
    private RadioButton totalCasesRadioButton,totalDeathsRadioButton,activeCasesRadioButton,totalTestRadioButton;
    private RadioButton ascendingOrderRadioButton,descendingOrderRadioButton;
    private Button filterButton;
    private RelativeLayout loadingDataLayout;
    private RelativeLayout performFiltringLayout;
    private NestedScrollView nestedScrollView;
    private TextView marqueTextView;
    private List<CountriesInfoModel> allEffectedCountriesInfoList;
    private FilterActivityRecyclerViewAdapter recyclerViewAdapter;
    private SortListedFragment sortListedFragment;
    private FrameLayout fragmentContainerLayout;
    private Toolbar appToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_countries);
        setUpLayout();
        marqueTextView.setSelected(true);
        setViewsVisibility();
        allEffectedCountriesInfoList = new ArrayList<>();
        recyclerViewAdapter = new FilterActivityRecyclerViewAdapter(allEffectedCountriesInfoList,this);
        createFragment();
        setButtonListener();

    }






    private void setUpLayout(){
        appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("Filter Countries Report");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        marqueTextView = findViewById(R.id.marque_text_view);
        filterCountryRadioGroup = findViewById(R.id.radio_group_sorting_country);
        orderByRadioGroup = findViewById(R.id.radio_group_order_by);
        totalCasesRadioButton = findViewById(R.id.total_cases_radio_button);
        totalDeathsRadioButton = findViewById(R.id.total_deaths_radio_button);
        activeCasesRadioButton = findViewById(R.id.total_active_cases_radio_button);
        totalTestRadioButton = findViewById(R.id.total_test_radio_button);
        ascendingOrderRadioButton = findViewById(R.id.ascending_order_radio_button);
        descendingOrderRadioButton = findViewById(R.id.descending_order_radio_button);
        filterButton = findViewById(R.id.filter_button);
        loadingDataLayout = findViewById(R.id.loading_data_layout);
        performFiltringLayout = findViewById(R.id.perform_filtering_layout);
        nestedScrollView = findViewById(R.id.nested_scroll_view);
        fragmentContainerLayout = findViewById(R.id.frame_layout);
        ImageView backgroundImageView = findViewById(R.id.background_image_view);
        Picasso.get().load(R.drawable.corona2).fit().centerCrop().into(backgroundImageView);
    }




    private void setViewsVisibility(){
        nestedScrollView.setVisibility(View.VISIBLE);
        loadingDataLayout.setVisibility(View.GONE);
        performFiltringLayout.setVisibility(View.GONE);
        fragmentContainerLayout.setVisibility(View.GONE);

    }




    private void createFragment(){
        sortListedFragment = new SortListedFragment(recyclerViewAdapter);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout,sortListedFragment,"fragment").commit();


    }

    private void showFragment(){
        String filterByValue = getFilterCountryRadioGroupSelectedValue();
        String orderByValue = getOrderByRadioGroupSelectedValue();
        sortListedFragment.setHeadingTextView("Sorted By "+ filterByValue +" in "+orderByValue);
        fragmentContainerLayout.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
        appToolbar.setVisibility(View.GONE);
    }

    private void hideFragment(){
        fragmentContainerLayout.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
        appToolbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (fragmentContainerLayout.getVisibility() == View.VISIBLE){
            hideFragment();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCloseFragment() {
        //This method is called when fragment cross button is clicked
        hideFragment();
    }

    private void setButtonListener(){
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allEffectedCountriesInfoList.isEmpty()) {
                    loadingDataLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                    extractDataFromIntent(getIntent());

                }else {
                    setDataInRecyclerView();
                }
            }
        });
    }


    private void setDataInRecyclerView(){
        String filterByValue = getFilterCountryRadioGroupSelectedValue();
        String orderByValue = getOrderByRadioGroupSelectedValue();
        recyclerViewAdapter.setFilterType(filterByValue);
        performShortingOperationOnArrayList(filterByValue, orderByValue);// This method takes some time to give result

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
    public void onCountryCardItemClick(int position,int positionNumberTextViewValue,int countryFlagId) {
        FilterCountryActivityDisplayDetailedDialog dialog
                = new FilterCountryActivityDisplayDetailedDialog(allEffectedCountriesInfoList.get(position),countryFlagId,positionNumberTextViewValue);
        dialog.show(getSupportFragmentManager(),"nisith");
    }



    private void extractDataFromIntent(final Intent intent){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = intent.getStringExtra("JSON_STRING");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<CountriesInfoModel>>(){}.getType();
                List<CountriesInfoModel> list = gson.fromJson(jsonString,type);
                allEffectedCountriesInfoList.addAll(list);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDataLayout.setVisibility(View.GONE);
                    }
                });
                if (allEffectedCountriesInfoList != null) {
                    setDataInRecyclerView();
                }
            }
        });

        thread.start();
    }




    private void performShortingOperationOnArrayList(String filterType, String orderBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                performFiltringLayout.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
            }
        });
        MyComparator myComparator = new MyComparator(filterType,orderBy,allEffectedCountriesInfoList,this);
        myComparator.performFiltering();

    }


    @Override
    public void onSortingThreadStop() {
        //This method will be called from MyComparator class when sorting operation is completed.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                performFiltringLayout.setVisibility(View.GONE);
                recyclerViewAdapter.setAnotherAllEffectedCountriesInfoList(allEffectedCountriesInfoList);
                recyclerViewAdapter.notifyDataSetChanged();
                showFragment();

            }
        });

    }


}



























