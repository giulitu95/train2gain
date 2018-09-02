package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.repository.common.SaveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.ScheduleHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

public class ScheduleRepository {

    private final ScheduleHelper scheduleHelperInstance;
    private final ScheduleAPI scheduleAPIInstance;
    private static ScheduleRepository instance = null;

    private ScheduleRepository(@NonNull Context context){
        this.scheduleHelperInstance = LocalDatabase.getInstance(context).getScheduleHelper();
        this.scheduleAPIInstance = APIClient.getImplementation(ScheduleAPI.class);
    }

    public static ScheduleRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (ScheduleRepository.class){
                if(instance == null){
                    instance = new ScheduleRepository(context);
                }
            }
        }
        return  instance;
    }

    public LiveData<Resource<Schedule>> getUpdatedScheduleByAthleteUserId(final long athleteUserId){
        return new RetrieveHandler<Schedule, Schedule>() {
            @NonNull @Override
            protected LiveData<Schedule> loadFromDatabase() {
                MutableLiveData<Schedule> scheduleLiveData = new MutableLiveData<Schedule>();
                AsyncTask.execute(() -> {
                    long scheduleId = scheduleHelperInstance.getCurrentScheduleIdByAthleteUserId(athleteUserId);
                    scheduleLiveData.postValue(scheduleHelperInstance.getById(scheduleId));
                });
                return scheduleLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<Schedule> responseData) {
                return scheduleHelperInstance.insert(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<Schedule>> loadFromAPI() {
                return APIUtils.callToLiveData(scheduleAPIInstance.getCurrentSchedule(athleteUserId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public void uploadSchedule(final Schedule schedule){
        new SaveHandler<Schedule, Schedule>(schedule) {
            @Override
            protected boolean shouldUploadToAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean shouldSaveAPIResponse() {
                return true;
            }

            @Override
            protected boolean saveDataObjectToDatabase(@NonNull Schedule objectToSave) {
                return scheduleHelperInstance.insert(objectToSave);
            }

            @Override
            protected boolean saveResponseDataToDatabase(@NonNull APIData<Schedule> responseData) {
                return true;
            }

            @Override
            protected LiveData<APIResponse<Schedule>> uploadToAPI(@NonNull Schedule dataToUpload) {
                return APIUtils.callToLiveData(scheduleAPIInstance.uploadNewSchedule(dataToUpload));
            }

            @Override
            protected void onAPIUploadFailed() {
                // TODO what to do here ? Will we show a message ?
            }

            @Override
            protected void onDatabaseSaveFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        };
    }

    public LiveData<Resource<Schedule>> updateScheduleByAthleteUserId(final long athleteUserId){
        return getUpdatedScheduleByAthleteUserId(athleteUserId);
    }

}
