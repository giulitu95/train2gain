package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ScheduleNote {

    @NonNull
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @Expose @SerializedName("text")
    private String content;

    @NonNull
    @Expose @SerializedName("date")
    private Date creationDate;

    @NonNull
    @Expose @SerializedName("localScheduleStepId")
    private long scheduleStepId = 0;

    @NonNull
    @Expose @SerializedName("scheduleStepId")
    private long remoteScheduleStepId = 0;

    @NonNull
    @Expose @SerializedName("authorId")
    private long authorUserId = 0;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public String getContent() {
        return content;
    }

    @NonNull public Date getCreationDate() {
        return creationDate;
    }

    @NonNull public long getScheduleStepId() {
        return scheduleStepId;
    }

    @NonNull public long getRemoteScheduleStepId() {
        return remoteScheduleStepId;
    }

    @NonNull public long getAuthorUserId() {
        return authorUserId;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public void setCreationDate(@NonNull Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setScheduleStepId(@NonNull long scheduleStepId) {
        this.scheduleStepId = scheduleStepId;
    }

    public void setRemoteScheduleStepId(@NonNull long remoteScheduleStepId) {
        this.remoteScheduleStepId = remoteScheduleStepId;
    }

    public void setAuthorUserId(@NonNull long authorUserId) {
        this.authorUserId = authorUserId;
    }

}
