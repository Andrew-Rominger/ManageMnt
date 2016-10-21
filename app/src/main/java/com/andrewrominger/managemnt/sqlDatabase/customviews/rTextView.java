package com.andrewrominger.managemnt.sqlDatabase.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Andrew on 10/17/2016.
 */

public class rTextView extends TextView {
    public rTextView(Context context) {
        super(context);
        init();
    }

    public rTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public rTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public rTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init()
    {
        if(!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ralewayregular.ttf");
            setTypeface(tf);
        }

    }
}
