package com.andrewrominger.managemnt.Fragments;

import android.app.Fragment;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.andrewrominger.managemnt.R;

/**
 * Created by wesleybanghart on 11/4/16.
 */

public class sessionView extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Button newSession;
    String TAG = sessionView.class.getSimpleName();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        Log.i(TAG, "mdee it to fragmento meme");
        return inflater.inflate(R.layout.sessionviewfragment, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        newSession = (Button) view.findViewById(R.id.newSessionButton);
        fragmentManager = getFragmentManager();
        newSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.createSessionFragmentContainer, new sessionView());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
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
