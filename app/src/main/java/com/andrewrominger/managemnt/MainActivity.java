package com.andrewrominger.managemnt;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.andrewrominger.managemnt.Fragments.addTaskFragment;
import com.andrewrominger.managemnt.Fragments.calendarFragment;
import com.andrewrominger.managemnt.Fragments.homeFragment;
import com.andrewrominger.managemnt.Fragments.studyBudyFragment;
import com.andrewrominger.managemnt.Fragments.taskFragment;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FragmentManager fm;
    FragmentTransaction transaction;
    ScrollView sv;
    ViewFlipper viewFlipper;

    boolean isTaskDisplayed;
    TextView numTasks, numDone, numOver;
    ImageView rect;
    addTaskFragment frag;


    FrameLayout fragmentholder;


    public ListView navList;
    menuAdapter ma;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindrawer);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Raleway-Bold.ttf").setFontAttrId(R.attr.fontPath).build());
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_mainDrawer);
        navList = (ListView) findViewById(R.id.menuList);
        fm = getFragmentManager();
        sv = (ScrollView) findViewById(R.id.scrollViewMain);
        fragmentholder = (FrameLayout) findViewById(R.id.addTaskFrame);
        rect = (ImageView) findViewById(R.id.rect);
        isTaskDisplayed = false;
        //sv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        transaction = fm.beginTransaction();
        transaction.replace(R.id.mainFragmentHolder,new homeFragment(), "taskFragment");
        //transaction.addToBackStack(null);
        transaction.commit();

        ma = new menuAdapter(this, com.andrewrominger.managemnt.MenuItem.getData());

        navList.setAdapter(ma);
        navList.setOnItemClickListener(new DrawerItemClickListner());
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        getSupportActionBar().setElevation(0);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //comment this method out to reset the font type
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    class DrawerItemClickListner implements ListView.OnItemClickListener
    {
        FragmentManager fm;
        FragmentTransaction transaction;
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {

            Log.i(TAG, "Item " + i + " Clicked");
            fm = getFragmentManager();
            drawerLayout.closeDrawers();

            changeFragment(i);
        }

        private void changeFragment(int i)
        {

            if(i != 1)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_rightt);
                anim.setDuration(500);
                anim.setFillAfter(true);
            }

            switch (i)
            {
                case 0:
                    getSupportActionBar().setElevation(0);
                    moveToHome();
                    break;
                case 1:
                    getSupportActionBar().setElevation(4 * getResources().getDisplayMetrics().density);
                    moveToTasks();
                    break;
                case 2:
                    getSupportActionBar().setElevation(4 * getResources().getDisplayMetrics().density);
                    moveToCalendar();
                    break;
                case 3:
                    getSupportActionBar().setElevation(4 * getResources().getDisplayMetrics().density);
                    moveToStudyBudy();
            }
        }
        private void moveToTasks()
        {
            transaction = fm.beginTransaction();
            Log.i(TAG, "Moving to tasks");
            taskFragment TaskFragment = new taskFragment();
            transaction.replace(R.id.mainFragmentHolder,TaskFragment, "taskFragment");
            //transaction.addToBackStack(null);
            transaction.commit();
        }

        private void moveToHome()
        {
            transaction = fm.beginTransaction();
            homeFragment HomeFragment =  new homeFragment();
            transaction.replace(R.id.mainFragmentHolder,HomeFragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        }
        private void moveToCalendar()
        {
            transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFragmentHolder,new calendarFragment());
            //transaction.addToBackStack(null);
            transaction.commit();
        }
        private void moveToStudyBudy()
        {
            transaction = fm.beginTransaction();
            studyBudyFragment sbf = new studyBudyFragment();
            transaction.replace(R.id.mainFragmentHolder, sbf);
            transaction.commit();
        }
    }
}
