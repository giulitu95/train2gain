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

import java.util.Date;
import java.util.List;

@Entity(
    tableName = Schedule.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = Athlete.class, childColumns = Schedule.COLUMN_ATHLETE_USER_ID, parentColumns = Athlete.COLUMN_USER_ID,
                onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Trainer.class, childColumns = Schedule.COLUMN_TRAINER_USER_ID, parentColumns = Trainer.COLUMN_USER_ID,
                onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { Schedule.COLUMN_ATHLETE_USER_ID}),
        @Index(value = { Schedule.COLUMN_TRAINER_USER_ID}),
        @Index(value = { Schedule.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class Schedule {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_ATHLETE_USER_ID = "athlete_user_id";
    public static final String COLUMN_TRAINER_USER_ID = "trainer_user_id";
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
    @ColumnInfo(name = COLUMN_START_DATE)
    @Expose @SerializedName("startDate")
    private Date startDate;

    @NonNull
    @ColumnInfo(name = COLUMN_ATHLETE_USER_ID)
    @Expose @SerializedName("athleteId")
    private long athleteUserId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_TRAINER_USER_ID)
    @Expose @SerializedName("trainerId")
    private long trainerUserId = -1;

    @NonNull
    @ColumnInfo(name = COLUMN_SYNCED_WITH_SERVER)
    private boolean syncedWithServer = false;

    @Nullable @Ignore
    @Expose @SerializedName("dailyWorkouts")
    private List<ScheduleDailyWorkout> scheduleDailyWorkoutList = null;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @Nullable public long getRemoteId() {
        return remoteId;
    }

    @NonNull public Date getStartDate() {
        return startDate;
    }

    @NonNull public long getAthleteUserId() {
        return athleteUserId;
    }

    @Nullable public long getTrainerUserId() {
        return trainerUserId;
    }

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
    }

    @Nullable public List<ScheduleDailyWorkout> getScheduleDailyWorkoutList() {
        return scheduleDailyWorkoutList;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setRemoteId(@Nullable long remoteId) {
        this.remoteId = remoteId;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    public void setAthleteUserId(@NonNull long athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public void setTrainerUserId(@Nullable long trainerUserId) {
        this.trainerUserId = trainerUserId;
    }

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

    public void setScheduleDailyWorkoutList(@Nullable List<ScheduleDailyWorkout> scheduleDailyWorkoutList) {
        this.scheduleDailyWorkoutList = scheduleDailyWorkoutList;
    }

}
