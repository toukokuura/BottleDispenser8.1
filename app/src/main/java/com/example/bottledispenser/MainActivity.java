package com.example.bottledispenser;
// https://www.androidtutorialpoint.com/basics/android-seekbar-tutorial/
// https://www.tutorialspoint.com/android/android_spinner_control.htm

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context = null;

    EditText nro; // chosen product
    Spinner products; // lists products
    TextView msg; // used for delivering system messages
    TextView mun; // shows amount of money in machine
    TextView selected; // product selected from the dropdown menu

    SeekBar amount;
    TextView amnt;

    int spinnernro = -1;

    // Creation of singleton
    BottleDispenser dispenser = BottleDispenser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        // Connect text views
        nro = (EditText) findViewById(R.id.nro);
        products = (Spinner) findViewById(R.id.spinner);
        msg = (TextView) findViewById(R.id.systemMsg);
        mun = (TextView) findViewById(R.id.muns);
        amnt = (TextView) findViewById(R.id.progress);
        selected = (TextView) findViewById(R.id.selected);

        amount = (SeekBar) findViewById(R.id.seekBar);
        amnt.setText(Integer.toString(amount.getProgress()));

        seekbarCreate();

        spinnerCreate();

        // List current products
        //productList();
    }

    public void seekbarCreate () {
        amount.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    // used to notify the user changes/actions in the SeekBar

                    int progressChange;

                    @Override
                    public void onProgressChanged(SeekBar SeekBar, int progress, boolean fromUser) {
                        progressChange = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar SeekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar SeekBar) {
                        amnt.setText(Integer.toString(progressChange));
                    }
                });
    }

    public void spinnerCreate() {

        List<Bottle> bottle_array = dispenser.listOlio();

        // adapter
        ArrayAdapter<Bottle> dataAdapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, bottle_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        products.setAdapter(dataAdapter);

        products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View x, int position, long id) {
                spinnernro = products.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

/*
    private void productList() {
        ArrayList<String> s = new ArrayList<String>();
        s = dispenser.list();

        // Empty TextView and add current bottles
        products.setText("");
        for (int i=0; i<s.size(); i++) {
            products.append(s.get(i));
        }
    }
*/

    public void add(View x) {
        dispenser.addMoney(msg, amount.getProgress());
        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());
    }

    public void take(View x) {
        dispenser.returnMoney(msg);
        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());
    }

    public void buy(View x) {
        if (spinnernro == -1) {
            // take number from EditText nro and buy corresponding bottle
            dispenser.buyBottle(Integer.parseInt(nro.getText().toString()), msg);
        } else{
            // use spinner number
            dispenser.buyBottle(spinnernro+1, msg);
        }

        //update money
        mun.setText("Amount of money: " + dispenser.showMoney());

        // update product list
        //productList();
    }
}