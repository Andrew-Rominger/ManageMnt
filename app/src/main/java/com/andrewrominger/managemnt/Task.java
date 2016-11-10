package com.andrewrominger.managemnt;

import android.database.Cursor;
import android.util.Log;

import com.andrewrominger.managemnt.sqlDatabase.sqlContract;

import java.util.Calendar;

/**
 * Created by Andrew on 10/21/2016.
 */

public class Task
{
    private Calendar dueDate;
    private int urgency;
    private String description;
    private String title;
    private long dueDateMs;
    private String day;
    private String month;
    private String year;
    private String minute;
    private String hour;
    private boolean isCompleted;
    String TAG = Task.class.getSimpleName();

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Task(String description, Calendar dueDate, long dueDateMs, String title, int urgency)
    {
        this.description = description;
        this.dueDate = dueDate;
        this.dueDateMs = dueDateMs;
        this.title = title;
        this.urgency = urgency;
    }
    public Task(String description,long dueDateMs, String title, int urgency)
    {
        this.description = description;
        this.dueDate = Utilities.makeCal(dueDateMs);
        this.dueDateMs = dueDateMs;
        this.title = title;
        this.urgency = urgency;
    }
    public Task(Cursor c)
    {
        this.title = c.getString(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_TASK_NAME));
        this.description = c.getString(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_TASK_DESCRIPTION));
        this.dueDateMs = c.getLong(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_DUE_DATE_IN_MS));
        this.urgency = c.getInt(c.getColumnIndexOrThrow(sqlContract.FeedEntryTasks.COLUMN_URGANCY));
        this.dueDate = Utilities.makeCal(this.dueDateMs);
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public long getDueDateMs() {
        return dueDateMs;
    }

    public void setDueDateMs(long dueDateMs) {
        this.dueDateMs = dueDateMs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public boolean isBefore(Task task)
    {
        return this.getDueDateMs() < task.getDueDateMs();
    }
}
