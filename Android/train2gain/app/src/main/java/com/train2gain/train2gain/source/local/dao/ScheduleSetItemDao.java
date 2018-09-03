package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleSetItem;

import java.util.List;

@Dao
public abstract class ScheduleSetItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleSetItem> scheduleSetItemList);

    /**
     * Retrieves the list of ScheduleSetItems related to a particular ScheduleSet
     * @param scheduleSetId for which ScheduleSet we want to retrieve the list of ScheduleSetItems
     * @return the list of ScheduleSetItems sorted by order number (in ascending order), if the
     *         query has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleSetItem.TABLE_NAME + " " +
           "WHERE " + ScheduleSetItem.COLUMN_SCHEDULE_SET_ID + " = :scheduleSetId " +
           "ORDER BY `" + ScheduleSetItem.COLUMN_ORDER + "` ASC")
    public abstract List<ScheduleSetItem> getScheduleSetItemListByScheduleSetId(long scheduleSetId);

    /**
     * Retrieves the ID of the ScheduleSetItem that has the given schedule set item's remote ID
     * @param scheduleSetItemRemoteId the remote ID of the schedule set item for which we want the ID
     * @return the ID of the ScheduleSetItem, if the query has returned some results, otherwise -1
     */
    @Query("SELECT " + ScheduleSetItem.COLUMN_ID + " " +
           "FROM " + ScheduleSetItem.TABLE_NAME + " " +
           "WHERE " + ScheduleSetItem.COLUMN_REMOTE_ID + " = :scheduleSetItemRemoteId")
    public abstract long getIdByRemoteId(long scheduleSetItemRemoteId);

}
