package com.andrewrominger.managemnt.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Utilities;

import java.util.Calendar;

/**
 * Created by Andrew on 10/21/2016.
 */

public class homeFragment extends Fragment
{
    FragmentTransaction transaction;
    FragmentManager fm;
    private TextView numTasks;
    private TextView numDone;
    private TextView numOver;

    String TAG = homeFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        numTasks = (TextView) view.findViewById(R.id.overViewNumTodo);
        numDone = (TextView) view.findViewById(R.id.overViewNumDone);
        numOver = (TextView) view.findViewById(R.id.overViewNumOver);
        Log.w(TAG, String.valueOf(Utilities.getDaysTasks(getActivity(), Calendar.getInstance()).size()));
        numTasks.setText(String.valueOf(Utilities.getNumTasks(getActivity(), Calendar.getInstance())));
        numDone.setText(String.valueOf(Utilities.getNumTasksDone(getActivity(), Calendar.getInstance())));
        numOver.setText(String.valueOf(Utilities.getNumTasksOver(getActivity(), Calendar.getInstance())));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart()
    {

        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        //transaction.add(R.id.calenderView, new calendarFragment(), "calendar Fragment");
        transaction.add(R.id.nextTaskView, new nextTaskFragment(), "next Task fragment");
        transaction.commit();
        super.onStart();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }
}
