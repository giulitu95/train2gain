package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.repository.ScheduleDailyWorkoutRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class ScheduleDailyWorkoutViewModel extends ViewModel {

    private final ScheduleDailyWorkoutRepository scheduleDailyWorkoutRepository;
    private LiveData<Resource<ScheduleDailyWorkout>> scheduleDailyWorkoutOfTheDayMinimal = null;
    private LiveData<Resource<ScheduleDailyWorkout>> scheduleDailyWorkoutOfTheDay = null;

    public ScheduleDailyWorkoutViewModel(){
        this.scheduleDailyWorkoutRepository = ScheduleDailyWorkoutRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<ScheduleDailyWorkout>> getScheduleDailyWorkoutOfTheDayMinimal(final long athleteUserId) {
        if(scheduleDailyWorkoutOfTheDayMinimal == null){
            this.scheduleDailyWorkoutOfTheDayMinimal = this.scheduleDailyWorkoutRepository.getScheduleDailyWorkoutOfTheDayMinimalByAthleteUserId(athleteUserId);
        }
        return scheduleDailyWorkoutOfTheDayMinimal;
    }

    public LiveData<Resource<ScheduleDailyWorkout>> getScheduleDailyWorkoutOfTheDay(final long athleteUserId) {
        if(scheduleDailyWorkoutOfTheDay == null){
            this.scheduleDailyWorkoutOfTheDay = this.scheduleDailyWorkoutRepository.getScheduleDailyWorkoutOfTheDayByAthleteUserId(athleteUserId);
        }
        return scheduleDailyWorkoutOfTheDay;
    }

}
