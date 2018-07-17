package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trainer {

    @NonNull
    @Expose @SerializedName("id")
    private long userId = 0;

    @NonNull
    @Expose @SerializedName("gymId")
    private long gymId = 0;

    @NonNull
    @Expose @SerializedName("gym")
    private Gym gym;


    // GETTERS

    @NonNull public long getUserId() {
        return userId;
    }

    @NonNull public long getGymId() {
        return gymId;
    }

    @NonNull public Gym getGym() {
        return gym;
    }


    // SETTERS

    public void setUserId(@NonNull long userId) {
        this.userId = userId;
    }

    public void setGymId(@NonNull long gymId) {
        this.gymId = gymId;
    }

    public void setGym(@NonNull Gym gym) {
        this.gym = gym;
    }

}
