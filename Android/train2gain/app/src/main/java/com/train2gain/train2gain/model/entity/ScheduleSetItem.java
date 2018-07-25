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

@Entity(
    tableName = ScheduleSetItem.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = ScheduleSet.class, childColumns = ScheduleSetItem.COLUMN_SCHEDULE_SET_ID,
                parentColumns = ScheduleSet.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Exercise.class, childColumns = ScheduleSetItem.COLUMN_EXERCISE_ID,
                parentColumns = Exercise.COLUMN_ID, onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleSetItem.COLUMN_SCHEDULE_SET_ID }),
        @Index(value = { ScheduleSetItem.COLUMN_EXERCISE_ID })
    }
)
public class ScheduleSetItem {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_set_item";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_ORDER = "order";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_SCHEDULE_SET_ID = "schedule_set_id";
    public static final String COLUMN_REMOTE_SCHEDULE_SET_ID = "remote_schedule_set_id";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";

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
    @ColumnInfo(name = COLUMN_REPS)
    @Expose @SerializedName("reps")
    private int reps;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_SET_ID)
    @Expose @SerializedName("localScheduleSetId")
    private long scheduleSetId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_SET_ID)
    @Expose @SerializedName("scheduleSetId")
    private long remoteScheduleSetId = -1;

    @NonNull
    @ColumnInfo(name = COLUMN_EXERCISE_ID)
    @Expose @SerializedName("exerciseId")
    private long exerciseId = 0;

    @NonNull @Ignore
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
