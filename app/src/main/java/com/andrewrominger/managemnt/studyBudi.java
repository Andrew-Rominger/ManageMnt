package com.andrewrominger.managemnt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andrewrominger.managemnt.Fragments.sessionView;

public class studyBudi extends AppCompatActivity {

    Button start;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_budi);
        start = (Button) findViewById(R.id.startButton);
        fragmentManager = getFragmentManager();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.sessionViewContainer, new sessionView());
                transaction.addToBackStack(null);
                transaction.commit();
                */
                changeFragment(R.id.sessionViewContainer);
            }
        });
    }
    public void changeFragment(int a)
    {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(a, new sessionView());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
