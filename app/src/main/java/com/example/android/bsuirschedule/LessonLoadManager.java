package com.example.android.bsuirschedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;

public class LessonLoadManager implements LoaderManager.LoaderCallbacks<ArrayList<Lesson>> {

    private static final String URL =
            "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=873901";

    private Fragment mContext;
    private int mWeekNumber;
    private LessonAdapter mAdapter;
    public LessonLoadManager(Fragment context, int weekNumber, LessonAdapter adapter){
        mContext = context;
        mWeekNumber = weekNumber;
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Lesson>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new LessonLoader(mContext.getActivity(), URL, mWeekNumber);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Lesson>> loader, ArrayList<Lesson> lessons) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (lessons != null && !lessons.isEmpty()) {
            mAdapter.addAll(lessons);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Lesson>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
