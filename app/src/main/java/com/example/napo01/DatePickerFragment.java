package com.example.napo01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Context context;
    String activty_name;

    public DatePickerFragment(Context context, String activty_name) {
        this.context = context;
        this.activty_name = activty_name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        if (activty_name.equals("CareerCert")) {
            ((CareerCertInput) context).processDatePickerResult(year, month, day);
        } else if (activty_name.equals("CareerAwards")) {
            ((CareerAwardsInput) context).processDatePickerResult(year, month, day);
        } else if (activty_name.equals("CareerLang")) {
            ((CareerLangInput) context).processDatePickerResult(year, month, day);
        }else if (activty_name.equals("CareerIntern")) {
            ((CareerInternInput) context).processDatePickerResult(year, month, day);
        }
    }
}

