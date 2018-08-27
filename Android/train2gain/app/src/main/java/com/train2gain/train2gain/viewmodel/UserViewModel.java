package com.train2gain.train2gain.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.UserRepository;
import com.train2gain.train2gain.repository.common.Resource;

public class UserViewModel extends ViewModel {

    private LiveData<Resource<User>> userLiveData = null;
    private UserRepository userRepository = null;

    public void init(@NonNull Context context, final long userId){
        this.userRepository = UserRepository.getInstance(context);
        this.userLiveData = this.userRepository.getUser(userId);
    }

    public LiveData<Resource<User>> getUser(){
        return this.userLiveData;
    }

}
