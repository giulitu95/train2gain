package com.train2gain.train2gain.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.AthleteRepository;
import com.train2gain.train2gain.repository.TrainerRepository;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class SyncDataTask extends AsyncTask<Long, Void, Boolean> {

    /**
     * Sync data callbacks called base on synchronization result
     */
    public interface CallbackInterface {
        void onSyncDataFailed();
        void onSyncDataSuccessful();
    }

    // Repo
    private final UserRepository userRepository;
    private final AthleteRepository athleteRepository;
    private final TrainerRepository trainerRepository;

    private final CallbackInterface callbackImpl;

    public SyncDataTask(@NonNull Context context, CallbackInterface interfaceImpl) {
        this.userRepository = UserRepository.getInstance(context);
        this.athleteRepository = AthleteRepository.getInstance(context);
        this.trainerRepository = TrainerRepository.getInstance(context);
        this.callbackImpl = interfaceImpl;
    }

    @Override
    protected Boolean doInBackground(Long... ids) {
        // Sync user data
        LiveData<Resource<User>> userLiveDataResource = this.userRepository.updateUser(ids[0]);
        if(!genericDataSync(userLiveDataResource)){
            return false;
        }

        // Update base on userType
        boolean update = false;
        User userObject = userLiveDataResource.getValue().getData();
        switch(userObject.getType()){
            case ATHLETE:
                update = updateAthleteInfo(userObject);
                break;
            case TRAINER:
                break;
            case BOTH:
                break;
        }

        return update;
    }

    @Override
    protected void onPostExecute(Boolean updatedInserted) {
        if(updatedInserted){
            callbackImpl.onSyncDataSuccessful();
        }else{
            callbackImpl.onSyncDataFailed();
        }
    }

    /**
     * Synchronizes the athlete info
     * @param userObject
     * @return
     */
    private boolean updateAthleteInfo(User userObject){
        // Sync athlete info
        LiveData<Resource<Athlete>> athleteLiveDataResource = this.athleteRepository.updateAthlete(userObject.getId());
        if(!genericDataSync(athleteLiveDataResource)){
            return false;
        }

        // Sync trainer user info
        long trainerUserId = athleteLiveDataResource.getValue().getData().getTrainerUserId();
        LiveData<Resource<User>> trainerUserLiveDataResource = this.userRepository.updateUser(trainerUserId);
        if(!genericDataSync(trainerUserLiveDataResource)){
            return false;
        }

        // Sync trainer info
        LiveData<Resource<Trainer>> trainerLiveDataResource = this.trainerRepository.updateTrainer(trainerUserId);
        if(!genericDataSync(trainerLiveDataResource)){
            return false;
        }

        // TODO Sync current schedule
        return true;
    }

    /**
     * Generic data sync handler
     * @param resourceLiveData
     * @param <DataType>
     * @return
     */
    private<DataType> boolean genericDataSync(LiveData<Resource<DataType>> resourceLiveData){
        resourceLiveData.observeForever(dataTypeResource -> {});
        while(resourceLiveData.getValue() == null || resourceLiveData.getValue().getStatus() == Resource.Status.LOADING);
        if(resourceLiveData.getValue().getData() == null){
            return false;
        }else{
            return true;
        }
    }

}
