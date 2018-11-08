package com.example.android.bsuirschedule;


public class Lesson {

    private String mStartTime=WEEK_DAY;
    private String mEndTime;
    private String mName;
    private String mAuditory;
    private Employee mEmployee;
    private String mType;
    private static final String WEEK_DAY ="abc";


    public Lesson(String name, String type, Employee employee,
                  String startTime, String endTime, String auditory) {
        mName = name;
        mType = type;
        mEmployee = employee;
        mStartTime = startTime;
        mEndTime = endTime;
        mAuditory = auditory;
    }

    public Lesson(String weekDay){
        mName = weekDay;
    }
    public String getLessonName(){
        return mName;
    }

    public String getLessonType() {
        return mType;
    }

    public Employee getLessonEmployee() {
        return mEmployee;
    }

    public String getLessonStartTime() {
        return mStartTime;
    }

    public String getLessonEndTime() {
        return mEndTime;}

    public String getLessonAuditory() {
        return mAuditory;
    }
    public boolean isWeekDay(){
        return(mStartTime.equals(WEEK_DAY));
    }

}
