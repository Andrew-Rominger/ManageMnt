package com.andrewrominger.managemnt.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Task;
import com.andrewrominger.managemnt.Utilities;
import com.andrewrominger.managemnt.datePicker;
import com.andrewrominger.managemnt.pickerListner;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;
import com.andrewrominger.managemnt.timePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static uk.co.chrisjenx.calligraphy.R.styleable.MenuItem;

/**
 * Created by Andrew on 10/21/2016.
 */

public class taskFragment extends Fragment implements pickerListner, AdapterView.OnItemSelectedListener
{
    String TAG = taskFragment.class.getSimpleName();
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm aa");
    EditText title;
    EditText description;
    TextView dueDate;
    Button addTask;
    Spinner urgSelector;
    SQLiteDatabase db;

    ImageView openPicker;

    int dayPicked;
    int monthPicked;
    int yearPicked;

    int hourPicked;
    int minutePicked;

    int urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.taskfragment, container, false);
    }
    public void setupUI(View view)
    {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText))
        {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event)
                {
                    Utilities.hideSoftKeyboard(getActivity());
                    title.setCursorVisible(false);
                    description.setCursorVisible(false);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
    }
}


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        setupUI(getActivity().findViewById(R.id.activity_mainDrawer));
        final dbHelperS mDb = new dbHelperS(view.getContext());
        db = mDb.getWritableDatabase();
        title = (EditText) view.findViewById(R.id.taskAddTitle);
        description = (EditText) view.findViewById(R.id.taskAddDescription);
        urgSelector = (Spinner) view.findViewById(R.id.taskAddSpinner);
        addTask = (Button) view.findViewById(R.id.taskAddButton);
        openPicker = (ImageView) view.findViewById(R.id.taskAddDueDateButton);
        dueDate = (TextView) view.findViewById(R.id.taskDueDate);
        description.setText("");
        title.setText("");
        dueDate.setText("Due Date");
        openPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPicker();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(description.getText().toString().equals("") || title.getText().toString().equals("") || dueDate.getText().toString().equals("Due Date"))
                {
                    Toast.makeText(view.getContext(), "You need to add a title, description, and pick a date", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String des = description.getText().toString();
                    String tit = title.getText().toString();
                    Calendar c = Utilities.makeCal(minutePicked, hourPicked, dayPicked, monthPicked, yearPicked);
                    Utilities.createTask(c, getActivity(), tit, des, urgh);
                    description.setText("");
                    title.setText("");
                    dueDate.setText("Due Date");
                }

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.urgencyArray, R.layout.simplespinnerlayouy);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        urgSelector.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void setTimes(int hourPicked, int minutePicked)
    {
        this.hourPicked = hourPicked;
        this.minutePicked = minutePicked;
    }

    @Override
    public void setDates(int dayPicked, int monthPicked, int yearPicked)
    {
        this.dayPicked = dayPicked;
        this.monthPicked = monthPicked;
        this.yearPicked = yearPicked;
        //Log.d(TAG, String.valueOf(this.monthPicked));
        dueDate.setText(this.monthPicked + "/" + this.dayPicked + "/" + this.yearPicked + " @ " + this.hourPicked + ":" + this.minutePicked);

    }

    public void openPicker()
    {
        DialogFragment datePicker = new datePicker();
        datePicker.show(getFragmentManager(), "datePicker");
        DialogFragment timePicker = new timePicker();
        timePicker.show(getFragmentManager(), "picker");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
       switch((String) urgSelector.getItemAtPosition(i))
        {
            case "Low":
                this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW;
                break;
            case "Medium":
                this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_MEDIUM;
                break;
            case "High":
                this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_HIGH;
                break;
            case "Criticle":
                this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_CRIT;
                break;
            default:
                this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        this.urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW;
    }
}
