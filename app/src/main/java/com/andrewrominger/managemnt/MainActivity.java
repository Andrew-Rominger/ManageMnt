package com.andrewrominger.managemnt;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.andrewrominger.managemnt.Fragments.homeFragment;
import com.andrewrominger.managemnt.Fragments.nextTaskFragment;
import com.andrewrominger.managemnt.Fragments.taskFragment;
import com.andrewrominger.managemnt.sqlDatabase.sqlContract;

import java.util.Calendar;

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

    EditText title;
    EditText description;
    Spinner urgSelector;
    Button addTask;
    TextView dueDate;
    DatePicker datePicker;
    TimePicker timePicker;

    public int quickMin;
    public int quickHour;
    public int quickDay;
    public int quickMonth;
    public int quickYear;
    int urgh = 0;


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
        //sv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);


        transaction = fm.beginTransaction();
        transaction.replace(R.id.mainFragmentHolder,new homeFragment(), "taskFragment");
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
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
            Log.i(TAG, "Item " + i + " Clicked");
            fm = getFragmentManager();
            drawerLayout.closeDrawers();
            transaction = fm.beginTransaction();
            changeFragment(i);
        }
        private void changeFragment(int i)
        {
            switch (i)
            {
                case 0:
                    moveToHome();
                    break;
                case 1:
                    moveToTasks();
            }
        }
        private void moveToTasks()
        {
            Log.i(TAG, "Moving to tasks");
            transaction.replace(R.id.mainFragmentHolder,new taskFragment(), "taskFragment");
            transaction.commit();
        }

        private void moveToHome()
        {
                transaction.replace(R.id.mainFragmentHolder, new homeFragment());
                transaction.commit();
        }
    }
}
