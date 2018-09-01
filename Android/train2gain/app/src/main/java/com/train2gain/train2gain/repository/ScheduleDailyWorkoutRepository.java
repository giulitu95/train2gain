package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.ScheduleDailyWorkoutHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleHelper;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;

public class ScheduleDailyWorkoutRepository {

    private final ScheduleDailyWorkoutHelper scheduleDailyWorkoutHelperInstance;
    private final ScheduleHelper scheduleHelperInstance;
    private static ScheduleDailyWorkoutRepository instance = null;

    private ScheduleDailyWorkoutRepository(@NonNull Context context){
        this.scheduleDailyWorkoutHelperInstance = LocalDatabase.getInstance(context).getScheduleDailyWorkoutHelper();
        this.scheduleHelperInstance = LocalDatabase.getInstance(context).getScheduleHelper();
    }

    public static ScheduleDailyWorkoutRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (ScheduleDailyWorkoutRepository.class){
                if(instance == null){
                    instance = new ScheduleDailyWorkoutRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<ScheduleDailyWorkout>> getScheduleDailyWorkoutOfTheDayMinimalByAthleteUserId(final long athleteUserId){
        return new RetrieveHandler<ScheduleDailyWorkout, ScheduleDailyWorkout>() {
            @NonNull @Override
            protected LiveData<ScheduleDailyWorkout> loadFromDatabase() {
                MutableLiveData<ScheduleDailyWorkout> scheduleDailyWorkoutOfTheDay = new MutableLiveData<ScheduleDailyWorkout>();
                AsyncTask.execute(() -> {
                    long scheduleId = scheduleHelperInstance.getCurrentScheduleIdByAthleteUserId(athleteUserId);
                    scheduleDailyWorkoutOfTheDay.postValue(scheduleDailyWorkoutHelperInstance.getScheduleDailyWorkoutOfTheDayByScheduleIdMinimal(scheduleId));
                });
                return scheduleDailyWorkoutOfTheDay;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return false;
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<ScheduleDailyWorkout> responseData) {
                return true;
            }

            @NonNull @Override
            protected LiveData<APIResponse<ScheduleDailyWorkout>> loadFromAPI() {
                return new MutableLiveData<APIResponse<ScheduleDailyWorkout>>();
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // Nothing to do
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<ScheduleDailyWorkout>> getScheduleDailyWorkoutOfTheDayByAthleteUserId(final long athleteUserId){
        return new RetrieveHandler<ScheduleDailyWorkout, ScheduleDailyWorkout>() {
            @NonNull @Override
            protected LiveData<ScheduleDailyWorkout> loadFromDatabase() {
                MutableLiveData<ScheduleDailyWorkout> scheduleDailyWorkoutOfTheDay = new MutableLiveData<ScheduleDailyWorkout>();
                AsyncTask.execute(() -> {
                    long scheduleId = scheduleHelperInstance.getCurrentScheduleIdByAthleteUserId(athleteUserId);
                    scheduleDailyWorkoutOfTheDay.postValue(scheduleDailyWorkoutHelperInstance.getScheduleDailyWorkoutOfTheDayByScheduleId(scheduleId));
                });
                return scheduleDailyWorkoutOfTheDay;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return false;
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<ScheduleDailyWorkout> responseData) {
                return true;
            }

            @NonNull @Override
            protected LiveData<APIResponse<ScheduleDailyWorkout>> loadFromAPI() {
                return new MutableLiveData<APIResponse<ScheduleDailyWorkout>>();
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // Nothing to do
            }
        }.getAsLiveData();
    }

}
