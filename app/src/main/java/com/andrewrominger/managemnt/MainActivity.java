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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

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
        //sv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        transaction = fm.beginTransaction();
        transaction.add(R.id.mainFragmentHolder,new homeFragment(), "homeFragment");
        transaction.commit();
        ma = new menuAdapter(this, com.andrewrominger.managemnt.MenuItem.getData());

        navList.setAdapter(ma);
        navList.setOnItemClickListener(new DrawerItemClickListner());

        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Test Title 1");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Test Title 2");
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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch (item.getItemId())
        {
            case R.id.quickAddTask:
                Log.d(TAG, "Quick add task");
                break;

        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
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
            transaction = fm.beginTransaction();
            changeFragment(i);
        }

        private void changeFragment(int i)
        {
            switch (i)
            {
                case 1:
                    moveToHome();
                    break;
                case 2:
                    moveToTasks();

            }
        }

        private void moveToTasks()
        {

        }

        private void moveToHome()
        {
            homeFragment hf = (homeFragment) getFragmentManager().findFragmentByTag("homeFragment");
            if(hf == null)
            {
                transaction.replace(R.id.mainFragmentHolder, new homeFragment());
            }
        }


    }



}
