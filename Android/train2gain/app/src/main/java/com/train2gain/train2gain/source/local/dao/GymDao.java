package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Gym;

@Dao
public abstract class GymDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(@NonNull Gym gym);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull Gym gym);

    @Query("SELECT * FROM " +  Gym.TABLE_NAME + " " +
           "WHERE " +  Gym.COLUMN_ID + " = :gymId")
    public abstract Gym getById(long gymId);

}
