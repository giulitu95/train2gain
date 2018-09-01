package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    public static final int SCHEDULE_STEP_ACTIVITY_REQUEST_CODE = 1;
    private ArrayList<ScheduleStep> scheduleStepList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addworkout);

        scheduleStepList = new ArrayList<ScheduleStep>();
        ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exercisevm.getExercises(true).observe(this, exerciseListResource ->{

            exerciseListResource.getData();

        });
        FloatingActionButton addScheduleStep = (FloatingActionButton)findViewById(R.id.addworkout_addschedulestep_button);
        addScheduleStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(WorkoutActivity.this, ScheduleStepActivity.class);
                startActivityForResult(startActivity, SCHEDULE_STEP_ACTIVITY_REQUEST_CODE);
            }
        });
        //*********************************************************************
        // Init activity top bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.addworkout_toolbar);
        setSupportActionBar(toolbar);

        // Sets menu open button on toolbar
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //*****************************************************
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SCHEDULE_STEP_ACTIVITY_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ScheduleStep selectedScheduleStep = (ScheduleStep) data.getExtras().getSerializable(ScheduleStepActivity.SCHEDULESTEP_PARAM);
                scheduleStepList.add(selectedScheduleStep);
            }
        }
    }

}
