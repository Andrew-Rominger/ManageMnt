package com.andrewrominger.managemnt.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;

import com.andrewrominger.managemnt.CustomViews.SlidingFrameLayout;
import com.andrewrominger.managemnt.R;
import com.andrewrominger.managemnt.studyBudyTask;

/**
 * Created by wesleybanghart on 12/8/16.
 */

public class studyBudyFragment extends Fragment {
    String TAG = studyBudyFragment.class.getSimpleName();
    SlidingFrameLayout studyBudyTag;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.study_budy_fragment, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Log.i(TAG, "onVeiwC");
        studyBudyTag = (SlidingFrameLayout) view.findViewById(R.id.addStudyBudiTab);
        studyBudyTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "why am I dong this, its 4am, I should be sleeping.");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.ctfStudyBudi, new studyBudyTask(studyBudyFragment.this));
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                Animator anim = ObjectAnimator.ofFloat(studyBudyTag, "rotation", 0, -90);
                anim.setInterpolator(new AnticipateInterpolator());
                anim.start();
                transaction.commit();

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

}
