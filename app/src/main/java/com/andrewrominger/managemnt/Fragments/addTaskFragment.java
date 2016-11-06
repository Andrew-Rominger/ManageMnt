package com.andrewrominger.managemnt.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.andrewrominger.managemnt.pickerListner;
import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Utilities;
import com.andrewrominger.managemnt.datePicker;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;
import com.andrewrominger.managemnt.timePicker;

import java.util.Calendar;

/**
 * Created by Andre on 11/4/2016.
 */

public class addTaskFragment extends android.support.v4.app.Fragment implements pickerListner, AdapterView.OnItemSelectedListener
{
    String TAG = addTaskFragment.class.getSimpleName();
    EditText title,description;
    TextView dueDate;
    ImageView openPickers;
    Spinner spinner;
    Button addTask;

    int dayPicked;
    int monthPicked;
    int yearPicked;

    int hourPicked;
    int minutePicked;

    int urgh = sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.addtaskfragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        title = (EditText) view.findViewById(R.id.taskAddTitle);
        description = (EditText) view.findViewById(R.id.taskAddDescription);
        dueDate = (TextView) view.findViewById(R.id.taskDueDate);
        openPickers = (ImageView) view.findViewById(R.id.taskAddDueDateButton);
        spinner = (Spinner) view.findViewById(R.id.taskAddSpinner);
        addTask = (Button) view.findViewById(R.id.taskAddButton);

        description.setText("");
        title.setText("");
        dueDate.setText("Due Date");
        openPickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickers();
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
                    Log.i(TAG, "URG is " + urgh);
                    Utilities.createTask(c, getActivity(), tit, des, urgh);
                    description.setText("");
                    title.setText("");
                    dueDate.setText("Due Date");
                }

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.urgencyArray, R.layout.simplespinnerlayouy);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    private void openPickers()
    {
        DialogFragment datePicker = new datePicker(this);
        datePicker.show(getActivity().getFragmentManager(),"datePicker");
        DialogFragment timePicker = new timePicker(this);
        timePicker.show(getActivity().getFragmentManager(), "picker");
    }

    @Override
    public void setTimes(int hourPicked, int minutePicked) {
        this.hourPicked = hourPicked;
        this.minutePicked=minutePicked;
    }

    @Override
    public void setDates(int dayPicked, int monthPicked, int yearPicked)
    {
        this.dayPicked=dayPicked;
        this.monthPicked=monthPicked;
        this.yearPicked=yearPicked;
        dueDate.setText(Utilities.fullDateWithTime.format(Utilities.makeCal(this.minutePicked,this.hourPicked,this.dayPicked,this.monthPicked,this.yearPicked).getTime()));
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        Log.i(TAG, "PASSED " + spinner.getItemAtPosition(i));
        switch((String) spinner.getItemAtPosition(i))
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
