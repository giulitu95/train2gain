package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleSet;

import java.util.List;

@Dao
public abstract class ScheduleSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<ScheduleSet> scheduleSetList);

    /**
     * Retrieves the list of ScheduleSets related to a particular ScheduleStep
     * @param scheduleStepId for which ScheduleStep we want to retrieve the list of ScheduleSets
     * @return the list of ScheduleSets sorted by order number (in ascending order), if the query
     *         has returned some results, otherwise NULL
     */
    @Query("SELECT * FROM " + ScheduleSet.TABLE_NAME + " " +
           "WHERE " +  ScheduleSet.COLUMN_SCHEDULE_STEP_ID + " = :scheduleStepId " +
           "ORDER BY `" + ScheduleSet.COLUMN_ORDER + "` ASC")
    public abstract List<ScheduleSet> getScheduleSetListByScheduleStepId(long scheduleStepId);

}
