package com.example.android.bsuirschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Fragment that displays "Monday".
 */
public class ThirdWeekFragment extends Fragment {

    private LessonAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).

        ListView listView = (ListView) rootView.findViewById(R.id.lessons_list);
        mAdapter = new LessonAdapter(getActivity(), new ArrayList<Lesson>());

        listView.setAdapter(mAdapter);
        LessonLoadManager callbacks = new LessonLoadManager(this, 3, mAdapter);

        loaderManager.initLoader(0, null, callbacks);
        return rootView;
    }
}

