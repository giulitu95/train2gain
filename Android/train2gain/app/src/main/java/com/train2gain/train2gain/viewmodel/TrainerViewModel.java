package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.repository.TrainerRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class TrainerViewModel extends ViewModel {

    private LiveData<Resource<Trainer>> trainerLiveData = null;
    private final TrainerRepository trainerRepository;

    public TrainerViewModel(){
        this.trainerRepository = TrainerRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<Trainer>> getTrainer(final long trainerUserId) {
        if(this.trainerLiveData == null){
            this.trainerLiveData = this.trainerRepository.getTrainer(trainerUserId);
        }
        return this.trainerLiveData;
    }

}
