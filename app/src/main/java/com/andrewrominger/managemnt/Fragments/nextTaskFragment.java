package com.andrewrominger.managemnt.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Task;
import com.andrewrominger.managemnt.Utilities;
import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andre on 10/21/2016.
 */

public class nextTaskFragment extends Fragment
{
    String TAG = nextTaskFragment.class.getSimpleName();
    TextView title;
    TextView description;
    TextView dueDate;
    TextView urgency;

    SQLiteDatabase db;
    dbHelperS helper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.nexttask_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        title = (TextView) view.findViewById(R.id.nextTaskTitle);
        description = (TextView) view.findViewById(R.id.nextTaskDueDescription);
        dueDate = (TextView) view.findViewById(R.id.nextTaskDueDate);
        urgency = (TextView) view.findViewById(R.id.nextTaskDueUrgency);

    }

    @Override
    public void onStart()
    {
        helper = new dbHelperS(getActivity());
        db = helper.getReadableDatabase();
        String[] projection = {
                sqlContract.FeedEntryTasks._ID,
                sqlContract.FeedEntryTasks.COLUMN_TASK_NAME,
                sqlContract.FeedEntryTasks.COLUMN_TASK_DESCRIPTION,
                sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS,
                sqlContract.FeedEntryTasks.COLUMN_URGANCY,
                sqlContract.FeedEntryTasks.COLUMN_DAY,
                sqlContract.FeedEntryTasks.COLUMN_MONTH,
                sqlContract.FeedEntryTasks.COLUMN_YEAR
        };
        String sortOrder = sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS + " ASC";

        Cursor c = db.query(
                sqlContract.FeedEntryTasks.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        Log.i(TAG, "Grabbed " + c.getCount() + " tasks");
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
            Log.i(TAG, sdf.format(Utilities.makeCal(c.getLong(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS))).getTime()));
            c.moveToNext();
        }
        c.moveToFirst();
        setupNextTask(c);


        super.onStart();
    }

    public void setupNextTask(Cursor c)
    {
        Task task = new Task(c);
        Calendar now = Calendar.getInstance();
        if(task.getDueDateMs() < now.getTimeInMillis())
        {
            c.moveToNext();
            if(c.isAfterLast())
            {
                title.setText("No tasks set!");
                description.setText("");
                urgency.setText("");
                dueDate.setText("");
            }
            else
            {
                setupNextTask(c);
            }
        }
        else
        {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            switch (task.getUrgency())
            {
                case 0:
                    urgency.setText("Low");
                    break;
                case 1:
                    urgency.setText("Medium");
                    break;
                case 2:
                    urgency.setText("High");
                    break;
                case 3:
                    urgency.setText("Criticle");
                    break;

            }
            if(now.get(Calendar.YEAR) == task.getDueDate().get(Calendar.YEAR) && now.get(Calendar.MONTH) == task.getDueDate().get(Calendar.MONTH) && (now.get(Calendar.DAY_OF_MONTH)) == (task.getDueDate().get(Calendar.DAY_OF_MONTH)-1))
            {
                dueDate.setText("Tomorrow");
            }
            else
            {
                dueDate.setText(task.getDueDate().get(Calendar.MONTH)+1 + "/" + task.getDueDate().get(Calendar.DAY_OF_MONTH) + "/" + task.getDueDate().get(Calendar.YEAR));
            }
        }


    }
}
