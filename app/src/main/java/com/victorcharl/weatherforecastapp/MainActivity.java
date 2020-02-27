package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    com.google.android.material.tabs.TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableLayout = findViewById(R.id.tab_layout);
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_label1));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_label2));
        tableLayout.addTab(tableLayout.newTab().setText(R.string.tab_label3));
        tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final androidx.viewpager.widget.ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tableLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //transitions between pages
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tableLayout));
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
