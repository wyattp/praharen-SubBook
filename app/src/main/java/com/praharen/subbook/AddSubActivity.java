package com.praharen.subbook;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class AddSubActivity extends AppCompatActivity {
    TextView dateText;
    TextView nameText;
    Subscription newSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        dateText = findViewById(R.id.edit_sub_date_text);
        nameText = findViewById(R.id.edit_sub_name_text);
        newSub = new Subscription();
    }

    public void showDatePickerDialog(View v) {
        FragmentManager fm = this.getFragmentManager();
        DialogFragment newFragment = new DateFragment(dateText);
        newFragment.show(fm, "datePicker");
    }

    public void addSubName() {
        String name = nameText.getText().toString();
        if (name.length() == 0 && name.length() > 20) {
            // Too long of string
            nameText.setBackgroundColor(Color.RED);
        }
        else
            newSub.setName(name);
    }

    public void fillSubscription() {
        /* check if formed filled is valid */

        /* print error if not */
    }

}
