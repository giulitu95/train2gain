package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TrainingHistory {

    @NonNull
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @Expose @SerializedName("date")
    private Date trainingDate;

    @NonNull
    @Expose @SerializedName("athleteId")
    private long athleteUserId = 0;

    @Nullable
    @Expose @SerializedName("localDailyWorkoutId")
    private long dailyWorkoutId = -1;

    @Nullable
    @Expose @SerializedName("dailyWorkoutId")
    private long remoteDailyWorkoutId = -1;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public Date getTrainingDate() {
        return trainingDate;
    }

    @NonNull public long getAthleteUserId() {
        return athleteUserId;
    }

    @Nullable public long getDailyWorkoutId() {
        return dailyWorkoutId;
    }

    @Nullable public long getRemoteDailyWorkoutId() {
        return remoteDailyWorkoutId;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setTrainingDate(@NonNull Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public void setAthleteUserId(@NonNull long athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public void setDailyWorkoutId(@Nullable long dailyWorkoutId) {
        this.dailyWorkoutId = dailyWorkoutId;
    }

    public void setRemoteDailyWorkoutId(@Nullable long remoteDailyWorkoutId) {
        this.remoteDailyWorkoutId = remoteDailyWorkoutId;
    }

}
