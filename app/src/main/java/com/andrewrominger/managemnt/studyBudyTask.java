package com.andrewrominger.managemnt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewrominger.managemnt.Fragments.studyBudyFragment;

/**
 * Created by wesleybanghart on 11/21/16.
 */

@SuppressLint("ValidFragment")
public class studyBudyTask extends Fragment {

    String TAG = studyBudyTask.class.getSimpleName();
    studyBudyFragment fragment;

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

        super.onViewCreated(view, savedInstanceState);
    }


}
