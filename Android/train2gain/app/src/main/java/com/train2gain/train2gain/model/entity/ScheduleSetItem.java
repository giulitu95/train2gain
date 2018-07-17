package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleSetItem {

    @NonNull
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @Expose @SerializedName("orderNumber")
    private int order;

    @NonNull
    @Expose @SerializedName("reps")
    private int reps;

    @NonNull
    @Expose @SerializedName("localScheduleSetId")
    private long scheduleSetId = 0;

    @Nullable
    @Expose @SerializedName("scheduleSetId")
    private long remoteScheduleSetId = -1;

    @NonNull
    @Expose @SerializedName("exerciseId")
    private long exerciseId = 0;

    @NonNull
    @Expose @SerializedName("exercise")
    private Exercise exercise;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public int getOrder() {
        return order;
    }

    @NonNull public int getReps() {
        return reps;
    }

    @NonNull public long getScheduleSetId() {
        return scheduleSetId;
    }

    @Nullable public long getRemoteScheduleSetId() {
        return remoteScheduleSetId;
    }

    @NonNull public long getExerciseId() {
        return exerciseId;
    }

    @NonNull public Exercise getExercise() {
        return exercise;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setOrder(@NonNull int order) {
        this.order = order;
    }

    public void setReps(@NonNull int reps) {
        this.reps = reps;
    }

    public void setScheduleSetId(@NonNull long scheduleSetId) {
        this.scheduleSetId = scheduleSetId;
    }

    public void setRemoteScheduleSetId(@Nullable long remoteScheduleSetId) {
        this.remoteScheduleSetId = remoteScheduleSetId;
    }

    public void setExerciseId(@NonNull long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExercise(@NonNull Exercise exercise) {
        this.exercise = exercise;
    }

}
