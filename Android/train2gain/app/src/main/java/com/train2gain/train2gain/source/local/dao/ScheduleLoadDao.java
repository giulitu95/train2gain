package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.model.entity.ScheduleStep;

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
     * Retrieves the list of ScheduleLoads related to a particular Schedule
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleLoads
     * @return the list of ScheduleLoads sorted by schedule load's insertion date (in decreasing
     *         order), if the query has returned some results, otherwise NULL
     */
    @Query("SELECT " + ScheduleLoad.TABLE_NAME+ ".* " +
           "FROM " + ScheduleLoad.TABLE_NAME + " " +
           "INNER JOIN " + ScheduleSetItem.TABLE_NAME + " " +
           "ON " + ScheduleLoad.TABLE_NAME + "." + ScheduleLoad.COLUMN_SCHEDULE_SET_ITEM_ID + " = " + ScheduleSetItem.TABLE_NAME + "." + ScheduleSetItem.COLUMN_ID + " " +
           "INNER JOIN " + ScheduleSet.TABLE_NAME + " " +
           "ON " + ScheduleSetItem.TABLE_NAME + "." + ScheduleSetItem.COLUMN_SCHEDULE_SET_ID + " = " + ScheduleSet.TABLE_NAME + "." + ScheduleSet.COLUMN_ID + " " +
           "INNER JOIN " + ScheduleStep.TABLE_NAME + " " +
           "ON " + ScheduleSet.TABLE_NAME + "." + ScheduleSet.COLUMN_SCHEDULE_STEP_ID + " = " + ScheduleStep.TABLE_NAME + "." + ScheduleStep.COLUMN_ID + " " +
           "INNER JOIN " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "ON " +ScheduleStep.TABLE_NAME + "." + ScheduleStep.COLUMN_SCHEDULE_DAILY_WORKOUT_ID + " = " + ScheduleDailyWorkout.TABLE_NAME + "." + ScheduleDailyWorkout.COLUMN_ID + " " +
           "INNER JOIN " + Schedule.TABLE_NAME + " " +
           "ON " + ScheduleDailyWorkout.TABLE_NAME + "." + ScheduleDailyWorkout.COLUMN_SCHEDULE_ID + " = " + Schedule.TABLE_NAME + "." + Schedule.COLUMN_ID + " " +
           "WHERE " + Schedule.TABLE_NAME + "." + Schedule.COLUMN_ID + " = :scheduleId " +
           "ORDER BY " + ScheduleLoad.TABLE_NAME + "." + ScheduleLoad.COLUMN_INSERT_DATE + " DESC")
    public abstract List<ScheduleLoad> getScheduleLoadListByScheduleId(long scheduleId);

}
