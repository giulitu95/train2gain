package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Schedule {

    @NonNull
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @Expose @SerializedName("startDate")
    private Date startDate;

    @NonNull
    @Expose @SerializedName("athleteId")
    private long athleteUserId = 0;

    @Nullable
    @Expose @SerializedName("trainerId")
    private long trainerUserId = -1;

    @Nullable
    @Expose @SerializedName("dailyWorkouts")
    private List<ScheduleDailyWorkout> scheduleDailyWorkoutList = null;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public Date getStartDate() {
        return startDate;
    }

    @NonNull public long getAthleteUserId() {
        return athleteUserId;
    }

    @Nullable public long getTrainerUserId() {
        return trainerUserId;
    }

    @Nullable public List<ScheduleDailyWorkout> getScheduleDailyWorkoutList() {
        return scheduleDailyWorkoutList;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    public void setAthleteUserId(@NonNull long athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public void setTrainerUserId(@Nullable long trainerUserId) {
        this.trainerUserId = trainerUserId;
    }

    public void setScheduleDailyWorkoutList(@Nullable List<ScheduleDailyWorkout> scheduleDailyWorkoutList) {
        this.scheduleDailyWorkoutList = scheduleDailyWorkoutList;
    }

}
