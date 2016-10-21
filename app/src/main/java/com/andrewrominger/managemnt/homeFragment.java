package com.andrewrominger.managemnt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andrew on 10/21/2016.
 */

public class homeFragment extends Fragment
{
    FragmentTransaction transaction;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onStart()
    {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.calenderView, new calendarFragment(), "calendar Fragment");
        transaction.add(R.id.nextTaskView, new nextTaskFragment(), "next task fragment");
        transaction.commit();
        super.onStart();
    }
}
