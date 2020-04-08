package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    LinearLayout viewPagerIndicator;
    TextView[] pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPagerIndicator = (LinearLayout)findViewById(R.id.viewPageIndicator);

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
            pagerIndicator[i].setTextColor(getResources().getColor(R.color.colorAccent));

            viewPagerIndicator.addView(pagerIndicator[i]);
        }
    }
}
