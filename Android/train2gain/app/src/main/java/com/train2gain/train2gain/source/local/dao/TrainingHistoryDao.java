package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.TrainingHistory;

import java.util.List;

@Dao
public abstract class TrainingHistoryDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    public abstract long insert(@NonNull TrainingHistory trainingHistory);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<TrainingHistory> trainingHistoryList);

    /**
     * Checks to see if some TrainingHistory object already exists in the database
     * @param remoteTrainingHistoryId the TrainingHistory's remote id
     * @return true, if the training history object already exists in the database
     *         false otherwise
     */
    @Query("SELECT EXISTS(" +
                "SELECT 1 FROM " +  TrainingHistory.TABLE_NAME + " " +
                "WHERE " + TrainingHistory.COLUMN_REMOTE_ID + " = :remoteTrainingHistoryId " +
                "LIMIT 1" +
            ")")
    public abstract boolean checkByRemoteId(long remoteTrainingHistoryId);

    /**
     * TODO: to insert / implement database DAO GETTERS methods
     */

}
