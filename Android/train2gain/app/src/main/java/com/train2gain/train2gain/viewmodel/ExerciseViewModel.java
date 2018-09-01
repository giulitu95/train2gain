package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.ExerciseRepository;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.repository.common.Resource;

import java.util.List;

public class ExerciseViewModel extends ViewModel{

    private LiveData<Resource<List<Exercise>>> exercisesListLiveData = null;
    private final ExerciseRepository exerciseRepository;

    public ExerciseViewModel(){
        this.exerciseRepository = ExerciseRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<List<Exercise>>> getExercises(boolean getUpdated) {
        if(this.exercisesListLiveData == null){
            this.exercisesListLiveData= this.exerciseRepository.getExercises(getUpdated);
        }
        return this.exercisesListLiveData;
    }
}
