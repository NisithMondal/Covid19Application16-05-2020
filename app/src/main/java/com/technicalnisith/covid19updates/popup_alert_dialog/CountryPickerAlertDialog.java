package com.technicalnisith.covid19updates.popup_alert_dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CountryPickerAlertDialog extends AppCompatDialogFragment {

    private OnCountryPickerDialogOptionSelectListener countryPickerDialogOptionSelectListener;

    public interface OnCountryPickerDialogOptionSelectListener{
        void OnCountryPickerDialogOptionSelect(String selectionType);
    }

    public CountryPickerAlertDialog(){}


    @SuppressLint("ValidFragment")
    public CountryPickerAlertDialog(OnCountryPickerDialogOptionSelectListener countryPickerDialogOptionSelectListener){
        this.countryPickerDialogOptionSelectListener = countryPickerDialogOptionSelectListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = "If you select your country name now, then you will see your country flag and information in Home Screen. If you skip it for now, then by default at Home Screen you can see Indian flag and information. You can also select your country Later.";

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle("Select Your Country")
                .setMessage(message)
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countryPickerDialogOptionSelectListener.OnCountryPickerDialogOptionSelect("SELECT");
                    }
                })
                .setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countryPickerDialogOptionSelectListener.OnCountryPickerDialogOptionSelect("SKIP");
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        return alertDialog;
    }
}
