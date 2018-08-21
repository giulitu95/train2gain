package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.repository.common.SaveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.TrainerHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.TrainerAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;

public class TrainerRepository {
    private final TrainerHelper trainerHelperInstance;
    private final TrainerAPI trainerAPIInstance;
    private static TrainerRepository instance = null;

    private TrainerRepository(@NonNull Context context){
        this.trainerHelperInstance = LocalDatabase.getInstance(context).getTrainerHelper();
        this.trainerAPIInstance = APIClient.getImplementation(TrainerAPI.class);
    }

    public static TrainerRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (TrainerRepository.class){
                if(instance == null){
                    instance = new TrainerRepository(context);
                }
            }
        }
        return instance;
    }
    public LiveData<Resource<Trainer>> getTrainer(final long trainerId){
        return new RetrieveHandler<Trainer, Trainer>() {
            @NonNull @Override
            protected LiveData<Trainer> loadFromDatabase() {
                MutableLiveData<Trainer> trainerLiveData = new MutableLiveData<Trainer>();
                AsyncTask.execute(() -> {
                    trainerLiveData.postValue(trainerHelperInstance.getById(trainerId));
                });
                return trainerLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return true;
            }

            @Override
            protected Boolean saveAPIResponse(@NonNull APIData<Trainer> responseData) {
                return trainerHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<Trainer>> loadFromAPI() {
                return APIUtils.callToLiveData(trainerAPIInstance.getTrainer(trainerId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO implement something..
            }
        }.getAsLiveData();
    }
    public void localSaveTrainer(final Trainer trainer){
        new SaveHandler<Trainer, Trainer>(trainer) {
            @Override
            protected boolean shouldUploadToAPI() {
                return false;
            }

            @Override
            protected boolean shouldSaveAPIResponse() {
                return false;
            }

            @Override
            protected boolean saveToDatabase(@NonNull Trainer dataToSave) {
                return trainerHelperInstance.insertOrUpdateIfExists(dataToSave);
            }

            @Override
            protected LiveData<APIResponse<Trainer>> uploadToAPI(@NonNull Trainer dataToUpload) {
                return null;
            }

            @Override
            protected void onAPIUploadFailed() {
                // TODO implement something..
            }

            @Override
            protected void onDatabaseSaveFailed() {
                // TODO implement something..
            }
        };
    }
    public LiveData<Resource<Trainer>> getUpdatedTrainer(final long trainerUserId){
        return new RetrieveHandler<Trainer, Trainer>() {
            @NonNull @Override
            protected LiveData<Trainer> loadFromDatabase() {
                MutableLiveData<Trainer> trainerLiveData = new MutableLiveData<Trainer>();
                AsyncTask.execute(() -> {
                    trainerLiveData.postValue(trainerHelperInstance.getById(trainerUserId));
                });
                return trainerLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                // TODO add network conditions..
                return true;
            }

            @Override
            protected Boolean saveAPIResponse(@NonNull APIData<Trainer> responseData) {
                return trainerHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<Trainer>> loadFromAPI() {
                return APIUtils.callToLiveData(trainerAPIInstance.getTrainer(trainerUserId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Trainer>> updateTrainer(final long trainerUserId){
        return getUpdatedTrainer(trainerUserId);
    }
}
