package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.ScheduleNoteHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleNoteAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

import java.util.Date;
import java.util.List;

public class ScheduleNoteRepository {

    private final ScheduleNoteHelper scheduleNoteHelperInstance;
    private final ScheduleNoteAPI scheduleNoteAPIInstance;
    private static ScheduleNoteRepository instance = null;

    private ScheduleNoteRepository(@NonNull Context context){
        this.scheduleNoteHelperInstance = LocalDatabase.getInstance(context).getScheduleNoteHelper();
        this.scheduleNoteAPIInstance = APIClient.getImplementation(ScheduleNoteAPI.class);
    }

    public static ScheduleNoteRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (ScheduleNoteRepository.class){
                if(instance == null){
                    instance = new ScheduleNoteRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<List<ScheduleNote>>> getUpdatedScheduleNotesList(final long scheduleId, final long athleteUserId, @NonNull final Date lastUpdate){
        return new RetrieveHandler<List<ScheduleNote>, List<ScheduleNote>>() {
            @NonNull @Override
            protected LiveData<List<ScheduleNote>> loadFromDatabase() {
                MutableLiveData<List<ScheduleNote>> scheduleNotesListLiveData = new MutableLiveData<List<ScheduleNote>>();
                AsyncTask.execute(() -> {
                    scheduleNotesListLiveData.postValue(scheduleNoteHelperInstance.getScheduleNoteListByScheduleId(scheduleId));
                });
                return scheduleNotesListLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<List<ScheduleNote>> responseData) {
                return scheduleNoteHelperInstance.insert(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<List<ScheduleNote>>> loadFromAPI() {
                return APIUtils.callToLiveData(scheduleNoteAPIInstance.getScheduleNotes(athleteUserId, scheduleId, lastUpdate.getTime()));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<ScheduleNote>>> updateScheduleNotes(final long scheduleId, final long athleteUserId, @NonNull final Date lastUpdate){
        return getUpdatedScheduleNotesList(scheduleId, athleteUserId, lastUpdate);
    }

}
