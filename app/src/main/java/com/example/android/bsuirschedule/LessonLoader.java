package com.example.android.bsuirschedule;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class LessonLoader extends AsyncTaskLoader<ArrayList<Lesson>> {

    /** Query URL */
    private String mUrl;

    private int mWeekNumber;


    public LessonLoader(Context context, String url, int weekNumber) {
        super(context);
        mUrl = url;
        mWeekNumber = weekNumber;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Lesson> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<Lesson> lessons = QueryUtils.extractLessons(mUrl, mWeekNumber);
        return lessons;
    }
}