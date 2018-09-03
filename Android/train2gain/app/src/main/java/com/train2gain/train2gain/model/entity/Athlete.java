package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(
    tableName = Athlete.TABLE_NAME,
    primaryKeys = { Athlete.COLUMN_USER_ID},
    foreignKeys = {
        @ForeignKey(entity = User.class, childColumns = Athlete.COLUMN_USER_ID, parentColumns = User.COLUMN_ID,
                onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { Athlete.COLUMN_TRAINER_USER_ID}),
        @Index(value = { Athlete.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class Athlete {

    // Table and columns name definitions
    public static final String TABLE_NAME = "athlete";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_FIRST_WORKOUT_DATE = "first_workout_date";
    public static final String COLUMN_TRAINER_USER_ID = "trainer_user_id";
    public static final String COLUMN_SYNCED_WITH_SERVER = "synced_with_server";

    @NonNull
    @ColumnInfo(name = COLUMN_USER_ID)
    @Expose @SerializedName("id")
    private long userId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_WEIGHT)
    @Expose @SerializedName("weight")
    private int weight = -1;

    @Nullable
    @ColumnInfo(name = COLUMN_HEIGHT)
    @Expose @SerializedName("height")
    private int height = -1;

    @Nullable
    @ColumnInfo(name = COLUMN_FIRST_WORKOUT_DATE)
    @Expose @SerializedName("firstWorkoutDate")
    private Date firstWorkoutDate = null;

    @Nullable
    @ColumnInfo(name = COLUMN_TRAINER_USER_ID)
    @Expose @SerializedName("trainerId")
    private long trainerUserId = -1;

    @NonNull
    @ColumnInfo(name = COLUMN_SYNCED_WITH_SERVER)
    private boolean syncedWithServer = false;


    // GETTERS

    @NonNull public long getUserId() {
        return userId;
    }

    @Nullable public int getWeight() {
        return weight;
    }

    @Nullable public int getHeight() {
        return height;
    }

    @Nullable public Date getFirstWorkoutDate() {
        return firstWorkoutDate;
    }

    @Nullable public long getTrainerUserId() {
        return trainerUserId;
    }

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
    }


    // SETTERS

    public void setUserId(@NonNull long userId) {
        this.userId = userId;
    }

    public void setWeight(@Nullable int weight) {
        this.weight = weight;
    }

    public void setHeight(@Nullable int height) {
        this.height = height;
    }

    public void setFirstWorkoutDate(@Nullable Date firstWorkoutDate) {
        this.firstWorkoutDate = firstWorkoutDate;
    }

    public void setTrainerUserId(@Nullable long trainerUserId) {
        this.trainerUserId = trainerUserId;
    }

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

}
