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
        TextView discription;
        ImageView check;
        ImageView colorIndicator;
        View item;

        String TAG = viewHolderDay.class.getSimpleName();

        int position;


        public viewHolderDay(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.taskTaskName);
            dueDate = (TextView) itemView.findViewById(R.id.taskTaskDueDate);
            check = (ImageView) itemView.findViewById(R.id.taskTaskCheck);
            colorIndicator = (ImageView) itemView.findViewById(R.id.taskColor);
            discription = (TextView) itemView.findViewById(R.id.taskTaskDescription);
            this.item = itemView;
        }

        public void setData(Task t, int position)
        {
            this.title.setText(t.getTitle());
            this.dueDate.setText(Utilities.justTime.format(t.getDueDate().getTime()));
            this.colorIndicator.setBackgroundColor(Utilities.getUrgColor(t.getUrgency(), itemView.getContext()));
            if(t.getDescription().contains("\n"))
            {
                String finals = "";
                char[] arr = t.getDescription().toCharArray();

                for(int i =0;i<arr.length;i++)
                {
                    if(arr[i] == '\n')
                    {
                        finals = t.getDescription().substring(0, i) + "...";
                        break;
                    }
                }
                this.discription.setText(finals);
            }
            else if(t.getDescription().length() > 30)
            {
                this.discription.setText(t.getDescription().substring(0,30) + "...");
            }
            else
            {
                this.discription.setText(t.getDescription());
            }

            this.setListners(t);
        }
        public void setListners(Task t)
        {
            this.check.setOnClickListener(new recViewAdapterDay.customButtonClickCheck(t));
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
