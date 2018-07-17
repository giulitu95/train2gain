package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(
    tableName = ScheduleNote.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = ScheduleStep.class, childColumns = ScheduleNote.COLUMN_SCHEDULE_STEP_ID,
                parentColumns = ScheduleStep.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class, childColumns = ScheduleNote.COLUMN_AUTHOR_USER_ID, parentColumns = User.COLUMN_ID,
                onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleNote.COLUMN_SCHEDULE_STEP_ID }),
        @Index(value = { ScheduleNote.COLUMN_AUTHOR_USER_ID }),
        @Index(value = { ScheduleNote.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class ScheduleNote {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_note";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_SCHEDULE_STEP_ID = "schedule_step_id";
    public static final String COLUMN_REMOTE_SCHEDULE_STEP_ID = "remote_schedule_step_id";
    public static final String COLUMN_AUTHOR_USER_ID = "author_user_id";
    public static final String COLUMN_SYNCED_WITH_SERVER = "synced_with_server";

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    @Expose @SerializedName("localId")
    private long id;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_ID)
    @Expose @SerializedName("id")
    private long remoteId = -1;

    @NonNull
    @ColumnInfo(name = COLUMN_CONTENT)
    @Expose @SerializedName("text")
    private String content;

    @NonNull
    @ColumnInfo(name = COLUMN_CREATION_DATE)
    @Expose @SerializedName("date")
    private Date creationDate;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_STEP_ID)
    @Expose @SerializedName("localScheduleStepId")
    private long scheduleStepId = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_STEP_ID)
    @Expose @SerializedName("scheduleStepId")
    private long remoteScheduleStepId = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_AUTHOR_USER_ID)
    @Expose @SerializedName("authorId")
    private long authorUserId = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_SYNCED_WITH_SERVER)
    private boolean syncedWithServer = false;


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

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
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

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

    public void setAuthorUserId(@NonNull long authorUserId) {
        this.authorUserId = authorUserId;
    }

}
