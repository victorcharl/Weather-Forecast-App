package com.victorcharl.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;


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
        isAppRunning();

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

    private boolean isAppRunning() {
        ActivityManager m = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList =  m.getRunningTasks(10);
        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();
        int n=0;
        while(itr.hasNext()){
            n++;
            itr.next();
        }
        if(n==1){
            SharedPreferences myPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            myPrefs.edit().clear().commit();// App is killed
            return false;
        }

        return true; // App is in background or foreground
    }
}
