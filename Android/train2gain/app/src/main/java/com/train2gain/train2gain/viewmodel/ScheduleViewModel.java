package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.repository.ScheduleRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class ScheduleViewModel extends ViewModel {

    private LiveData<Resource<Schedule>> scheduleResourceLiveData = null;
    private final ScheduleRepository scheduleRepository;

    public ScheduleViewModel(){
        this.scheduleRepository = ScheduleRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<Schedule>> getScheduleByAthleteUserId(final long athleteUserId){
        if(this.scheduleResourceLiveData == null){
            this.scheduleResourceLiveData = this.scheduleRepository.getUpdatedScheduleByAthleteUserId(athleteUserId);
        }
        return this.scheduleResourceLiveData;
    }

}
