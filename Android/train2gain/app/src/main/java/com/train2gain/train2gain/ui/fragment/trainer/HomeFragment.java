package com.train2gain.train2gain.ui.fragment.trainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.train2gain.train2gain.R;
import com.train2gain.train2gain.ui.activity.trainer.ManageWorkoutActivity;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_trainer, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO do things here..

        Button createSchedule = view.findViewById(R.id.home_trainer_btn_new_schedule);
        createSchedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startActivityIntent = new Intent(getActivity(), ManageWorkoutActivity.class);
                long a = 642206013;
                startActivityIntent.putExtra(ManageWorkoutActivity.USERID_PARAM, a  );
                startActivity(startActivityIntent);
            }
        });
    }

}
