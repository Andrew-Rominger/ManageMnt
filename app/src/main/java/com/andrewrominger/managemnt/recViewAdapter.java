package com.andrewrominger.managemnt;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andrew on 11/2/2016.
 */

public class recViewAdapter extends RecyclerView.Adapter<recViewAdapter.viewHolderWeek>
{
    String TAG = recViewAdapter.class.getSimpleName();
    ArrayList<Calendar> list;
    private Context context;
    private LayoutInflater inflater;

    public recViewAdapter(ArrayList<Calendar> list, Context context)
    {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public recViewAdapter.viewHolderWeek onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.i(TAG, "onCreateViewHolder");
        return new viewHolderWeek(inflater.inflate(R.layout.dayinweek, parent, false), context);
    }

    @Override
    public void onBindViewHolder(recViewAdapter.viewHolderWeek holder, int position)
    {
        Log.i(TAG, "BINDING");
        Calendar current = list.get(position);
        holder.setData(current,position);
    }

    public void addDays(ArrayList<Calendar> newDays)
    {
        for(Calendar c : newDays)
        {
            this.list.add(c);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolderWeek extends RecyclerView.ViewHolder
    {
        RecyclerView dayRecView;
        Context context;
        TextView dayLabel;
        int position;
        Calendar calendar;
        public viewHolderWeek(View inflate, Context context)
        {
            super(inflate);
            Log.i(TAG, "viewHolderWeek");
            this.context = context;
            dayRecView = (RecyclerView) inflate.findViewById(R.id.recDayInWeek);
            dayLabel = (TextView) inflate.findViewById(R.id.dayDayInWeek);
        }
        public void setData(Calendar c, int position)
        {
            dayLabel.setText(Utilities.MonthDayYearsdf.format(c.getTime()));
            recViewAdapterDay adaptar = new recViewAdapterDay(context, list.get(position));
            dayRecView.setAdapter(adaptar);
            LinearLayoutManager linLayoutVertical = new LinearLayoutManager(context)
            {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linLayoutVertical.setOrientation(LinearLayoutManager.VERTICAL);
            dayRecView.setLayoutManager(linLayoutVertical);
            dayRecView.setItemAnimator(new DefaultItemAnimator());
        }

    }
}
