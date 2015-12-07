package com.mapplinks.ppcroicalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class MainActivity extends AppCompatActivity {
    double budget, costPerClick, convRate, avgSaleSize, totClicks, totSales, revenue, profit, ROI;
    TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String projectToken = "2f1020b47151e4f80946a591d41f5d8f";
        final MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        final EditText budgetView, CPCView,convRateView,avgSalesSizeView;

        budgetView = (EditText)findViewById(R.id.budget);
        CPCView = (EditText)findViewById(R.id.cpc);
        convRateView = (EditText)findViewById(R.id.conv_rate);
        avgSalesSizeView = (EditText)findViewById(R.id.avg_sales_size);

        Button calc = (Button) findViewById(R.id.calculate_but);
        Button reset = (Button) findViewById(R.id.reset_but);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b, cpc, cr, ass;
                b = budgetView.getText().toString();
                cpc = CPCView.getText().toString();
                cr = convRateView.getText().toString();
                ass = avgSalesSizeView.getText().toString();

                if(b.isEmpty()||cpc.isEmpty()||cr.isEmpty()||ass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter All The Fields!", Toast.LENGTH_SHORT).show();
                }else{
                    budget = Double.parseDouble(b);
                    costPerClick = Double.parseDouble(cpc);
                    convRate = Double.parseDouble(cr);
                    avgSaleSize = Double.parseDouble(ass);
                    mixpanel.track("Calculate");
                    calculate();
                }
            }
        });

    }

    void calculate() {
        totClicks = budget/costPerClick;
        totSales = budget*convRate/100;
        revenue = totSales*avgSaleSize;
        profit = revenue-budget;
        ROI = profit/budget*100;

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
