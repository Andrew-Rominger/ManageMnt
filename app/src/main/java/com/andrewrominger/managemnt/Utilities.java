package com.andrewrominger.managemnt;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.andrewrominger.managemnt.sqlDatabase.dbHelperS;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andrew on 10/18/2016.
 */

public class Utilities
{
    public static SimpleDateFormat MonthDayYearsdf = new SimpleDateFormat("MMM d, yyyy");
    public static SimpleDateFormat fullDateWithTime = new SimpleDateFormat("MMM d, yyyy @ h:mm aa");
    public static SimpleDateFormat justTime = new SimpleDateFormat("h:mm aa");
    public static ArrayList<Calendar> getDays(Context context, Calendar calendar)
    {
        ArrayList<Calendar> arr = new ArrayList<>();
        Calendar c = (Calendar) calendar.clone();
        c.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        //Log.i("Day retured", String.valueOf(dayOfWeek));
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        //Log.i("First Day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
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
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
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
        try {
            Long newRowID = db.insert(sqlContract.FeedEntryTasks.TABLE_NAME, null, values);
        }catch (android.database.sqlite.SQLiteException e)
        {
            Toast.makeText(context, "Error Creating Task", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "Task Created!", Toast.LENGTH_LONG).show();
        db.close();
    }
    public static void createTask(Context context, int minute, int hour, int day, int month, int year, String title, String description, int urgency)
    {
        Calendar c = makeCal(minute, hour, day, month, year);
        createTask(c,context,title,description,urgency);
    }
    public static String getUrgString(int urgency)
    {
        switch (urgency)
        {
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW:
                return "Low";
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_MEDIUM:
                return "Medium";
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_HIGH:
                return "High";
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_CRIT:
                return "Criticle";
            default:
                return "Error getting urgency";
        }
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

    public static ArrayList<Task> getDaysTasks(Context context, Calendar day)
    {
        Calendar calendar = day;
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
        String selection = sqlContract.FeedEntryTasks.COLUMN_DAY + " = ? AND " + sqlContract.FeedEntryTasks.COLUMN_MONTH + " = ? AND "+ sqlContract.FeedEntryTasks.COLUMN_YEAR + " = ?";
        String[] selectionArgs = {String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), String.valueOf(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR))};

        Cursor c = db.query(
                sqlContract.FeedEntryTasks.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
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
        db.close();
        return list;
    }
    public static void deleteAllTasks(Context context)
    {
        dbHelperS helper = new dbHelperS(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(sqlContract.FeedEntryTasks.TABLE_NAME, null, null);
        db.close();
    }
    public static ArrayList<Calendar> getNext6Days(Context context, Calendar from)
    {
        ArrayList<Calendar> cal = new ArrayList<>();
        Log.i("getNext6Days", "PASSED: " +Utilities.MonthDayYearsdf.format(from.getTime()));
        for(int i = 0;i<6;i++)
        {
            Calendar temp = Calendar.getInstance();
            temp.setTimeInMillis(from.getTimeInMillis());
            temp.add(Calendar.DAY_OF_MONTH, i);
            cal.add(temp);
            Log.i("getNext6Days", "ADDING: " + Utilities.MonthDayYearsdf.format(temp.getTime()));
        }
        Log.i("getNext6Days", String.valueOf(cal.size()));
        return cal;

    }

    public static int getUrgColor(int urgency, Context context)
    {
        switch (urgency)
        {
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_LOW:
                return ContextCompat.getColor(context, R.color.colorPrimaryLight);
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_MEDIUM:
                return ContextCompat.getColor(context, R.color.colorPrimary);
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_HIGH:
                return ContextCompat.getColor(context, R.color.colorPrimaryDark);
            case sqlContract.FeedEntryTasks.LEVEL_URGANCY_CRIT:
                return ContextCompat.getColor(context, R.color.colorAccent);
            default:
                return ContextCompat.getColor(context, R.color.colorPrimary);
        }
    }

    public static ArrayList<Calendar> getDaysWithTasks(Context context, Calendar from, int offset)
    {
        final int numtofetch = 10;
        ArrayList<Calendar> tasks = new ArrayList<>();
        dbHelperS helper = new dbHelperS(context);
        SQLiteDatabase db = helper.getReadableDatabase();
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
        String sortOrder = sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS + " ASC LIMIT " + offset + "," + numtofetch;
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
            Calendar newCal = makeCal(c);
            int q = tasks.size();
            int isoff = 0;
            for(int i = 0;i<q;i++)
            {
                if(tasks.get(i).get(Calendar.DAY_OF_YEAR)!= newCal.get(Calendar.DAY_OF_YEAR))
                {
                   isoff++;
                }
            }
            if(isoff == tasks.size())
            {
                tasks.add(newCal);
            }
            c.moveToNext();
        }
        return tasks;
    }

    public static Calendar makeCal(Cursor c)
    {
        return makeCal(c.getLong(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS)));
    }

}

