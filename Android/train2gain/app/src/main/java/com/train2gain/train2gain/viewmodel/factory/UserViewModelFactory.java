package com.train2gain.train2gain.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.viewmodel.UserViewModel;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private final long userId;
    private final Context context;

    public UserViewModelFactory(@NonNull Context context, final long userId){
        this.userId = userId;
        this.context = context;
    }

    @NonNull @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(this.context, this.userId);
        }
        throw new IllegalArgumentException("UserViewModelFactory: unknown ViewModel class");
    }

}
