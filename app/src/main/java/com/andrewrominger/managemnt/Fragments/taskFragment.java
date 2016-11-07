package com.andrewrominger.managemnt.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrewrominger.managemnt.CustomViews.SlidingFrameLayout;
import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Utilities;
import com.andrewrominger.managemnt.addTaskListner;
import com.andrewrominger.managemnt.datePicker;
import com.andrewrominger.managemnt.pickerListner;
import com.andrewrominger.managemnt.recViewAdapter;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;
import com.andrewrominger.managemnt.timePicker;

import java.io.ObjectInputValidation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    SlidingFrameLayout addTaskTab;
    ArrayList<Calendar> list;
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
        list = Utilities.getDaysWithTasks(getActivity(),Calendar.getInstance(),0);
        setupRecView();
        addTaskTab = (SlidingFrameLayout) view.findViewById(R.id.addTaskTab);

        currentOffset = list.size();
        addTaskTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.ctf, new addTaskFragment(taskFragment.this));
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right);
                Animator anim = ObjectAnimator.ofFloat(addTaskTab, "rotation", 0,-90);
                anim.setInterpolator(new AnticipateInterpolator());
                anim.start();
                transaction.commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupRecView()
    {
        adaptar = new recViewAdapter(list,getActivity());
        currentOffset = list.size();
        LinearLayoutManager linLayoutVertical = new LinearLayoutManager(getActivity());
        linLayoutVertical.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycler.setNestedScrollingEnabled(false);
        taskListRecycler.setAdapter(adaptar);
        taskListRecycler.setLayoutManager(linLayoutVertical);
        taskListRecycler.setItemAnimator(new DefaultItemAnimator());
    }
    public void addToRecview()
    {

        ArrayList<Calendar>  l = Utilities.getDaysWithTasks(getActivity(), Calendar.getInstance(), -1);
        adaptar.addDays(l);
        adaptar.notifyItemRangeChanged(currentOffset,10);
    }
    public void updateRecview()
    {
        Log.i(TAG, "Current offset before: " + currentOffset);
        ArrayList<Calendar>  l = Utilities.getDaysWithTasks(getActivity(), Calendar.getInstance(), -1);
        Log.i(TAG, "Current offset after: " + currentOffset);
        adaptar.setData(l);
        adaptar.updatedList();
    }
}
