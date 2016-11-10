package com.andrewrominger.managemnt.sqlDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 10/18/2016.
 */

public class dbHelperS extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION =2;
    public static final String DATABASE_NAME = "tasks.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INT_TYPE = " INTEGER";
    private static final String SQL_CREATE_ENTRIES =
                            "CREATE TABLE " + sqlContract.FeedEntryTasks.TABLE_NAME + " (" +
                            sqlContract.FeedEntryTasks._ID + " INTEGER PRIMARY KEY," +
                            sqlContract.FeedEntryTasks.COLUMN_TASK_NAME + TEXT_TYPE  + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_TASK_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS + INT_TYPE  + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_URGANCY + INT_TYPE  + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_MONTH + INT_TYPE + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_DAY + INT_TYPE + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_YEAR + INT_TYPE + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_HOUR + INT_TYPE + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_MINUTE + INT_TYPE + COMMA_SEP +
                            sqlContract.FeedEntryTasks.COLUMN_ISCOMPLETE + INT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + sqlContract.FeedEntryTasks.TABLE_NAME;
    public dbHelperS(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
