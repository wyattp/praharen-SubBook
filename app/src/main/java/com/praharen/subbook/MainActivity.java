package com.praharen.subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "subscriptions.sav";

    private ArrayList<Subscription> subList;
    private ArrayAdapter<Subscription> arrayAdapter;
    private Price total;
    private TextView priceDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get text view element
        priceDisplay = findViewById(R.id.price_display);

        // load data from file store in subscription list
        loadData();

        // display price
        showPrice();

        arrayAdapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, subList);
        final ListView listView = findViewById(R.id.main_listview);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subscription sub = (Subscription) listView.getAdapter().getItem(i);
                System.err.println(sub.toString());

                Intent intent = new Intent(MainActivity.this, SubInfoActivity.class);
                intent.putExtra("sub", sub);
                intent.putExtra("pos", i);
                startActivityForResult(intent, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * loadData()
     *
     * load data from FILENAME into subscription array
     * subList
     */
    public void loadData() {
        try {
            FileInputStream fileInputStream = openFileInput(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subList = gson.fromJson(bufferedReader, listType);
        }
        catch (FileNotFoundException e) {
            subList = new ArrayList<Subscription>();
        }
    }

    /*
     * saveData()
     *
     * overwrite file this.FILENAME with arraylist of
     * Subscription types
     */
    public void saveData() {
        Gson gson = new Gson();

        try {
            FileOutputStream fos = openFileOutput(this.FILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(this.subList, bufferedWriter);
            bufferedWriter.flush();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /*
     * onActivityResult
     *
     * called on return from child activity
     * Depending on resultCode, complete a specific
     * action.
     *
     * resultCode:
     *
     *  0: add subscription to sublist
     *  1: ignore returned result and intent
     *  2: not implemented
     *  3: delete subscription from subList in position "pos"
     *  4: replace subscription in "pos" with "newSub"
     *
     *  After handling return result, overwrite file for persistent data.
     */
    // referenced from https://stackoverflow.com/questions/26703691/android-return-object-as-a-activity-result
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int pos;
        switch (resultCode) {
            case 0:
                Subscription resultSub = (Subscription)data.getExtras().getSerializable("newSub");
                addSub(resultSub);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                /* delete this subscription */
                /* extra is position of element */
                pos = data.getExtras().getInt("pos");
                subList.remove(pos);
                break;
            case 4:
                /* edit a subscription */
                /* position found in bundle pos */
                pos = data.getExtras().getInt("pos");
                Subscription newSub = (Subscription)data.getExtras().getSerializable("newSub");
                subList.set(pos, newSub);
        }
        arrayAdapter.notifyDataSetChanged();
        showPrice();
        saveData();
    }

    /*
     * addSub()
     *
     * wrapper for easily adding subscriptions to
     * subList and listView.
     */
    public void addSub(Subscription subscription) {
        this.subList.add(subscription);
        arrayAdapter.notifyDataSetChanged();
        saveData();
    }

    /*
     * Calculate price and display on screen
     */
    public void showPrice() {
        /* update price */
        Price total = new Price(0);
        for (Subscription s : subList) {
            total.add(s.getPrice());
        }
        this.priceDisplay.setText(total.toString());
    }

}
