package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleLoad;

import java.util.List;

@Dao
public abstract class ScheduleLoadDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    public abstract long insert(@NonNull ScheduleLoad scheduleLoad);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleLoad> scheduleLoadList);

    /**
     * Checks to see if some ScheduleLoad already exists in the database
     * @param remoteScheduleLoadId the ScheduleLoad's remote id
     * @return true, if the schedule load already exists in the database
     *         false otherwise
     */
    @Query("SELECT EXISTS(" +
                "SELECT 1 FROM " +  ScheduleLoad.TABLE_NAME + " " +
                "WHERE " + ScheduleLoad.COLUMN_REMOTE_ID + " = :remoteScheduleLoadId " +
                "LIMIT 1" +
            ")")
    public abstract boolean checkByRemoteId(long remoteScheduleLoadId);

    /**
     * TODO: to insert / implement database DAO GETTERS methods
     */

}
