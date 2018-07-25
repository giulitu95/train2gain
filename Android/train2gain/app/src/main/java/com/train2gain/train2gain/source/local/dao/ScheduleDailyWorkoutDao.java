package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;

import java.util.Date;
import java.util.List;

@Dao
public abstract class ScheduleDailyWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleDailyWorkout> scheduleDailyWorkoutList);

    /**
     * Updates the date (last time) when the ScheduleDailyWorkout, given as param, was executed
     * @param lastExecutionDate the date (last time) when the ScheduleDailyWorkout was executed
     * @param scheduleDailyWorkoutId the DailyWorkout we want to update with the new last
     *                               execution's date
     * @return an int that represents the number of updated rows: 1, update has been successful,
     *         otherwise not
     */
    @Query("UPDATE " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "SET " + ScheduleDailyWorkout.COLUMN_LAST_EXECUTION_DATE + " = :lastExecutionDate " +
           "WHERE " + ScheduleDailyWorkout.COLUMN_ID + " = :scheduleDailyWorkoutId")
    public abstract int updateScheduleDailyWorkoutLastExecutionDate(@NonNull Date lastExecutionDate,
                                                                    long scheduleDailyWorkoutId);

    @Query("SELECT * FROM " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "WHERE " + ScheduleDailyWorkout.COLUMN_ID + " = :scheduleDailyWorkoutId")
    public abstract ScheduleDailyWorkout getById(long scheduleDailyWorkoutId);

    /**
     * Checks to see if some ScheduleDailyWorkout already exists in the database
     * @param remoteScheduleDailyWorkoutId the ScheduleDailyWorkout's remote id
     * @return true, if the schedule daily workout already exists in the database
     *         false otherwise
     */
    @Query("SELECT EXISTS(" +
                "SELECT 1 FROM " +  ScheduleDailyWorkout.TABLE_NAME + " " +
                "WHERE " + ScheduleDailyWorkout.COLUMN_REMOTE_ID + " = :remoteScheduleDailyWorkoutId " +
                "LIMIT 1" +
            ")")
    public abstract boolean checkByRemoteId(long remoteScheduleDailyWorkoutId);

    /**
     * Retrieves the list of ScheduleDailyWorkouts related to a particular Schedule
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleDailyWorkouts
     * @return the list of ScheduleDailyWorkouts sorted by order number (in ascending order), if
     *         the query has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "WHERE " + ScheduleDailyWorkout.COLUMN_SCHEDULE_ID + " = :scheduleId " +
           "ORDER BY `" + ScheduleDailyWorkout.COLUMN_ORDER + "` ASC")
    public abstract List<ScheduleDailyWorkout> getScheduleDailyWorkoutListByScheduleId(long scheduleId);

    /**
     * Retrieves the ScheduleDailyWorkout that was executed less recently. The method checks the
     * last execution's date for all the ScheduleDailyWorkouts of a specific Schedule and it takes
     * the one that has the oldest last execution's date. If there are one or more objects with
     * last execution's date set to NULL, the method sorts the results base on the order number and
     * it retrieves only the first item.
     * @param scheduleId for which schedule we want to retrieve the ScheduleDailyWorkout that was
     *                   executed less recently
     * @return the ScheduleDailyWorkout that was executed less recently, if the query has returned
     *         some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "WHERE " + ScheduleDailyWorkout.COLUMN_SCHEDULE_ID + " = :scheduleId " +
           "ORDER BY " + ScheduleDailyWorkout.COLUMN_LAST_EXECUTION_DATE + " DESC, " +
           " `" + ScheduleDailyWorkout.COLUMN_ORDER + "` ASC " +
           "LIMIT 1")
    public abstract ScheduleDailyWorkout getScheduleDailyWorkoutOfTheDayByScheduleId(long scheduleId);


    /**
     * Retrieves the ID of the ScheduleDailyWorkout that has the given schedule daily workout's
     * remote ID
     * @param scheduleDailyWorkoutRemoteId the remote ID of the schedule daily workout for which
     *                                    we want the ID
     * @return the ID of the ScheduleDailyWorkout, if the query has returned some results
     *         -1 otherwise
     */
    @Query("SELECT " + ScheduleDailyWorkout.COLUMN_ID + " " +
            "FROM " + ScheduleDailyWorkout.TABLE_NAME + " " +
            "WHERE " + ScheduleDailyWorkout.COLUMN_REMOTE_ID + " = :scheduleDailyWorkoutRemoteId")
    public abstract long getIdByRemoteId(long scheduleDailyWorkoutRemoteId);

}

