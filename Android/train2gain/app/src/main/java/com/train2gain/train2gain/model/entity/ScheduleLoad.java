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
    tableName = ScheduleLoad.TABLE_NAME,
    foreignKeys = {
        @ForeignKey(entity = ScheduleSetItem.class, childColumns = ScheduleLoad.COLUMN_SCHEDULE_SET_ITEM_ID,
                parentColumns = ScheduleSetItem.COLUMN_ID, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = { ScheduleLoad.COLUMN_SCHEDULE_SET_ITEM_ID }),
        @Index(value = { ScheduleLoad.COLUMN_SYNCED_WITH_SERVER })
    }
)
public class ScheduleLoad {

    // Table and columns name definitions
    public static final String TABLE_NAME = "schedule_load";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REMOTE_ID = "remote_id";
    public static final String COLUMN_LOAD = "load";
    public static final String COLUMN_INSERT_DATE = "insert_date";
    public static final String COLUMN_SCHEDULE_SET_ITEM_ID = "schedule_set_item_id";
    public static final String COLUMN_REMOTE_SCHEDULE_SET_ITEM_ID = "remote_schedule_set_item_id";
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
    @ColumnInfo(name = COLUMN_LOAD)
    @Expose @SerializedName("weight")
    private int load;

    @NonNull
    @ColumnInfo(name = COLUMN_INSERT_DATE)
    @Expose @SerializedName("date")
    private Date insertDate;

    @NonNull
    @ColumnInfo(name = COLUMN_SCHEDULE_SET_ITEM_ID)
    private long scheduleSetItemId = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_REMOTE_SCHEDULE_SET_ITEM_ID)
    @Expose @SerializedName("schedule_item_id")
    private long remoteScheduleSetItemId = 0;

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

    @NonNull public int getLoad() {
        return load;
    }

    @NonNull public Date getInsertDate() {
        return insertDate;
    }

    @NonNull public long getScheduleSetItemId() {
        return scheduleSetItemId;
    }

    @NonNull public long getRemoteScheduleSetItemId() {
        return remoteScheduleSetItemId;
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

    public void setLoad(@NonNull int load) {
        this.load = load;
    }

    public void setInsertDate(@NonNull Date insertDate) {
        this.insertDate = insertDate;
    }

    public void setScheduleSetItemId(@NonNull long scheduleSetItemId) {
        this.scheduleSetItemId = scheduleSetItemId;
    }

    public void setRemoteScheduleSetItemId(@NonNull long remoteScheduleSetItemId) {
        this.remoteScheduleSetItemId = remoteScheduleSetItemId;
    }

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

}
