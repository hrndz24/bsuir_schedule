package com.example.android.bsuirschedule;

public class Employee {

    private String mFirstName;
    private String mLastName;
    private String mMiddleName;
    public Employee(String firstName, String lastName, String middleName){
        mFirstName = firstName;
        mLastName = lastName;
        mMiddleName = middleName;
    }



    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }
}
