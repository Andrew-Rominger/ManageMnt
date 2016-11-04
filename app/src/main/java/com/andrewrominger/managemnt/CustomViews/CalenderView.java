package com.andrewrominger.managemnt.CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.Task;
import com.andrewrominger.managemnt.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andrew on 10/20/2016.
 */

public class CalenderView extends LinearLayout
{
    private RelativeLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    LayoutInflater inflator;
    Calendar curCal;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
    ArrayList<Calendar> days;
    calendarAdapter adapter;

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
        header = (RelativeLayout) findViewById(R.id.header);
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
        days = Utilities.getDays(getContext(), Calendar.getInstance());
        adapter = new calendarAdapter(getContext(), 0, 0, days);
        txtDate.setText(sdf.format(Calendar.getInstance().getTime()));
        grid.setAdapter(adapter);
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
        adapter.adaptMove(Utilities.getDays(getContext(), curCal));
        txtDate.setText(sdf.format(curCal.getTime()));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()));
            }
        });
        animateoutLeft();
    }
    public void moveBackMonth()
    {
        curCal.add(Calendar.MONTH, -1);
        adapter.adaptMove(Utilities.getDays(getContext(), curCal));
        txtDate.setText(sdf.format(curCal.getTime()));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Calendar c = (Calendar) adapterView.getItemAtPosition(i);
                Log.i(TAG, "Clicked on " + sdf2.format(c.getTime()));
            }
        });
        animateOutRight();
    }
    public void animateInRight()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        anim.setDuration(250);
        grid.startAnimation(anim);

    }
    public void animateInLeft()
    {
        adapter.notifyDataSetChanged();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        anim.setDuration(250);
        grid.startAnimation(anim);

    }
    public void animateoutLeft()
    {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
        anim.setDuration(250);
        grid.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {

            public void run()
            {
                adapter.notifyDataSetChanged();
                animateInRight();
            }

        }, anim.getDuration());

    }
    public void animateOutRight()
    {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        anim.setDuration(250);
        grid.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {

            public void run()
            {
                adapter.notifyDataSetChanged();
                animateInLeft();
            }

        }, anim.getDuration());
    }
    public class calendarAdapter extends ArrayAdapter<Calendar>
    {
        ArrayList<Calendar> days;
        ImageView lowCircle;
        ImageView mediumCircle;
        ImageView highCircle;
        ImageView critCircle;

        public calendarAdapter(Context context, int resource, int textViewResourceId, List<Calendar> objects)
        {
            super(context, resource, textViewResourceId, objects);
            days = (ArrayList<Calendar>) objects;
        }
        public void adaptMove(ArrayList<Calendar> newDays)
        {
            Log.d(TAG, "adaptMove: addapting");
            this.days = newDays;
        }
        @Nullable
        @Override
        public Calendar getItem(int position)
        {
            return days.get(position);
        }

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void setCircles(Calendar c)
        {
            ArrayList<Task> list = Utilities.getDaysTasks(getContext(),c);
            for(Task task : list)
            {
                Log.v(TAG, String.valueOf(task.getUrgency()));
                switch (task.getUrgency())
                {
                    case 0:
                        Log.d(TAG, "Day " + c.get(Calendar.DAY_OF_MONTH) + " has task with urgency 0");
                        lowCircle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_light));
                        break;
                    case 1:
                        Log.d(TAG, "Day " + c.get(Calendar.DAY_OF_MONTH) + " has task with urgency 0");
                        mediumCircle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_medium));
                        break;

                    case 2:
                        Log.d(TAG, "Day " + c.get(Calendar.DAY_OF_MONTH) + " has task with urgency 0");
                        highCircle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_high));
                        break;

                    case 3:
                        Log.d(TAG, "Day " + c.get(Calendar.DAY_OF_MONTH) + " has task with urgency 0");
                        critCircle.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_crit));
                        break;
                }
            }
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
                Log.i(TAG, "getView: " + position);
                Calendar c = days.get(position);
                Calendar now = Calendar.getInstance();

                if(convertView == null)
                {
                    convertView = inflator.inflate(R.layout.daylayout, parent, false);
                }
                TextView dateView = (TextView) convertView.findViewById(R.id.dayOfMonthNum);
                lowCircle = (ImageView) convertView.findViewById(R.id.lowCircle);
                mediumCircle = (ImageView) convertView.findViewById(R.id.medCircle);
                highCircle = (ImageView) convertView.findViewById(R.id.highCirlce);
                critCircle = (ImageView) convertView.findViewById(R.id.critCircle);
                lowCircle.setImageDrawable(null);
                mediumCircle.setImageDrawable(null);
                highCircle.setImageDrawable(null);
                critCircle.setImageDrawable(null);
                setCircles(c);
                LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.dayLayoutLin);
                dateView.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                boolean nowb = (c.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)) && (c.get(Calendar.MONTH) == now.get(Calendar.MONTH)) && (c.get(Calendar.YEAR) == now.get(Calendar.YEAR));
                lin.setBackgroundColor(Color.WHITE);
                dateView.setTextColor(Color.BLACK);
                if(position <= 5 && c.get(Calendar.DAY_OF_MONTH) >= 23)
                {
                    lin.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
                }
                if(position >= 29 && c.get(Calendar.DAY_OF_MONTH) <= 14)
                {
                    lin.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
                }
                if(nowb)
                {
                    dateView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                }
                return  convertView;


        }
    }

}
