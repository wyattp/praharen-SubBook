package com.praharen.subbook;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddSubActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText subName;
    private Button subDateButton;
    private TextView subDate;
    private EditText subPrice;
    private EditText subComment;
    private Button subSubmit;
    private boolean datePicked;
    private Subscription subscription;
    private boolean isEdit;
    private int pos;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        datePicked = false;
        subscription = new Subscription();
        subName = findViewById(R.id.edit_sub_name);
        subDateButton = findViewById(R.id.edit_sub_set_date);
        subDate = findViewById(R.id.edit_sub_date_text);
        subPrice = findViewById(R.id.edit_sub_price);
        subComment = findViewById(R.id.edit_sub_comment);
        subSubmit = findViewById(R.id.edit_sub_add);

        isEdit = false;

        /* check if edit */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("edit")) {
                this.isEdit = true;
                subscription = (Subscription) bundle.getSerializable("sub");
                pos = bundle.getInt("pos");
                subName.setText(subscription.getName());
                subPrice.setText(Float.toString(subscription.getPrice().getCost()));
                subComment.setText(subscription.getComment());
                subDate.setText(subscription.getDate().toString());
                date = subscription.getDate();
                datePicked = true;
            }
        }

    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DateFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        this.date = new Date();
        date.setYear(i-1900);
        date.setMonth(i1);
        date.setDate(i2);
        this.subDate.setText(date.toString());
        this.datePicked = true;
    }

    public boolean checkValid(View view) {

        // name is empty
        if (subName.getText().toString().equals("")) {
            notFilled("Name", view);
            return false;
        }

        // price is empty
        if (subPrice.getText().toString().equals("")) {
            notFilled("Price", view);
            return false;
        }

        // date is empty
        if (!datePicked) {
            notFilled("Date", view);
            return false;
        }

        // name too long
        if (subName.getText().toString().length() > 20) {
            stringTooLong("name", view);
            return false;
        }

        // comment too long
        if (subComment.getText().toString().length() > 30) {
            stringTooLong("comment", view);
            return false;
        }

        return true;
    }

    public void createSubscription(View view) {
        if (!checkValid(view)) {
            return;
        }

        subscription.setPrice(new Price(subPrice.getText().toString()));
        subscription.setDate(this.date);
        subscription.setName(subName.getText().toString());
        subscription.setComment(subComment.getText().toString());

        returnToMain();
    }

    public void stringTooLong(String field, View view) {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.edit_sub_error_str_long,field), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void notFilled(String field, View view) {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.edit_sub_error,field), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void returnToMain() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newSub", this.subscription);
        if (isEdit) {
            resultIntent.putExtra("pos",this.pos);
            setResult(4, resultIntent);
        }
        else {
            setResult(0,resultIntent);
        }
        finish();
    }

    public void cancel(View view) {
        Intent resultIntent = new Intent();
        setResult(1,resultIntent);
        finish();
    }

    public void cancel() {
        cancel(null);
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

}
