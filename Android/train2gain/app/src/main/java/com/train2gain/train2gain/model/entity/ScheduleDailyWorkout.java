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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(
    tableName = ScheduleDailyWorkout.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = Schedule.class, childColumns = ScheduleDailyWorkout.COLUMN_SCHEDULE_ID,
                parentColumns = Schedule.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleDailyWorkout.COLUMN_SCHEDULE_ID })
    }
)
public class ScheduleDailyWorkout implements Serializable{

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_daily_workout";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_ORDER = "order";
    public static final String COLUMN_LAST_EXECUTION_DATE = "last_execution_date";
    public static final String COLUMN_SCHEDULE_ID = "schedule_id";
    public static final String COLUMN_REMOTE_SCHEDULE_ID = "remote_schedule_id";

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

    @Nullable
    @ColumnInfo(name = COLUMN_LAST_EXECUTION_DATE)
    private Date lastExecutionDate = null;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_ID)
    @Expose @SerializedName("localScheduleId")
    private long scheduleId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_ID)
    @Expose @SerializedName("scheduleId")
    private long remoteScheduleId = -1;

    @NonNull @Ignore
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

    @Nullable public Date getLastExecutionDate() {
        return lastExecutionDate;
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

    public void setLastExecutionDate(@Nullable Date lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
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
