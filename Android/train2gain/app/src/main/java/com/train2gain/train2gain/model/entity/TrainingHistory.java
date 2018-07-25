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
    tableName = TrainingHistory.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = Athlete.class, childColumns = TrainingHistory.COLUMN_ATHLETE_USER_ID,
                parentColumns = Athlete.COLUMN_USER_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = ScheduleDailyWorkout.class, childColumns = TrainingHistory.COLUMN_DAILY_WORKOUT_ID,
                parentColumns = ScheduleDailyWorkout.COLUMN_ID, onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { TrainingHistory.COLUMN_ATHLETE_USER_ID }),
        @Index(value = { TrainingHistory.COLUMN_DAILY_WORKOUT_ID }),
        @Index(value = { TrainingHistory.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class TrainingHistory {

    // Table and columns name definitions
    public static final String TABLE_NAME = "training_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_TRAINING_DATE = "training_date";
    public static final String COLUMN_ATHLETE_USER_ID = "athlete_user_id";
    public static final String COLUMN_DAILY_WORKOUT_ID = "daily_workout_id";
    public static final String COLUMN_REMOTE_DAILY_WORKOUT_ID = "remote_daily_workout_id";
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
    @ColumnInfo(name = COLUMN_TRAINING_DATE)
    @Expose @SerializedName("date")
    private Date trainingDate;

    @NonNull
    @ColumnInfo(name = COLUMN_ATHLETE_USER_ID)
    @Expose @SerializedName("athleteId")
    private long athleteUserId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_DAILY_WORKOUT_ID)
    @Expose @SerializedName("localDailyWorkoutId")
    private long dailyWorkoutId = -1;

    @Nullable
    @ColumnInfo(name = COLUMN_REMOTE_DAILY_WORKOUT_ID)
    @Expose @SerializedName("dailyWorkoutId")
    private long remoteDailyWorkoutId = -1;

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

    @NonNull public Date getTrainingDate() {
        return trainingDate;
    }

    @NonNull public long getAthleteUserId() {
        return athleteUserId;
    }

    @Nullable public long getDailyWorkoutId() {
        return dailyWorkoutId;
    }

    @Nullable public long getRemoteDailyWorkoutId() {
        return remoteDailyWorkoutId;
    }

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setTrainingDate(@NonNull Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public void setAthleteUserId(@NonNull long athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public void setDailyWorkoutId(@Nullable long dailyWorkoutId) {
        this.dailyWorkoutId = dailyWorkoutId;
    }

    public void setRemoteDailyWorkoutId(@Nullable long remoteDailyWorkoutId) {
        this.remoteDailyWorkoutId = remoteDailyWorkoutId;
    }

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

}
