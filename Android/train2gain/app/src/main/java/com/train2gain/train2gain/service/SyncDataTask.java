package com.train2gain.train2gain.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.AthleteRepository;
import com.train2gain.train2gain.repository.ScheduleLoadRepository;
import com.train2gain.train2gain.repository.ScheduleNoteRepository;
import com.train2gain.train2gain.repository.ScheduleRepository;
import com.train2gain.train2gain.repository.TrainerRepository;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.repository.common.Resource;

import java.util.Date;
import java.util.List;

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
    private final ScheduleRepository scheduleRepository;
    private final ScheduleLoadRepository scheduleLoadRepository;
    private final ScheduleNoteRepository scheduleNoteRepository;

    private final CallbackInterface callbackImpl;

    public SyncDataTask(@NonNull Context context, CallbackInterface interfaceImpl) {
        this.userRepository = UserRepository.getInstance(context);
        this.athleteRepository = AthleteRepository.getInstance(context);
        this.trainerRepository = TrainerRepository.getInstance(context);
        this.scheduleRepository = ScheduleRepository.getInstance(context);
        this.scheduleLoadRepository = ScheduleLoadRepository.getInstance(context);
        this.scheduleNoteRepository = ScheduleNoteRepository.getInstance(context);
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
                update = updateInfoForAthlete(userObject);
                break;
            case TRAINER:
                update = updateInfoForTrainer(userObject);
                break;
            case BOTH:
                boolean update1 = updateInfoForAthlete(userObject);
                boolean update2 = updateInfoForTrainer(userObject);
                update = update1 && update2;
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
     * Called to synchronize, with the web server, all the athlete's information like athlete details,
     * schedules, training history, ecc..
     * @param athleteUserObject the athlete's user for which we want to synchronize the information
     * @return true, if the synchronization has been successful
     *         false otherwise
     */
    private boolean updateInfoForAthlete(User athleteUserObject){
        // Sync athlete info
        LiveData<Resource<Athlete>> athleteLiveDataResource = this.athleteRepository.updateAthlete(athleteUserObject.getId());
        if(!genericDataSync(athleteLiveDataResource)){
            return false;
        }

        long trainerUserId = athleteLiveDataResource.getValue().getData().getTrainerUserId();
        long athleteId = athleteLiveDataResource.getValue().getData().getUserId();

        // Sync trainer user info
        LiveData<Resource<User>> trainerUserLiveDataResource = this.userRepository.updateUser(trainerUserId);
        if(!genericDataSync(trainerUserLiveDataResource)){
            return false;
        }

        // Sync trainer info
        LiveData<Resource<Trainer>> trainerLiveDataResource = this.trainerRepository.updateTrainer(trainerUserId);
        if(!genericDataSync(trainerLiveDataResource)){
            return false;
        }

        // Sync schedule info
        LiveData<Resource<Schedule>> scheduleLiveDataResource = this.scheduleRepository.updateScheduleByAthleteUserId(athleteId);
        if(!genericDataSync(scheduleLiveDataResource)){
            // No schedule, ok, continue..
            return true;
        }

        long scheduleId = scheduleLiveDataResource.getValue().getData().getId();

        // Sync schedule loads
        LiveData<Resource<List<ScheduleLoad>>> scheduleLoadsListLiveDataResource = this.scheduleLoadRepository.updatedScheduleLoads(scheduleId, athleteId, new Date(0));
        if(!genericDataSync(scheduleLoadsListLiveDataResource)){
            return false;
        }

        // Sync schedule notes
        LiveData<Resource<List<ScheduleNote>>> scheduleNotesListLiveDataResource = this.scheduleNoteRepository.updateScheduleNotes(scheduleId, athleteId, new Date(0));
        if(!genericDataSync(scheduleNotesListLiveDataResource)){
            return false;
        }

        // TODO Sync training history & Sync all the uploads
        return true;
    }

    /**
     * Called to synchronize, with the web server, all the trainer's information like trainer details,
     * schedules that he/she created, notes added to the schedules, ecc..
     * @param trainerUserObject the trainer's user for which we want to synchronize the information
     * @return true, if the synchronization has been successful
     *         false otherwise
     */
    private boolean updateInfoForTrainer(User trainerUserObject){
        long trainerUserId = trainerUserObject.getId();

        // Sync trainer info
        LiveData<Resource<Trainer>> trainerLiveDataResource = this.trainerRepository.updateTrainer(trainerUserId);
        if(!genericDataSync(trainerLiveDataResource)){
            return false;
        }

        // TODO Sync all the uploads
        return true;
    }

    /**
     * A generic data sync handler. It is use to observe the data and take decision base on data's state
     * @param resourceLiveData the data that we want to sync with the web server
     * @param <DataType> type of data we want to sync (it can be an Entity or a List)
     * @return true, if sync has been handled correctly
     *         false otherwise
     */
    private<DataType> boolean genericDataSync(LiveData<Resource<DataType>> resourceLiveData){
        resourceLiveData.observeForever(dataTypeResource -> {});
        while(resourceLiveData.getValue() == null || resourceLiveData.getValue().getStatus() == Resource.Status.LOADING);
        if(resourceLiveData.getValue().getData() != null){
            return true;
        }else{
            return false;
        }
    }

}
