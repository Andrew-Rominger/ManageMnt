package com.andrewrominger.managemnt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Andrew on 10/18/2016.
 */

public class menuAdapter extends ArrayAdapter<MenuItem>
{
    public menuAdapter(Context context, ArrayList<MenuItem> objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MenuItem menuItem = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nav_menu_element, parent, false);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.navMenuListIcon);

        TextView title = (TextView) convertView.findViewById(R.id.navMenuListTitle);

        TextView content = (TextView) convertView.findViewById(R.id.navMenuListContent);

        iv.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), menuItem.getIcon(), null));

        title.setText(menuItem.getTitle());

        content.setText(menuItem.getContent());

        return convertView;
    }
}
