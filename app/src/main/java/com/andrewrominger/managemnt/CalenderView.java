package com.andrewrominger.managemnt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 10/20/2016.
 */

public class CalenderView extends LinearLayout
{
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    LayoutInflater inflator;
    Calendar curCal;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");

    final String TAG = CalenderView.class.getSimpleName();
    public CalenderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl();
    }

    private void initControl()
    {
         inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.calendarlayout, this);
        header = (LinearLayout) findViewById(R.id.header);
        btnNext = (ImageView) findViewById(R.id.calenderMoveFoward);
        btnPrev = (ImageView) findViewById(R.id.calenderMoveBack);
        txtDate = (TextView) findViewById(R.id.calendarMonthName);
        grid = (GridView) findViewById(R.id.calendarGrid);
        curCal = Calendar.getInstance();
        initCalendar();
    }

    public void initCalendar()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        ArrayList<Calendar> days = Utilities.getDays(getContext(), Calendar.getInstance());
        txtDate.setText(sdf.format(Calendar.getInstance().getTime()));
        grid.setAdapter(new calendarAdapter(getContext(), 0, 0, days));

        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             moveFowardMonth();
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
             moveBackMonth();
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()));
            }
        });

    }

    public void moveFowardMonth()
    {
        curCal.add(Calendar.MONTH, 1);
        grid.setAdapter(new calendarAdapter(getContext(),0,0,Utilities.getDays(getContext(), curCal)));
        txtDate.setText(sdf.format(curCal.getTime()));
    }
    public void moveBackMonth()
    {
        curCal.add(Calendar.MONTH, -1);
        grid.setAdapter(new calendarAdapter(getContext(),0,0,Utilities.getDays(getContext(), curCal)));
        txtDate.setText(sdf.format(curCal.getTime()));
    }

    public class calendarAdapter extends ArrayAdapter<Calendar>
    {
        ArrayList<Calendar> days;

        public calendarAdapter(Context context, int resource, int textViewResourceId, List<Calendar> objects)
        {
            super(context, resource, textViewResourceId, objects);
            days = (ArrayList<Calendar>) objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Calendar c = days.get(position);
            Calendar now = Calendar.getInstance();

            if(convertView == null)
            {
                convertView = inflator.inflate(R.layout.daylayout, parent,false);
            }
            TextView dateView = (TextView) convertView.findViewById(R.id.dayOfMonthNum);
            dateView.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

            if((c.get(Calendar.DAY_OF_MONTH) >= 25 && position < 6) || (c.get(Calendar.DAY_OF_MONTH) <=10 && position > 28))
            {
                dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyDark));
            }
            if(now.after(c))
            {
                convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
            }
            if((c.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) && (c.get(Calendar.MONTH) == now.get(Calendar.MONTH)) && (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)))
            {
                dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                convertView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                dateView.setTypeface(null, Typeface.BOLD);
            }


            return  convertView;



        }
    }

}
