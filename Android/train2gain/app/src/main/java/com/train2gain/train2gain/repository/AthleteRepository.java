package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.repository.common.SaveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.AthleteHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.AthleteAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

public class AthleteRepository {

    private final AthleteHelper athleteHelperInstance;
    private final AthleteAPI athleteAPIInstance;
    private static AthleteRepository instance = null;

    private AthleteRepository(@NonNull Context context){
        this.athleteHelperInstance = LocalDatabase.getInstance(context).getAthleteHelper();
        this.athleteAPIInstance = APIClient.getImplementation(AthleteAPI.class);
    }

    public static AthleteRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (AthleteRepository.class){
                if(instance == null){
                    instance = new AthleteRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<Athlete>> getAthlete(final long athleteId){
        return new RetrieveHandler<Athlete, Athlete>() {
            @NonNull @Override
            protected LiveData<Athlete> loadFromDatabase() {
                MutableLiveData<Athlete> athleteLiveData = new MutableLiveData<Athlete>();
                AsyncTask.execute(() -> {
                    athleteLiveData.postValue(athleteHelperInstance.getById(athleteId));
                });
                return athleteLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<Athlete> responseData) {
                return athleteHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<Athlete>> loadFromAPI() {
                return APIUtils.callToLiveData(athleteAPIInstance.getAthlete(athleteId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public void uploadAthlete(final Athlete athlete){
        new SaveHandler<Athlete, Athlete>(athlete) {
            @Override
            protected boolean shouldUploadToAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean shouldSaveAPIResponse() {
                return false;
            }

            @Override
            protected boolean saveDataObjectToDatabase(@NonNull Athlete objectToSave) {
                return athleteHelperInstance.insertOrUpdateIfExists(objectToSave);
            }

            @Override
            protected boolean saveResponseDataToDatabase(@NonNull APIData<Athlete> responseData) {
                return true;
            }

            @Override
            protected LiveData<APIResponse<Athlete>> uploadToAPI(@NonNull Athlete dataToUpload) {
                return APIUtils.callToLiveData(athleteAPIInstance.uploadAthlete(dataToUpload));
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

    public LiveData<Resource<Athlete>> getUpdatedAthlete(final long athleteUserId){
        return new RetrieveHandler<Athlete, Athlete>() {
            @NonNull @Override
            protected LiveData<Athlete> loadFromDatabase() {
                MutableLiveData<Athlete> athleteLiveData = new MutableLiveData<Athlete>();
                AsyncTask.execute(() -> {
                    athleteLiveData.postValue(athleteHelperInstance.getById(athleteUserId));
                });
                return athleteLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<Athlete> responseData) {
                return athleteHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<Athlete>> loadFromAPI() {
                return APIUtils.callToLiveData(athleteAPIInstance.getAthlete(athleteUserId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Athlete>> updateAthlete(final long athleteUserId){
        return getUpdatedAthlete(athleteUserId);
    }

}
