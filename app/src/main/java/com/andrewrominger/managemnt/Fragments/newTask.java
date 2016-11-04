package com.andrewrominger.managemnt.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;

/**
 * Created by Andrew on 11/4/2016.
 */

public class newTask extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.taskfragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}
