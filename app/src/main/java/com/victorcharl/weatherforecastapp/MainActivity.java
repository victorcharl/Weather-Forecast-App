package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        int page = 0;
        if(bundle != null){
            page = bundle.getInt("page");
        }
        viewPager.setCurrentItem(page);

        viewPageIndicator();
    }

    public void viewPageIndicator(){
        pagerIndicator = new TextView[2];
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
