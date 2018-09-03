package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Trainer;

@Dao
public abstract class TrainerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(@NonNull Trainer trainerUser);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull Trainer trainerUser);

    @Query("SELECT * FROM " +  Trainer.TABLE_NAME + " " +
           "WHERE " +  Trainer.COLUMN_USER_ID + " = :trainerUserId")
    public abstract Trainer getById(long trainerUserId);

}
