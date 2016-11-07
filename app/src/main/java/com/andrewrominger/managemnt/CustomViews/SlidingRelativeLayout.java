package com.andrewrominger.managemnt.CustomViews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Andrew on 11/6/2016.
 */

public class SlidingRelativeLayout extends RelativeLayout {

    public SlidingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingRelativeLayout(Context context) {
        super(context);
    }

    public float getXFraction()
    {
        int width = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth();
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction)
    {
        int width = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth();
        if (width > 0)
        {
            setX((xFraction * width));
        }
        else setX(0);
    }
}
