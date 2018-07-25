package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;

@Dao
public abstract class AthleteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(@NonNull Athlete athleteUser);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull Athlete athleteUser);

    @Query("SELECT * FROM " +  Athlete.TABLE_NAME + " " +
           "WHERE " +  Athlete.COLUMN_USER_ID + " = :athleteUserId")
    public abstract Athlete getById(long athleteUserId);

}
