package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.train2gain.train2gain.model.enums.ScheduleStepType;

import java.util.List;

public class ScheduleStep {

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
    @Expose @SerializedName("scheduleStepTypeId")
    private ScheduleStepType type;

    @NonNull
    @Expose @SerializedName("restTime")
    private long restTime;

    @NonNull
    @Expose @SerializedName("localDailyWorkoutId")
    private long scheduleDailyWorkoutId = 0;

    @Nullable
    @Expose @SerializedName("dailyWorkoutId")
    private long remoteScheduleDailyWorkoutId = -1;

    @NonNull
    @Expose @SerializedName("scheduleSets")
    private List<ScheduleSet> scheduleSetList;


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

    @NonNull public ScheduleStepType getType() {
        return type;
    }

    @NonNull public long getRestTime() {
        return restTime;
    }

    @NonNull public long getScheduleDailyWorkoutId() {
        return scheduleDailyWorkoutId;
    }

    @Nullable public long getRemoteScheduleDailyWorkoutId() {
        return remoteScheduleDailyWorkoutId;
    }

    @NonNull public List<ScheduleSet> getScheduleSetList() {
        return scheduleSetList;
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

    public void setType(@NonNull ScheduleStepType type) {
        this.type = type;
    }

    public void setRestTime(@NonNull long restTime) {
        this.restTime = restTime;
    }

    public void setScheduleDailyWorkoutId(@NonNull long scheduleDailyWorkoutId) {
        this.scheduleDailyWorkoutId = scheduleDailyWorkoutId;
    }

    public void setRemoteScheduleDailyWorkoutId(@Nullable long remoteScheduleDailyWorkoutId) {
        this.remoteScheduleDailyWorkoutId = remoteScheduleDailyWorkoutId;
    }

    public void setScheduleSetList(@NonNull List<ScheduleSet> scheduleSetList) {
        this.scheduleSetList = scheduleSetList;
    }

}
