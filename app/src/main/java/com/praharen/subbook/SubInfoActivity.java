package com.praharen.subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/*
 * Class: SubInfoActivity
 *
 * Activity to view details of given subscription
 * and give the option to edit the details if the user
 * wants to.
 */
public class SubInfoActivity extends AppCompatActivity {

    private Subscription subscription;
    private int pos;
    private TextView name;
    private TextView price;
    private TextView date;
    private TextView comment;

    /*
     * onCreate
     *
     * Get TextViews of XML elements.
     * Retrieve subscription details from bundle.
     * Set TextViews to subscription details.
     */
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
            pos = bundle.getInt("pos");
        }

        // change xml variables
        setTextViews();

    }

    /* Method to handle physical back button on phones. */
    @Override
    public void onBackPressed() {
        cancel();
    }

    /* Cancel the current activity and return to MainActivity */
    public void cancel(View view) {
        Intent resultIntent = new Intent();
        setResult(1,resultIntent);
        finish();
    }

    /* Overload of cancel */
    public void cancel() {
        cancel(null);
    }

    /*
     * setTextViews
     *
     * Set the TextViews to the subscription details.
     */
    private void setTextViews() {
        this.name.setText(subscription.getName());
        this.price.setText(subscription.getPrice().toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(subscription.getDate());
        this.date.setText(formattedDate);

        if (subscription.getComment().equals("")) {
            comment.setText(R.string.no_comment);
        }
        else {
            comment.setText(subscription.getComment());
        }
    }

    /*
     * deleteRecord
     *
     * Return to mainActivity and set the intent to
     * delete the subscription record.
     */
    public void deleteRecord(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("pos",pos);
        setResult(3, resultIntent);
        finish();
    }

    /*
     * editSub
     *
     * Go to AddSubActivity with the intent to change
     * details of the current subscription.
     */
    public void editSub(View view) {
        Intent newIntent = new Intent(SubInfoActivity.this, AddSubActivity.class);
        newIntent.putExtra("edit",true);
        newIntent.putExtra("pos", pos);
        newIntent.putExtra("sub", this.subscription);
        startActivityForResult(newIntent, 0);
    }

    /*
     * onActivityResult
     *
     * Called when AddSubActivity returns after editing the details
     * of the given subscription.
     * Return to MainActivity with the intent to edit the current
     * subscription.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode,data);
        if (resultCode == 4)
            finish();
    }

}
