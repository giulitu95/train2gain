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
import com.train2gain.train2gain.model.enums.ScheduleStepType;

import java.util.List;

@Entity(
    tableName = ScheduleStep.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = ScheduleDailyWorkout.class, childColumns = ScheduleStep.COLUMN_SCHEDULE_DAILY_WORKOUT_ID,
                parentColumns = ScheduleDailyWorkout.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleStep.COLUMN_SCHEDULE_DAILY_WORKOUT_ID })
    }
)
public class ScheduleStep {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_step";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_ORDER = "order";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_REST_TIME = "rest_time";
    public static final String COLUMN_SCHEDULE_DAILY_WORKOUT_ID = "schedule_daily_workout_id";
    public static final String COLUMN_REMOTE_SCHEDULE_DAILY_WORKOUT_ID = "remote_schedule_daily_workout_id";

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
    @ColumnInfo(name = COLUMN_TYPE)
    @Expose @SerializedName("scheduleStepTypeId")
    private ScheduleStepType type;

    @NonNull
    @ColumnInfo(name = COLUMN_REST_TIME)
    @Expose @SerializedName("restTime")
    private long restTime;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_DAILY_WORKOUT_ID)
    @Expose @SerializedName("localDailyWorkoutId")
    private long scheduleDailyWorkoutId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_DAILY_WORKOUT_ID)
    @Expose @SerializedName("dailyWorkoutId")
    private long remoteScheduleDailyWorkoutId = -1;

    @NonNull @Ignore
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
