package com.praharen.subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SubInfoActivity extends AppCompatActivity {

    private Subscription subscription;
    private ArrayList<Subscription> subList;
    private int pos;
    private TextView name;
    private TextView price;
    private TextView date;
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_info);

        /* xml variables */
        name = findViewById(R.id.info_name);
        price = findViewById(R.id.info_price);
        date = findViewById(R.id.info_date);
        comment = findViewById(R.id.info_comment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subscription = (Subscription) bundle.getSerializable("sub");
            pos = (int) bundle.getInt("pos");
        }

        // change xml variables
        setTextViews();

    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    public void cancel(View view) {
        Intent resultIntent = new Intent();
        setResult(1,resultIntent);
        finish();
    }

    public void cancel() {
        cancel(null);
    }

    private void setTextViews() {
        this.name.setText(subscription.getName());
        this.price.setText(subscription.getPrice().toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(subscription.getDate());
        this.date.setText(formattedDate);

        if (subscription.getComment().equals("")) {
            comment.setText("No comment added.");
        }
        else {
            comment.setText(subscription.getComment());
        }
    }

    public void deleteRecord(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("pos",pos);
        setResult(3, resultIntent);
        finish();
    }

    public void editSub(View view) {
        Intent newIntent = new Intent(SubInfoActivity.this, AddSubActivity.class);
        newIntent.putExtra("edit",true);
        newIntent.putExtra("pos", pos);
        newIntent.putExtra("sub", this.subscription);
        startActivityForResult(newIntent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode,data);
        if (resultCode == 4)
            finish();
    }

}
