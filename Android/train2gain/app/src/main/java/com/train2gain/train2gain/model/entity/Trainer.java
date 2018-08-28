package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(
    tableName = Trainer.TABLE_NAME,
    primaryKeys = { Trainer.COLUMN_USER_ID},
    foreignKeys = {
        @ForeignKey(entity = User.class, childColumns = Trainer.COLUMN_USER_ID, parentColumns = User.COLUMN_ID,
                onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { Trainer.COLUMN_GYM_ID }),
        @Index(value = { Trainer.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class Trainer {

    // Table and columns name definitions
    public static final String TABLE_NAME = "trainer";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_GYM_ID = "gym_id";
    public static final String COLUMN_SYNCED_WITH_SERVER = "synced_with_server";

    @NonNull
    @ColumnInfo(name = COLUMN_USER_ID)
    @Expose @SerializedName("id")
    private long userId = 0;

    @Nullable
    @ColumnInfo(name = COLUMN_GYM_ID)
    @Expose @SerializedName("gymId")
    private long gymId = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_SYNCED_WITH_SERVER)
    private boolean syncedWithServer = false;

    @Nullable @Ignore
    @Expose @SerializedName("gym")
    private Gym gym;


    // GETTERS

    @NonNull public long getUserId() {
        return userId;
    }

    @NonNull public long getGymId() {
        return gymId;
    }

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
    }

    @NonNull public Gym getGym() {
        return gym;
    }


    // SETTERS

    public void setUserId(@NonNull long userId) {
        this.userId = userId;
    }

    public void setGymId(@NonNull long gymId) {
        this.gymId = gymId;
    }

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

    public void setGym(@NonNull Gym gym) {
        this.gym = gym;
    }

}
