package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleNote;

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

}
