package com.victorcharl.weatherforecastapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumberofTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNumberofTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WeatherToday();
            case 1:
                return new SearchOtherLocationWeather();
            case 2:
                return new SetNotification();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumberofTabs;
    }
}
