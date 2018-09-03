package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.model.entity.ScheduleStep;

import java.util.List;

@Dao
public abstract class ScheduleNoteDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    public abstract long insert(@NonNull ScheduleNote scheduleNote);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleNote> scheduleNoteList);

    /**
     * Checks to see if some ScheduleNote already exists in the database
     * @param remoteScheduleNoteId the ScheduleNote's remote id
     * @return true, if the schedule note already exists in the database
     *         false otherwise
     */
    @Query("SELECT EXISTS(" +
                "SELECT 1 FROM " +  ScheduleNote.TABLE_NAME + " " +
                "WHERE " + ScheduleNote.COLUMN_REMOTE_ID + " = :remoteScheduleNoteId " +
                "LIMIT 1" +
            ")")
    public abstract boolean checkByRemoteId(long remoteScheduleNoteId);

    /**
     * Retrieves the list of ScheduleNotes related to a particular ScheduleStep
     * @param scheduleStepId for which ScheduleStep we want to retrieve the list of ScheduleNotes
     * @return the list of ScheduleNotes sorted by schedule note's creation date (in decreasing
     *         order), if the query has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleNote.TABLE_NAME + " " +
           "WHERE " + ScheduleNote.COLUMN_SCHEDULE_STEP_ID + " = :scheduleStepId " +
           "ORDER BY " + ScheduleNote.COLUMN_CREATION_DATE + " DESC")
    public abstract List<ScheduleNote> getScheduleNoteListByScheduleStepId(long scheduleStepId);

    /**
     * Retrieves the list of ScheduleNotes related to a particular Schedule
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleNotes
     * @return the list of ScheduleNotes sorted by schedule note's creation date (in decreasing
     *         order), if the query has returned some results, otherwise NULL
     */
    @Query("SELECT " + ScheduleNote.TABLE_NAME + ".*" + " " +
           "FROM " + ScheduleNote.TABLE_NAME + " " +
           "INNER JOIN " + ScheduleStep.TABLE_NAME + " " +
           "ON " + ScheduleNote.TABLE_NAME + "." + ScheduleNote.COLUMN_SCHEDULE_STEP_ID + " = " + ScheduleStep.TABLE_NAME + "." + ScheduleStep.COLUMN_ID + " " +
           "INNER JOIN " + ScheduleDailyWorkout.TABLE_NAME + " " +
           "ON " + ScheduleStep.TABLE_NAME + "." + ScheduleStep.COLUMN_SCHEDULE_DAILY_WORKOUT_ID + " = " + ScheduleDailyWorkout.TABLE_NAME + "." + ScheduleDailyWorkout.COLUMN_ID + " " +
           "INNER JOIN " + Schedule.TABLE_NAME + " " +
           "ON " + ScheduleDailyWorkout.TABLE_NAME + "." + ScheduleDailyWorkout.COLUMN_SCHEDULE_ID + " = " + Schedule.TABLE_NAME + "." + Schedule.COLUMN_ID + " " +
           "WHERE " + Schedule.TABLE_NAME + "." + Schedule.COLUMN_ID + " = :scheduleId " +
           "ORDER BY " + ScheduleNote.TABLE_NAME + "." + ScheduleNote.COLUMN_CREATION_DATE + " DESC")
    public abstract List<ScheduleNote> getScheduleNoteListByScheduleId(long scheduleId);

}
