package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleSet {

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
    @Expose @SerializedName("localScheduleStepId")
    private long scheduleStepId = 0;

    @Nullable
    @Expose @SerializedName("scheduleStepId")
    private long remoteScheduleStepId = -1;

    @NonNull
    @Expose @SerializedName("scheduleItems")
    private List<ScheduleSetItem> scheduleSetItemList;


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

    @NonNull public long getScheduleStepId() {
        return scheduleStepId;
    }

    @Nullable public long getRemoteScheduleStepId() {
        return remoteScheduleStepId;
    }

    @NonNull public List<ScheduleSetItem> getScheduleSetItemList() {
        return scheduleSetItemList;
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

    public void setScheduleStepId(@NonNull long scheduleStepId) {
        this.scheduleStepId = scheduleStepId;
    }

    public void setRemoteScheduleStepId(@Nullable long remoteScheduleStepId) {
        this.remoteScheduleStepId = remoteScheduleStepId;
    }

    public void setScheduleSetItemList(@NonNull List<ScheduleSetItem> scheduleSetItemList) {
        this.scheduleSetItemList = scheduleSetItemList;
    }

}
