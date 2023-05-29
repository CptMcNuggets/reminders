package com.example.reminders20;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeletionConfirmationDialogFragment extends DialogFragment {
    public static String TAG = "ReminderDeletionConfirmation";
    private AdapterCallback adapterCallback;

    public void setAdapterCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity context = (MainActivity) getContext();
        return new AlertDialog.Builder(context)
                .setTitle(R.string.delete_reminder_alert_title)
                .setMessage(R.string.delete_reminder_alert_message)
                .setNegativeButton(R.string.reminder_delete_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.reminder_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCallback.confirmDeletion();
                    }
                }).create();
    }
}
