package com.example.android.bsuirschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment that displays "Monday".
 */
public class FourthWeekFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        ScheduleAsyncTask task = new ScheduleAsyncTask(this, 4);
        task.execute();
        return rootView;
    }
}

