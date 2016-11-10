package com.andrewrominger.managemnt.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewrominger.managemnt.R;

/**
 * Created by wesleybanghart on 11/5/16.
 */

public class createSessionFragment extends Fragment {

    FragmentTransaction transaction;
    FragmentManager fragmentManager;

    String TAG = sessionView.class.getSimpleName();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(TAG, "fragment loaded");
        return inflater.inflate(R.layout.create_session_fragment, container, false);
    }
    public void onViewCreated(View v, Bundle savedInstanceState)
    {

    }
    public void onStart()
    {
        super.onStart();
    }
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
    }

}
