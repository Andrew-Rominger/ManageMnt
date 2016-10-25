package com.andrewrominger.managemnt.sqlDatabase;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 10/18/2016.
 */

public class sqlContract
{
    private sqlContract(){};

    public static class FeedEntryTasks implements BaseColumns
    {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_TASK_DESCRIPTION = "taskDescription";
        public static final String COLUMN_DUE_DATE_IN_MS = "dueDateInMs";
        public static final String COLUMN_URGANCY = "taskUrgency";
        public static final String COLUMN_MONTH = "taskMonth";
        public static final String COLUMN_DAY = "taskDay";
        public static final String COLUMN_YEAR = "taskYear";
        public static final String COLUMN_HOUR = "taskHour";
        public static final String COLUMN_MINUTE = "taskMinute";


        //DEFINE URGANCY LEVELS
        public static final int LEVEL_URGANCY_LOW = 0;
        public static final int LEVEL_URGANCY_MEDIUM = 1;
        public static final int LEVEL_URGANCY_HIGH = 2;
        public static final int LEVEL_URGANCY_CRIT = 3;

    }
}
