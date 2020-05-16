package com.technicalnisith.covid19updates.popup_alert_dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technicalnisith.covid19updates.R;
import com.technicalnisith.covid19updates.model.CountriesInfoModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

@SuppressLint("ValidFragment")
public class FilterCountryActivityDisplayDetailedDialog extends AppCompatDialogFragment {

    private CountriesInfoModel countryInfo;
    private int flagId;
    private int positionNumber;
    private String sortedByValue, orderByValue;

    public FilterCountryActivityDisplayDetailedDialog(CountriesInfoModel countryInfo, int flagId, int positionNumber,String sortedByValue, String orderByValue) {
        this.countryInfo = countryInfo;
        this.flagId = flagId;
        this.positionNumber = positionNumber;
        this.sortedByValue = sortedByValue;
        this.orderByValue = orderByValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.filter_country_info_display_dialog_layout,null);
        setUpReportDisplayDialogViews(view, countryInfo,flagId);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }


    private void setUpReportDisplayDialogViews(View view,CountriesInfoModel countryInfo, int flagId){
        ImageView flagImageView = view.findViewById(R.id.flag_image_view);
        TextView countryNameTextView = view.findViewById(R.id.country_name_text_view);
        TextView orderByTextView = view.findViewById(R.id.order_by_text_view);
        TextView sortedByTextView = view.findViewById(R.id.sorted_by_text_view);
        TextView positionNumberTextView = view.findViewById(R.id.position_number_text_view);
        TextView totalCasesTextView = view.findViewById(R.id.total_cases_text_view);
        TextView newCasesTextView = view.findViewById(R.id.new_cases_text_view);
        TextView activeCasesTextView = view.findViewById(R.id.active_cases_text_view);
        TextView totalRecoveredTextView = view.findViewById(R.id.total_recovered_text_view);
        TextView totalDeathsTextView = view.findViewById(R.id.total_deaths_text_view);
        TextView newDeathsTextView = view.findViewById(R.id.new_deaths_text_view);
        TextView criticalConditionTextView = view.findViewById(R.id.critical_condition_text_view);
        TextView totalTestTextView = view.findViewById(R.id.total_test_text_view);
        TextView totalTestPerMillionTextView = view.findViewById(R.id.total_test_per_million_text_view);
        TextView totalDeathsPerMillionTextView = view.findViewById(R.id.total_deaths_per_million_text_view);
        TextView totalCasesPerMillionTextView = view.findViewById(R.id.total_cases_per_million_text_view);

        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(flagImageView);
        }else {
            flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
        countryNameTextView.setText(countryInfo.getCountryName());
        orderByTextView.setText("Order By:  "+orderByValue);
        sortedByTextView.setText("Sorted By:  "+sortedByValue);
        positionNumberTextView.setText("Position No:  "+String.valueOf(positionNumber));
        totalCasesTextView.setText("Total Cases: "+countryInfo.getTotalCases());
        newCasesTextView.setText("New Cases: "+countryInfo.getNewCases());
        activeCasesTextView.setText("Active Cases: "+countryInfo.getActivCcases());
        totalDeathsTextView.setText("Total Deaths: "+countryInfo.getTotalDeaths());
        newDeathsTextView.setText("New Deaths: "+countryInfo.getNewDeaths());
        totalRecoveredTextView.setText("Total Recovered: "+countryInfo.getTotalRecovered());
        criticalConditionTextView.setText("Critical Condition: "+countryInfo.getSeriousCritical());
        totalTestTextView.setText("Total Test: " + countryInfo.getTotalTests());
        totalTestPerMillionTextView.setText("Test Per Million Population: "+countryInfo.getTestsPer1mPopulation());
        totalDeathsPerMillionTextView.setText("Deaths Per Million Population: "+countryInfo.getDeathsPer1mPopulation());
        totalCasesPerMillionTextView.setText("Cases Per Million Population: "+countryInfo.getTotalCasesPer1mPopulation());

    }

}
