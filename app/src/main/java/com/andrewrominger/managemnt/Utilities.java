package com.andrewrominger.managemnt;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

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
        Calendar c = calendar;
        c.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        //Log.i("Day retured", String.valueOf(dayOfWeek));
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        //Log.i("First Day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        for(int i = 0; i < 42; i++)
        {
            arr.add(c);
            //Log.i("Day: ", sdf.format(c.getTime()));
            c.add(Calendar.DAY_OF_MONTH, 1);

        }
        return arr;

    }

}
