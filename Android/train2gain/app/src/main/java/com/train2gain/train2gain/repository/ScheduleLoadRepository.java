package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.ScheduleLoadHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleLoadAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

import java.util.Date;
import java.util.List;

public class ScheduleLoadRepository {

    private final ScheduleLoadHelper scheduleLoadHelperInstance;
    private final ScheduleLoadAPI scheduleLoadAPIInstance;
    private static ScheduleLoadRepository instance = null;

    private ScheduleLoadRepository(@NonNull Context context){
        this.scheduleLoadHelperInstance = LocalDatabase.getInstance(context).getScheduleLoadHelper();
        this.scheduleLoadAPIInstance = APIClient.getImplementation(ScheduleLoadAPI.class);
    }

    public static ScheduleLoadRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (ScheduleLoadRepository.class){
                if(instance == null){
                    instance = new ScheduleLoadRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<List<ScheduleLoad>>> getUpdatedScheduleLoadsList(final long scheduleId, final long athleteUserId, @NonNull final Date lastUpdate){
        return new RetrieveHandler<List<ScheduleLoad>, List<ScheduleLoad>>() {
            @NonNull @Override
            protected LiveData<List<ScheduleLoad>> loadFromDatabase() {
                MutableLiveData<List<ScheduleLoad>> scheduleLoadsListLiveData = new MutableLiveData<List<ScheduleLoad>>();
                AsyncTask.execute(() -> {
                    scheduleLoadsListLiveData.postValue(scheduleLoadHelperInstance.getScheduleLoadListByScheduleId(scheduleId));
                });
                return scheduleLoadsListLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<List<ScheduleLoad>> responseData) {
                return scheduleLoadHelperInstance.insert(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<List<ScheduleLoad>>> loadFromAPI() {
                return APIUtils.callToLiveData(scheduleLoadAPIInstance.getScheduleLoads(athleteUserId, scheduleId, lastUpdate.getTime()));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<ScheduleLoad>>> updatedScheduleLoads(final long scheduleId, final long athleteUserId, @NonNull final Date lastUpdate){
        return getUpdatedScheduleLoadsList(scheduleId, athleteUserId, lastUpdate);
    }

}
