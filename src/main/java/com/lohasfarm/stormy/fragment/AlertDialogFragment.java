package com.lohasfarm.stormy.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.lohasfarm.stormy.R;

/**
 * Created by Jih on 10/4/2015.
 */
public class AlertDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.error_title)).setMessage(context.getString(R.string.error_message))
                .setPositiveButton("Ok", null);
        return builder.create();
    }
}
