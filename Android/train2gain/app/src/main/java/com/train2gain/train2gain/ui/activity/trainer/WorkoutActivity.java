package com.train2gain.train2gain.ui.activity.trainer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.DailyWorkoutRecyclerViewAdapter;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.model.enums.MuscleGroup;
import com.train2gain.train2gain.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class WorkoutActivity extends AppCompatActivity {

    public static final int SCHEDULE_STEP_ACTIVITY_REQUEST_CODE = 1;
    public static final String DAILY_WORKOUT_PARAM = "DAILY_WORKOUT_PARAM";
    public static final String CATEGORY_SET_PARAM = "CATEGORY_SET_PARAM";
    private ArrayList<ScheduleStep> scheduleStepList;
    private ArrayList<RecyclerViewItem> recyclerViewItemList;
    private final DailyWorkoutRecyclerViewAdapter dailyWorkoutRecyclerViewAdapter;
    private ScheduleDailyWorkout currentDailyWorkout;
    private HashSet<String> categorySet;
    public WorkoutActivity(){
        this.dailyWorkoutRecyclerViewAdapter = new DailyWorkoutRecyclerViewAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addworkout);
        categorySet = new HashSet<String>();
        currentDailyWorkout = new ScheduleDailyWorkout();
        scheduleStepList = new ArrayList<ScheduleStep>();
        recyclerViewItemList = new ArrayList<RecyclerViewItem>();
      /*  ExerciseViewModel exercisevm = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exercisevm.getExercises(true).observe(this, exerciseListResource ->{

            exerciseListResource.getData();

        });*/
        FloatingActionButton addScheduleStep = (FloatingActionButton)findViewById(R.id.addworkout_addschedulestep_button);
        addScheduleStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(WorkoutActivity.this, ScheduleStepActivity.class);
                startActivityForResult(startActivity, SCHEDULE_STEP_ACTIVITY_REQUEST_CODE);
            }
        });

        ImageView confirmButton = findViewById(R.id.addworkout_confirm);
        ImageView cancelButton = findViewById(R.id.addworkout_back);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scheduleStepList == null || scheduleStepList.size() == 0){
                    Toast.makeText(getApplicationContext(), "Devi prima aggiungere almeno un esercizioo", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(DAILY_WORKOUT_PARAM, currentDailyWorkout);
                    intent.putExtra(CATEGORY_SET_PARAM, categorySet);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.addworkout_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.dailyWorkoutRecyclerViewAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SCHEDULE_STEP_ACTIVITY_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ScheduleStep selectedScheduleStep = (ScheduleStep) data.getExtras().getSerializable(ScheduleStepActivity.SCHEDULESTEP_PARAM);
                selectedScheduleStep.setOrder(scheduleStepList.size());
                scheduleStepList.add(selectedScheduleStep);
                currentDailyWorkout.setScheduleStepList(scheduleStepList);
                RecyclerViewItem recyclerViewitem = new RecyclerViewItem(RecyclerViewItem.ItemType.CREATION_STANDARD_SET, selectedScheduleStep);
                this.dailyWorkoutRecyclerViewAdapter.setNewRecyclerViewItemList(convertToRecyclerViewItems(scheduleStepList));
                this.dailyWorkoutRecyclerViewAdapter.notifyDataSetChanged();
                if(scheduleStepList.size() == 0){
                    findViewById(R.id.addworkout_noexerciseselected_text).setVisibility(View.VISIBLE);
                } else{
                    findViewById(R.id.addworkout_noexerciseselected_text).setVisibility(View.GONE);
                }
            }
        }
    }

    @NonNull
    private List<RecyclerViewItem> convertToRecyclerViewItems(@NonNull final List<ScheduleStep> scheduleStepList){
        List<RecyclerViewItem> recyclerViewRecyclerViewItems = new ArrayList<RecyclerViewItem>();

        categorySet = new HashSet<String>();
        // Init Recycler view items list
        MuscleGroup muscleGroup = null;
        for(ScheduleStep scheduleStep : scheduleStepList){
            MuscleGroup muscleGroupTemp = scheduleStep.getScheduleSetList().get(0).getScheduleSetItemList().get(0).getExercise().getMuscleGroup();
            if(muscleGroup == null || muscleGroupTemp.getKey() != muscleGroup.getKey()){
                muscleGroup = muscleGroupTemp;
                recyclerViewRecyclerViewItems.add(new RecyclerViewItem<String>(RecyclerViewItem.ItemType.HEADER, muscleGroup.toString()));
                categorySet.add(muscleGroup.toString());
            }
            recyclerViewRecyclerViewItems.add(new RecyclerViewItem<ScheduleStep>(RecyclerViewItem.ItemType.CREATION_STANDARD_SET, scheduleStep));
        }

        return recyclerViewRecyclerViewItems;
    }

}
