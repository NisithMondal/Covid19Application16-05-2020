package com.technicalnisith.covid19updates.popup_alert_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.technicalnisith.covid19updates.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class InternetErrorAlertDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle("Internet Error")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setMessage("Please check your Internet Connection and try again. Turn on mobile data or connect to wi-fi.")
                .setIcon(R.drawable.ic_network);


        return dialogBuilder.create();
    }
}
