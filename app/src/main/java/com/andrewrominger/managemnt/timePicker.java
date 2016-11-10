package com.andrewrominger.managemnt;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import com.andrewrominger.managemnt.Fragments.addTaskFragment;
import com.andrewrominger.managemnt.Fragments.taskFragment;

import java.util.Calendar;

/**
 * Created by Andrew on 10/21/2016.
 */

public class timePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
    int hourpicked;
    int minutepicked;
    addTaskFragment fragment;
    @SuppressLint("ValidFragment")
    public timePicker(addTaskFragment fragment)
    {
        this.fragment = fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int currHour = c.get(Calendar.HOUR_OF_DAY);
        int currMinute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,currHour,currMinute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        FragmentManager fm = getFragmentManager();
        this.hourpicked = hourOfDay;
        this.minutepicked = minute;
        pickerListner pickerlistner = (pickerListner) fragment;
        pickerlistner.setTimes(hourpicked,minutepicked);
        Log.i("Setting time" , "test");
    }





}