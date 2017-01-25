package com.andrewrominger.managemnt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.andrewrominger.managemnt.Fragments.studyBudyFragment;

/**
 * Created by wesleybanghart on 11/21/16.
 */

@SuppressLint("ValidFragment")
public class studyBudyTask extends Fragment implements pickerListner, AdapterView.OnItemSelectedListener{

    String TAG = studyBudyTask.class.getSimpleName();
    studyBudyFragment fragment;
    ImageButton cycle;
    ImageButton stdyBreak;
    ImageButton stdyLength;
    ProgressBar studyLengthProgressBar;

    @SuppressLint("ValidFragment")
    public studyBudyTask(studyBudyFragment fragment)
    {
        this.fragment = fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.study_budy_task, container ,false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        cycle = (ImageButton) view.findViewById(R.id.cycleImageButton);
        stdyBreak = (ImageButton) view.findViewById(R.id.studyBreakLengthImageButtno);
        stdyLength = (ImageButton) view.findViewById(R.id.studyLengthImageButton);

        studyLengthProgressBar = (ProgressBar) view.findViewById(R.id.studyLengthProgressBar);

        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        stdyBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        stdyLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void setTimes(int hourPicked, int minutePicked) {

    }

    @Override
    public void setDates(int dayPicked, int monthPicked, int yearPicked) {

    }
}
