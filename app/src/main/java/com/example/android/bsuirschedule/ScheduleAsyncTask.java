package com.example.android.bsuirschedule;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.support.v4.app.Fragment;



public class ScheduleAsyncTask extends AsyncTask<URL, Void, ArrayList<Lesson>> {

    private Fragment mContext;
    private int mWeekNumber;

    public ScheduleAsyncTask(Fragment context, int weekNumber){
        mContext=context;
        mWeekNumber = weekNumber;
    }

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String USGS_REQUEST_URL =
            "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=873901";

    // ArrayList
    private void updateUi(ArrayList<Lesson> lessons) {
        // set adapter
        // Display the earthquake title in the UI
        LessonAdapter adapter = new LessonAdapter(mContext.getActivity(), lessons);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext.getActivity(), R.layout.lesson_text_item, lessons);
        ListView listView = (ListView) mContext.getView().findViewById(R.id.lessons_list);
        listView.setAdapter(adapter);

    }

    @Override
    // ArrayList<Lesson>
    protected ArrayList<Lesson> doInBackground(URL... urls) {
        // Create URL object
        URL url = createUrl(USGS_REQUEST_URL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Lesson> lessons = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
        return lessons;
    }

    /**
     * Update the screen with the given earthquake (which was the result of the
     *
     */
    //ArrayList

    @Override
    protected void onPostExecute(ArrayList<Lesson> lessons) {
        if (lessons == null) {
            return;
        }

        updateUi(lessons);
    }

    /**
     * Returns new URL object from the given string URL.
     */

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

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Lesson} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private ArrayList<Lesson>  extractFeatureFromJson(String earthquakeJSON) {
        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }
        try {
            ArrayList<Lesson> lessons = new ArrayList<>();
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray schedules = baseJsonResponse.getJSONArray("schedules");
            for(int day=0; day<schedules.length(); day++){
                JSONObject weekDay = schedules.getJSONObject(day);
                String wDay = weekDay.getString("weekDay");
                //lessons.add(new Lesson(wDay));
                JSONArray daySubjects = weekDay.getJSONArray("schedule");

                for(int i = 0; i<daySubjects.length(); i++){

                    JSONObject lesson = daySubjects.getJSONObject(i);
                    JSONArray weekNumber = lesson.getJSONArray("weekNumber");
                    for(int j=0; j<weekNumber.length(); j++){
                        if(weekNumber.getInt(j)==mWeekNumber){
                            String title = lesson.getString("subject");
                            String startLesson = lesson.getString("startLessonTime");
                            String endLesson = lesson.getString("endLessonTime");
                            String lessonType = lesson.getString("lessonType");
                            JSONArray auditoryContainer = lesson.getJSONArray("auditory");
                            String auditory ="";
                            if(auditoryContainer.length()>0){
                                auditory = auditoryContainer.getString(0);
                            }
                            JSONArray employeeContainer = lesson.getJSONArray("employee");
                            Employee employeePerson=new Employee(" ", " ", " ");
                            if(employeeContainer.length()>0){
                                JSONObject employee = employeeContainer.getJSONObject(0);
                                String firstName = employee.getString("firstName");
                                String lastName = employee.getString("lastName");
                                String middleName = employee.getString("middleName");
                                employeePerson = new Employee(firstName, lastName, middleName);
                            }

                            lessons.add(new Lesson(title, lessonType, employeePerson, startLesson, endLesson, auditory));
                        }
                    }

                }
            }

            // Create a new {@link Event} object
            return lessons;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}
