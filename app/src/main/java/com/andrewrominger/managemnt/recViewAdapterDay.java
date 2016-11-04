package com.andrewrominger.managemnt;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andrew on 11/2/2016.
 */

public class recViewAdapterDay extends RecyclerView.Adapter<recViewAdapterDay.viewHolderDay>
{
    private ArrayList<Task> list;
    private LayoutInflater inflater;
    private Context context;
    boolean noTasks = false;
    public recViewAdapterDay(Context context, Calendar day)
    {
        this.list = Utilities.getDaysTasks(context, day);
        if(list.size() == 0)
        {
            list.add(new Task(" ", 0, "No tasks", -1));
        }
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public recViewAdapterDay.viewHolderDay onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new recViewAdapterDay.viewHolderDay(inflater.inflate(R.layout.taskinday, parent, false));
    }
    @Override
    public void onBindViewHolder(recViewAdapterDay.viewHolderDay holder, int position)
    {
        Task currentTask = list.get(position);
        holder.setData(currentTask,position);
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class viewHolderDay extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView dueDate;
        TextView urgency;
        ImageView check;
        ImageView cross;
        ImageView x1,x2,x3;

        int position;


        public viewHolderDay(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.taskInDayTitle);
            dueDate = (TextView) itemView.findViewById(R.id.taskInDayDueDate);
            urgency = (TextView) itemView.findViewById(R.id.taskInDayUrgency);
            check = (ImageView) itemView.findViewById(R.id.taskInDayCheck);
            cross = (ImageView) itemView.findViewById(R.id.taskInDayCross);
            x1 = (ImageView) itemView.findViewById(R.id.x1);
            x2 = (ImageView) itemView.findViewById(R.id.duedateIMG);
            x3 = (ImageView) itemView.findViewById(R.id.urgencyIMG);
        }

        public void setData(Task t, int position)
        {
            if(t.getUrgency() == -1)
            {
                this.dueDate.setText("");
                this.urgency.setText("");
                this.x1.setImageDrawable(null);
                this.x2.setImageDrawable(null);
                this.x3.setImageDrawable(null);
                this.check.setImageDrawable(null);
                this.cross.setImageDrawable(null);
                this.title.setGravity(Gravity.CENTER_HORIZONTAL);
                this.title.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                this.title.setText("No Tasks");
                return;
            }
            this.title.setText(t.getTitle());
            this.dueDate.setText(Utilities.MonthDayYearsdf.format(t.getDueDate().getTime()));
            this.urgency.setText(Utilities.getUrgString(t.getUrgency()));
            this.setListners(t);
        }
        public void setListners(Task t)
        {
            this.check.setOnClickListener(new recViewAdapterDay.customButtonClickCheck(t));
            this.cross.setOnClickListener(new recViewAdapterDay.customButtonClickCross(t));
        }

    }
    private class customButtonClickCheck implements View.OnClickListener
    {
        Task task;
        String TAG = recViewAdapterDay.customButtonClickCheck.class.getSimpleName();
        public customButtonClickCheck(Task t)
        {
            this.task = t;
        }
        @Override
        public void onClick(View view)
        {
            Log.i(TAG,"CLICKED CHECK FOR " + task.getDueDateMs());
        }
    }
    private class customButtonClickCross implements View.OnClickListener
    {
        Task task;
        String TAG = recViewAdapterDay.customButtonClickCheck.class.getSimpleName();
        public customButtonClickCross(Task t)
        {
            this.task = t;
        }
        @Override
        public void onClick(View view)
        {
            Log.i(TAG,"CLICKED CROSS FOR " + task.getDueDateMs());
        }
    }
}
