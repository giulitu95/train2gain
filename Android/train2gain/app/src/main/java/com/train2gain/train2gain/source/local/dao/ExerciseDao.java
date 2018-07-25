package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Exercise;

import java.util.List;

@Dao
public abstract class ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<Exercise> exerciseList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull List<Exercise> exerciseList);

    @Query("SELECT * FROM " + Exercise.TABLE_NAME + " " +
           "WHERE " + Exercise.COLUMN_ID + " = :exerciseId")
    public abstract Exercise getById(long exerciseId);

    @Query("SELECT * FROM " + Exercise.TABLE_NAME + " " +
           "ORDER BY " + Exercise.COLUMN_NAME + " ASC")
    public abstract List<Exercise> getAll();

}
