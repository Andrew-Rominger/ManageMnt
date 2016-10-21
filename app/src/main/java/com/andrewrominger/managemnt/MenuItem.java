package com.andrewrominger.managemnt;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/18/2016.
 */

public class MenuItem
{
    private int iconPath;
    private String Title;
    private String content;

    public MenuItem(int iconPath, String title, String content) {
        this.iconPath = iconPath;
        Title = title;
        this.content = content;
    }

    public int getIcon() {
        return iconPath;
    }

    public void setIcon(int icon) {
        this.iconPath = icon;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static ArrayList<MenuItem> getData()
    {
        ArrayList<MenuItem> arr = new ArrayList<>();

        MenuItem homePage = new MenuItem(R.drawable.home, "Home", "Take me home tonight");
        arr.add(homePage);

        MenuItem tasksPage = new MenuItem(R.drawable.check, "Tasks", "Organize your tasks and todo lists");
        arr.add(tasksPage);

        MenuItem calendarPage = new MenuItem(R.drawable.calendar_text, "Calendar", "View your tasks and block out time");
        arr.add(calendarPage);

        MenuItem studiBudiPage = new MenuItem(R.drawable.book_open_page_variant, "Study Budi", "Less distractions, less wasted time, more studying");
        arr.add(studiBudiPage);

        MenuItem wakeMeUpPage = new MenuItem(R.drawable.alarm, "WakeMeUp", "Set alarms and notificaions");
        arr.add(wakeMeUpPage);

        MenuItem settingsPage = new MenuItem(R.drawable.wrench, "Settings", "Tinker and tweak");
        arr.add(settingsPage);

        return arr;
    }
}
