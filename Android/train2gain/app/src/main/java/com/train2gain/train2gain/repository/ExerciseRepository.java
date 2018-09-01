package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.ExerciseHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.ExerciseAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

import java.util.List;

public class ExerciseRepository {

    private final ExerciseHelper exerciseHelperInstance;
    private final ExerciseAPI exerciseAPIInstance;
    private static ExerciseRepository instance = null;

    private ExerciseRepository(@NonNull Context context){
        this.exerciseHelperInstance = LocalDatabase.getInstance(context).getExerciseHelper();
        this.exerciseAPIInstance = APIClient.getImplementation(ExerciseAPI.class);
    }

    public static ExerciseRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (ExerciseRepository.class){
                if(instance == null){
                    instance = new ExerciseRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<List<Exercise>>> getExercises( boolean getUpdated){
        return new RetrieveHandler<List<Exercise>, List<Exercise>>() {
            @NonNull @Override
            protected LiveData<List<Exercise>> loadFromDatabase() {
                MutableLiveData<List<Exercise>> exercisesLiveData = new MutableLiveData<List<Exercise>>();
                AsyncTask.execute(() -> {
                    exercisesLiveData.postValue(exerciseHelperInstance.getAll());
                });
                return exercisesLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                if(getUpdated) {
                    return NetworkUtil.isConnected();
                } else {
                    return false;
                }
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<List<Exercise>> responseData) {
                return exerciseHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<List<Exercise>>> loadFromAPI() {
                return APIUtils.callToLiveData(exerciseAPIInstance.getExercises(0));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }


}
