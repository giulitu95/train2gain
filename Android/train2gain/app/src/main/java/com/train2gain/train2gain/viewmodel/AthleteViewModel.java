package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.repository.AthleteRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class AthleteViewModel extends ViewModel {

    private LiveData<Resource<Athlete>> athleteLiveData = null;
    private final AthleteRepository athleteRepository;

    public AthleteViewModel(){
        this.athleteRepository = AthleteRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<Athlete>> getAthlete(final long athleteUserId) {
        if(this.athleteLiveData == null){
            this.athleteLiveData = this.athleteRepository.getAthlete(athleteUserId);
        }
        return this.athleteLiveData;
    }

}
