package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(
    tableName = ScheduleSet.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = ScheduleStep.class, childColumns = ScheduleSet.COLUMN_SCHEDULE_STEP_ID,
                parentColumns = ScheduleStep.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleSet.COLUMN_SCHEDULE_STEP_ID })
    }
)
public class ScheduleSet {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_set";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_ORDER = "order";
    public static final String COLUMN_SCHEDULE_STEP_ID = "schedule_step_id";
    public static final String COLUMN_REMOTE_SCHEDULE_STEP_ID = "remote_schedule_step_id";

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
    @ColumnInfo(name = COLUMN_ORDER)
    @Expose @SerializedName("orderNumber")
    private int order;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_STEP_ID)
    @Expose @SerializedName("localScheduleStepId")
    private long scheduleStepId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_STEP_ID)
    @Expose @SerializedName("scheduleStepId")
    private long remoteScheduleStepId = -1;

    @NonNull @Ignore
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
