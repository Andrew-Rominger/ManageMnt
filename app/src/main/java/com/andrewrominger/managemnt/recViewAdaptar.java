package com.andrewrominger.managemnt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/24/2016.
 */

public class recViewAdaptar extends RecyclerView.Adapter<recViewAdaptar.viewHolder>
{
    private ArrayList<Task> list;
    private LayoutInflater inflater;
    private Context context;
    public recViewAdaptar(Context context, ArrayList<Task> list)
    {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public recViewAdaptar.viewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.dayinrec, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(recViewAdaptar.viewHolder holder, int position)
    {
        Task t = list.get(position);
        holder.setData(t,position);
        holder.setListners();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView day;
        ListView listView;

        public viewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }

        public void setData(Task t, int position)
        {

        }

        public void setListners()
        {
        }
    }
}
