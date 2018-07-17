package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ScheduleLoad {

    @NonNull
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @Expose @SerializedName("weight")
    private int load;

    @NonNull
    @Expose @SerializedName("date")
    private Date insertDate;

    @NonNull
    @Expose @SerializedName("schedule_item_id")
    private long remoteScheduleSetItemId = 0;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public int getLoad() {
        return load;
    }

    @NonNull public Date getInsertDate() {
        return insertDate;
    }

    @NonNull public long getRemoteScheduleSetItemId() {
        return remoteScheduleSetItemId;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setLoad(@NonNull int load) {
        this.load = load;
    }

    public void setInsertDate(@NonNull Date insertDate) {
        this.insertDate = insertDate;
    }

    public void setRemoteScheduleSetItemId(@NonNull long remoteScheduleSetItemId) {
        this.remoteScheduleSetItemId = remoteScheduleSetItemId;
    }

}
