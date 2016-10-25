package com.andrewrominger.managemnt;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andrew on 10/18/2016.
 */

public class Utilities
{
    public static ArrayList<Calendar> getDays(Context context, Calendar calendar)
    {
        ArrayList<Calendar> arr = new ArrayList<>();
        Calendar c = (Calendar) calendar.clone();
        c.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        //Log.i("Day retured", String.valueOf(dayOfWeek));
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        //Log.i("First Day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        for(int i = 0;i<42;i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(c.getTimeInMillis());
            arr.add(cal);
            c.add(Calendar.DAY_OF_MONTH, 1);

        }
        return arr;

    }
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void createTask(Calendar date, Context context, String title, String description, int urgency)
    {
        dbHelperS helper = new dbHelperS(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        date.set(Calendar.SECOND, 0);

        values.put(sqlContract.FeedEntryTasks.COLUMN_TASK_NAME, title);
        values.put(sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS, date.getTimeInMillis());
        values.put(sqlContract.FeedEntryTasks.COLUMN_TASK_DESCRIPTION, description);
        values.put(sqlContract.FeedEntryTasks.COLUMN_URGANCY, urgency);
        values.put(sqlContract.FeedEntryTasks.COLUMN_DAY, date.get(Calendar.DAY_OF_MONTH));
        values.put(sqlContract.FeedEntryTasks.COLUMN_MONTH, date.get(Calendar.MONTH));
        values.put(sqlContract.FeedEntryTasks.COLUMN_YEAR, date.get(Calendar.YEAR));
        values.put(sqlContract.FeedEntryTasks.COLUMN_MINUTE, date.get(Calendar.MINUTE));
        values.put(sqlContract.FeedEntryTasks.COLUMN_HOUR, date.get(Calendar.HOUR));
        Long newRowID = db.insert(sqlContract.FeedEntryTasks.TABLE_NAME, null, values);
        Toast.makeText(context, "Task Created!", Toast.LENGTH_LONG).show();

    }
    public static void createTask(Context context, int minute, int hour, int day, int month, int year, String title, String description, int urgency)
    {
        Calendar c = makeCal(minute, hour, day, month, year);
        createTask(c,context,title,description,urgency);
    }

    public static Calendar makeCal(int minute, int hour, int day, int month, int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MONTH, month);
        return c;

    }

    public static Calendar makeCal(Long ms)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        return c;
    }

    public static ArrayList<Task> getTasks(Context context)
    {
        dbHelperS helper = new dbHelperS(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Task> list = new ArrayList<>();
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
        c.moveToFirst();

        while (!c.isAfterLast())
        {
            list.add(new Task(c));
            c.moveToNext();
        }
        return list;
    }


}

