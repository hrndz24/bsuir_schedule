package com.example.android.bsuirschedule;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LessonAdapter extends ArrayAdapter<Lesson> {

    Context mContext;

    public LessonAdapter(Context context, ArrayList<Lesson> lessons) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.

        super(context, 0, lessons);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Get the Word object located at this position in the list
        Lesson currentLesson = getItem(position);
        if(!(currentLesson.isWeekDay())) {
            View lessonType = (View) listItemView.findViewById(R.id.lesson_type);
            String type = currentLesson.getLessonType();
            switch (type) {
                case "ПЗ":
                    lessonType.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    break;
                case "ЛК":
                    lessonType.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    break;
                case "ЛР":
                    lessonType.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                    break;
            }


            TextView startTime = (TextView) listItemView.findViewById(R.id.lesson_start_time);
            startTime.setText(currentLesson.getLessonStartTime());

            TextView endTime = (TextView) listItemView.findViewById(R.id.lesson_end_time);
            endTime.setText(currentLesson.getLessonEndTime());

            TextView lessonName = (TextView) listItemView.findViewById(R.id.lesson_name);
            lessonName.setText(currentLesson.getLessonName());

            String employeeName = currentLesson.getLessonEmployee().getLastName() + " " +
                    currentLesson.getLessonEmployee().getFirstName() + " " +
                    currentLesson.getLessonEmployee().getMiddleName();
            TextView employee = (TextView) listItemView.findViewById(R.id.lesson_employee);
            employee.setText(employeeName);

            TextView auditory = (TextView) listItemView.findViewById(R.id.lesson_auditory);
            auditory.setText(currentLesson.getLessonAuditory());
        } else{
            TextView lessonName = (TextView) listItemView.findViewById(R.id.lesson_name);
            lessonName.setText(currentLesson.getLessonName());

            TextView employee = (TextView) listItemView.findViewById(R.id.lesson_employee);
            employee.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
