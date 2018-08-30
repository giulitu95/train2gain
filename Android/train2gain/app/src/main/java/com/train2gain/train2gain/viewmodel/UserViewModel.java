package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.repository.common.Resource;

import java.util.List;

public class UserViewModel extends ViewModel {

    private LiveData<Resource<User>> userLiveData = null;
    private LiveData<Resource<List<User>>> userListLiveData = null;
    private final UserRepository userRepository;

    public UserViewModel(){
        this.userRepository = UserRepository.getInstance(App.getContext());
    }

    public LiveData<Resource<User>> getUser(final long userId){
        if(this.userLiveData == null){
            this.userLiveData = this.userRepository.getUser(userId);
        }
        return this.userLiveData;
    }

    public LiveData<Resource<List<User>>> getTrainerUserList(final long trainerId, final long lastUpdate) {
        if(this.userListLiveData == null){
            this.userListLiveData = this.userRepository.getTrainerUsers(trainerId, lastUpdate);
        }
        return this.userListLiveData;
    }
}
