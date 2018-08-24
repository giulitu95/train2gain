package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Schedule;

import java.util.List;

@Dao
public abstract class ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(@NonNull Schedule schedule);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<Schedule> scheduleList);

    @Query("SELECT * FROM " +  Schedule.TABLE_NAME + " " +
           "WHERE " + Schedule.COLUMN_ID + " = :scheduleId")
    public abstract Schedule getById(long scheduleId);

    /**
     * Checks to see if some Schedule already exists in the database
     * @param remoteScheduleId the Schedule's remote id
     * @return true, if the schedule already exists in the database
     *         false otherwise
     */
    @Query("SELECT EXISTS(" +
                "SELECT 1 FROM " +  Schedule.TABLE_NAME + " " +
                "WHERE " + Schedule.COLUMN_REMOTE_ID + " = :remoteScheduleId " +
                "LIMIT 1" +
           ")")
    public abstract boolean checkByRemoteId(long remoteScheduleId);

    /**
     * Retrieves the list of Schedules created by a Trainer for a particular Athlete user
     * @param athleteUserId for which athlete user we want to retrieves the schedules
     * @param trainerUserId the trainer who creates the schedules we want to retrieve
     * @return the list of Schedules sorted by schedule's creation date (in decreasing order), if
     *         the query has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " +  Schedule.TABLE_NAME + " " +
           "WHERE " + Schedule.COLUMN_ATHLETE_USER_ID + " = :athleteUserId " +
           "AND " + Schedule.COLUMN_TRAINER_USER_ID + " = :trainerUserId " +
           "ORDER BY " + Schedule.COLUMN_START_DATE + " DESC")
    public abstract List<Schedule> getByAthleteAndTrainerUserId(long athleteUserId, long trainerUserId);


    /**
     * Retrieves the ID of the Schedule that has the given schedule's remote ID
     * @param scheduleRemoteId the remote ID of the schedule for which we want the ID
     * @return the ID of the Schedule, if the query has returned some results, otherwise -1
     */
    @Query("SELECT " + Schedule.COLUMN_ID + " " +
           "FROM " + Schedule.TABLE_NAME + " " +
           "WHERE " + Schedule.COLUMN_REMOTE_ID + " = :scheduleRemoteId")
    public abstract long getIdByRemoteId(long scheduleRemoteId);

    /**
     * Retrieves the current (active) schedule's ID for the given athlete
     * @param athleteUserId for which athlete user we want to retrieve the current schedule ID
     * @return the current schedule's ID, if the query has returned some results
     *         -1 otherwise
     */
    @Query("SELECT " + Schedule.COLUMN_ID + " " +
           "FROM " +  Schedule.TABLE_NAME + " " +
           "WHERE " + Schedule.COLUMN_ATHLETE_USER_ID + " = :athleteUserId " +
           "AND " + Schedule.COLUMN_START_DATE + " <= date('now') " +
           "ORDER BY " + Schedule.COLUMN_START_DATE + " DESC " +
           "LIMIT 1"
    )
    public abstract long getCurrentScheduleIdByAthleteUserId(long athleteUserId);

}