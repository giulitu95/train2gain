package com.train2gain.train2gain.ui.fragment.athlete;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.train2gain.train2gain.R;
import com.train2gain.train2gain.adapter.DailyWorkoutRecyclerViewAdapter;
import com.train2gain.train2gain.adapter.model.RecyclerViewItem;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.model.enums.MuscleGroup;
import com.train2gain.train2gain.viewmodel.ScheduleDailyWorkoutViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyWorkoutFragment extends Fragment {

    private final static String ATHLETE_USER_ID_KEY = "ATHLETE_USER_ID_KEY";

    private long athleteUserId = -1;
    private final DailyWorkoutRecyclerViewAdapter dailyWorkoutRecyclerViewAdapter;

    public static DailyWorkoutFragment newInstance(final long athleteUserId){
        DailyWorkoutFragment dailyWorkoutFragment = new DailyWorkoutFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putLong(DailyWorkoutFragment.ATHLETE_USER_ID_KEY, athleteUserId);
        dailyWorkoutFragment.setArguments(fragmentArgs);
        return dailyWorkoutFragment;
    }

    public DailyWorkoutFragment(){
        this.dailyWorkoutRecyclerViewAdapter = new DailyWorkoutRecyclerViewAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get fragment argument (athlete user id)
        Bundle fragmentParams = getArguments();
        if(fragmentParams != null && fragmentParams.containsKey(DailyWorkoutFragment.ATHLETE_USER_ID_KEY)){
            this.athleteUserId = (Long) fragmentParams.getLong(DailyWorkoutFragment.ATHLETE_USER_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_daily_workout_athlete, parent, false);
        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.daily_workout_schedule_steps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.dailyWorkoutRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Get dailyWorkout and update the recyclerView
        ScheduleDailyWorkoutViewModel scheduleDailyWorkoutViewModel = ViewModelProviders.of(this).get(ScheduleDailyWorkoutViewModel.class);
        scheduleDailyWorkoutViewModel.getScheduleDailyWorkoutOfTheDay(this.athleteUserId).observe(this, scheduleDailyWorkoutResource -> {
            if(scheduleDailyWorkoutResource != null && scheduleDailyWorkoutResource.getData() != null){
                List<RecyclerViewItem> recyclerViewRecyclerViewItems = convertToRecyclerViewItems(scheduleDailyWorkoutResource.getData());
                this.dailyWorkoutRecyclerViewAdapter.setNewRecyclerViewItemList(recyclerViewRecyclerViewItems);
                this.dailyWorkoutRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.daily_workout_title));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Nothing to do
    }

    /**
     * Prepares the list of items that will be displayed in the daily workout's recycler view
     * @param scheduleDailyWorkout the object that contains the list of items that we want to display in the recycler view
     * @return the list of items that will be displayed in the daily workout's recycler view
     */
    @NonNull
    private List<RecyclerViewItem> convertToRecyclerViewItems(@NonNull final ScheduleDailyWorkout scheduleDailyWorkout){
        List<RecyclerViewItem> recyclerViewRecyclerViewItems = new ArrayList<RecyclerViewItem>();

        // Init Recycler view items list
        List<ScheduleStep> scheduleStepList = scheduleDailyWorkout.getScheduleStepList();
        MuscleGroup muscleGroup = null;
        for(ScheduleStep scheduleStep : scheduleStepList){
            MuscleGroup muscleGroupTemp = scheduleStep.getScheduleSetList().get(0).getScheduleSetItemList().get(0).getExercise().getMuscleGroup();
            if(muscleGroup == null || muscleGroupTemp.getKey() != muscleGroup.getKey()){
                muscleGroup = muscleGroupTemp;
                recyclerViewRecyclerViewItems.add(new RecyclerViewItem<String>(RecyclerViewItem.ItemType.HEADER, muscleGroup.toString()));
            }
            recyclerViewRecyclerViewItems.add(new RecyclerViewItem<ScheduleStep>(RecyclerViewItem.ItemType.STANDARD_SET, scheduleStep));
        }

        return recyclerViewRecyclerViewItems;
    }

}
