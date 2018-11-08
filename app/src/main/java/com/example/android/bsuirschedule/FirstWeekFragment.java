package com.example.android.bsuirschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Fragment that displays "Monday".
 */
public class FirstWeekFragment extends Fragment {

    ScheduleAsyncTask task;

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL =
            "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=873901";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);

        task = new ScheduleAsyncTask(this, 1);
        //URL url = createUrl(USGS_REQUEST_URL);
        //ArrayList<Lesson> lessons = task.doInBackground(url);
        task.execute();
        //ArrayList<Lesson> lessons = task.doInBackground(USGS_REQUEST_URL);

        return rootView;
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    private void updateUi(ArrayList<Lesson> lessons) {
        // set adapter
        // Display the earthquake title in the UI
        LessonAdapter adapter = new LessonAdapter(getActivity(), lessons);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext.getActivity(), R.layout.lesson_text_item, lessons);
        ListView listView = (ListView) getView().findViewById(R.id.lessons_list);
        listView.setAdapter(adapter);

    }
    @Override
    public void onStart() {


        super.onStart();
    }
}

