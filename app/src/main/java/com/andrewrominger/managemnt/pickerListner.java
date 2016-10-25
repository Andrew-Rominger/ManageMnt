package com.andrewrominger.managemnt;

/**
 * Created by Andrew on 10/21/2016.
 */

public interface pickerListner
{
    public void setTimes(int hourPicked, int minutePicked);
    public void setDates(int dayPicked, int monthPicked, int yearPicked);
}
