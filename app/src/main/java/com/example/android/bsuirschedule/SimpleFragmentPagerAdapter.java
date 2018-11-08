package com.example.android.bsuirschedule;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FirstWeekFragment();
        } else if (position == 1){
            return new SecondWeekFragment();
        } else if(position == 2) {
            return new ThirdWeekFragment();
        } else{
            return new FourthWeekFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    /** Setting a title for each fragment */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Week 1";
        } else if(position == 1){
            return "Week 2";
        } else if(position == 2){
            return "Week 3";
        } else{
            return "Week 4";
        }
    }
}
