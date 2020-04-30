package com.nisith.covid19application.popup_alert_dialog;

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

import com.nisith.covid19application.R;
import com.nisith.covid19application.model.CountryInfoSearchHistoryModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

@SuppressLint("ValidFragment")
public class AffectedCountryReportDisplayDialog extends AppCompatDialogFragment {

    private CountryInfoSearchHistoryModel countryReport;
    private int flagId;

    public AffectedCountryReportDisplayDialog(CountryInfoSearchHistoryModel countryReport,int flagId) {
        this.countryReport = countryReport;
        this.flagId = flagId;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.country_report_display_dialog_layout,null);
        setUpReportDisplayDialogViews(view,countryReport,flagId);
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




    private void setUpReportDisplayDialogViews(View view,CountryInfoSearchHistoryModel countryReport, int flagId){
        ImageView flagImageView = view.findViewById(R.id.flag_image_view);
        TextView dateTextView = view.findViewById(R.id.date_text_view);
        TextView totalCasesTextView = view.findViewById(R.id.total_cases_text_view);
        TextView totalRecoveredTextView = view.findViewById(R.id.total_recovered_text_view);
        TextView deathsTextView = view.findViewById(R.id.deaths_text_view);
        TextView criticalConditionTextView = view.findViewById(R.id.critical_condition_text_view);
        TextView totalTestTextView = view.findViewById(R.id.total_test_text_view);
        TextView deathRatioTextView = view.findViewById(R.id.death_ratio_text_view);
        TextView recoveredRatioTExtView = view.findViewById(R.id.recovered_ratio_text_view);
        if (flagId != -1){
            Picasso.get().load(flagId).fit().centerCrop().into(flagImageView);
        }else {
            flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
        dateTextView.setText("Date: "+countryReport.getDate());
        String text = "not available";
            totalCasesTextView.setText("Total Cases: "+String.valueOf(countryReport.getTotalCases()));

            totalRecoveredTextView.setText("Total Recovered: "+String.valueOf(countryReport.getRecovered()));

            deathsTextView.setText("Total Deaths: "+String.valueOf(countryReport.getDeaths()));


            criticalConditionTextView.setText("Critical Condition: "+String.valueOf(countryReport.getCritical()));

        if (countryReport.getTested() == 0){
            totalTestTextView.setText("Total Tested: " + text);
        }else {
            totalTestTextView.setText(String.valueOf("Total Tested: " + countryReport.getTested()));
        }

            deathRatioTextView.setText("Death Ratio: "+String.valueOf(countryReport.getDeathRatio()));


            recoveredRatioTExtView.setText("Recovered Ratio: "+String.valueOf(countryReport.getRecoveryRatio()));

    }

}
