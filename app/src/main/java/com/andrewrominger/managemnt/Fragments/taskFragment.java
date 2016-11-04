package com.andrewrominger.managemnt.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Utilities;
import com.andrewrominger.managemnt.datePicker;
import com.andrewrominger.managemnt.pickerListner;
import com.andrewrominger.managemnt.recViewAdapter;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;
import com.andrewrominger.managemnt.timePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andrew on 10/21/2016.
 */

public class taskFragment extends Fragment
{
    String TAG = taskFragment.class.getSimpleName();
    SQLiteDatabase db;
    RecyclerView taskListRecycler;
    recViewAdapter adaptar;
    int currentOffset = 0;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.taskfragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        final dbHelperS mDb = new dbHelperS(view.getContext());
        db = mDb.getWritableDatabase();
        taskListRecycler = (RecyclerView) view.findViewById(R.id.taskListRecycler);
        setupRecView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupRecView()
    {
        adaptar = new recViewAdapter(Utilities.getDaysWithTasks(getActivity(),Calendar.getInstance(),currentOffset),getActivity());
        currentOffset+=10;
        LinearLayoutManager linLayoutVertical = new LinearLayoutManager(getActivity());
        linLayoutVertical.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycler.setNestedScrollingEnabled(false);
        taskListRecycler.setAdapter(adaptar);
        taskListRecycler.setLayoutManager(linLayoutVertical);
        taskListRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void addToRecview()
    {
        adaptar.addDays(Utilities.getDaysWithTasks(getActivity(), Calendar.getInstance(), currentOffset));
        adaptar.notifyItemRangeChanged(currentOffset,10);
        currentOffset+=10;
    }



}
