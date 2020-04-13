package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    LinearLayout viewPagerIndicator;
    TextView[] pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerIndicator = (LinearLayout) findViewById(R.id.viewPageIndicator);

        final androidx.viewpager.widget.ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);

        viewPageIndicator();
    }

    public void viewPageIndicator(){
        pagerIndicator = new TextView[3];
        for(int i = 0; i < pagerIndicator.length; i++)
        {
            pagerIndicator[i] = new TextView(this);
            pagerIndicator[i].setText(Html.fromHtml("&#8226;"));
            pagerIndicator[i].setTextSize(35);
            pagerIndicator[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            viewPagerIndicator.addView(pagerIndicator[i]);
        }
    }
}
