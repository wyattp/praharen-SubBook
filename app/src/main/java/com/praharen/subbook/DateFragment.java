package com.praharen.subbook;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wyatt on 24/01/18.
 */

/*
 * Date fragment for date picker
 */
public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DateFragment() {
        super();
    }

    /* return an instance of DatePickerDialog */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Return instance of DatePickerDialog
        return new DatePickerDialog(getActivity(), (AddSubActivity)getActivity(), year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
