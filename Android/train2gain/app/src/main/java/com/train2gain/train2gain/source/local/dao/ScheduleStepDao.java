package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleStep;

import java.util.List;

@Dao
public abstract class ScheduleStepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleStep> scheduleStepList);

    /**
     * Retrieves the list of ScheduleSteps related to a particular ScheduleDailyWorkout
     * @param scheduleDailyWorkoutId for which ScheduleDailyWorkout we want to retrieve the list
     *                               of ScheduleSteps
     * @return the list of ScheduleSteps sorted by order number (in ascending order), if the query
     *         has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleStep.TABLE_NAME + " " +
           "WHERE " + ScheduleStep.COLUMN_SCHEDULE_DAILY_WORKOUT_ID + " = :scheduleDailyWorkoutId " +
           "ORDER BY `" + ScheduleStep.COLUMN_ORDER + "` ASC")
    public abstract List<ScheduleStep> getScheduleStepListByScheduleDailyWorkoutId(long scheduleDailyWorkoutId);

    /**
     * Retrieves the ID of the ScheduleStep that has the given schedule step's remote ID
     * @param scheduleStepRemoteId the remote ID of the schedule step for which we want the ID
     * @return the ID of the ScheduleStep, if the query has returned some results, otherwise -1
     */
    @Query("SELECT " + ScheduleStep.COLUMN_ID + " " +
           "FROM " + ScheduleStep.TABLE_NAME + " " +
           "WHERE " + ScheduleStep.COLUMN_REMOTE_ID + " = :scheduleStepRemoteId")
    public abstract long getIdByRemoteId(long scheduleStepRemoteId);

}
