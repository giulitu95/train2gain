package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleDailyWorkout {

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
    @Expose @SerializedName("localScheduleId")
    private long scheduleId = 0;

    @Nullable
    @Expose @SerializedName("scheduleId")
    private long remoteScheduleId = -1;

    @NonNull
    @Expose @SerializedName("scheduleSteps")
    private List<ScheduleStep> scheduleStepList;


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

    @NonNull public long getScheduleId() {
        return scheduleId;
    }

    @Nullable public long getRemoteScheduleId() {
        return remoteScheduleId;
    }

    @NonNull public List<ScheduleStep> getScheduleStepList() {
        return scheduleStepList;
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

    public void setScheduleId(@NonNull long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setRemoteScheduleId(@Nullable long remoteScheduleId) {
        this.remoteScheduleId = remoteScheduleId;
    }

    public void setScheduleStepList(@NonNull List<ScheduleStep> scheduleStepList) {
        this.scheduleStepList = scheduleStepList;
    }

}
