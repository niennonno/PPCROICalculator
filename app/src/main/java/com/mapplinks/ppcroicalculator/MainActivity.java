package com.mapplinks.ppcroicalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    double budget, costPerClick, convRate, avgSaleSize, totClicks, totSales, revenue, profit, ROI, ROAS;
    TextView result1View, result2View;

    DecimalFormat precision = new DecimalFormat("0.0000");
    DecimalFormat precision1 = new DecimalFormat("0");
    DecimalFormat precision2 = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String projectToken = "2f1020b47151e4f80946a591d41f5d8f";
        final MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        final EditText budgetView, CPCView, convRateView, avgSalesSizeView;

        budgetView = (EditText) findViewById(R.id.budget);
        CPCView = (EditText) findViewById(R.id.cpc);
        convRateView = (EditText) findViewById(R.id.conv_rate);
        avgSalesSizeView = (EditText) findViewById(R.id.avg_sales_size);

        result1View = (TextView) findViewById(R.id.result1);
        result2View = (TextView) findViewById(R.id.result2);

        Button calc = (Button) findViewById(R.id.calculate_but);
        Button reset = (Button) findViewById(R.id.reset_but);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budgetView.setText("");
                CPCView.setText("");
                convRateView.setText("");
                avgSalesSizeView.setText("");
                result1View.setText("");
                result2View.setText("");
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b, cpc, cr, ass;
                b = budgetView.getText().toString();
                cpc = CPCView.getText().toString();
                cr = convRateView.getText().toString();
                ass = avgSalesSizeView.getText().toString();

                if (b.isEmpty() || cpc.isEmpty() || cr.isEmpty() || ass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter All The Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    budget = Double.parseDouble(b);
                    costPerClick = Double.parseDouble(cpc);
                    convRate = Double.parseDouble(cr);
                    avgSaleSize = Double.parseDouble(ass);
                    if (convRate > 100) {
                        Toast.makeText(MainActivity.this, "Conversion Rate can't be greater than 100!", Toast.LENGTH_SHORT).show();
                    } else {
                        mixpanel.track("Calculate");
                        calculate();
                        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }
                }
            }
        });

    }

    void calculate() {
        totClicks = budget / costPerClick;
        totSales = totClicks * convRate / 100;
        revenue = totSales * avgSaleSize;
        profit = revenue - budget;
        ROI = profit / budget * 100;
        ROAS = revenue / budget * 100;


        result1View.setText("Total Clicks: " + precision1.format(totClicks) +
                            "\n\nRevenue: " + precision2.format(revenue) +
                            "\n\nROI: " + precision.format(ROI) + "%");

        result2View.setText("Total Sales: " + precision1.format(totSales)
                + "\n\nProfit: " + precision2.format(profit)
                +  "\n\nROAS: " + precision.format(ROAS) + "%");

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
}
