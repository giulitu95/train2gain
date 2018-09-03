package com.train2gain.train2gain.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.model.entity.User;

import java.util.List;

@Dao
public abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(@NonNull User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insert(@NonNull List<User> userList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull User user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract int update(@NonNull List<User> userList);

    @Query("SELECT * FROM " + User.TABLE_NAME + " " +
           "WHERE " + User.COLUMN_ID + " = :userId")
    public abstract User getById(long userId);

    /**
     * Retrieves the list of Users (athletes) which are followed by a particular Trainer
     * @param trainerUserId for which trainer we want to retrieve the list of users (athletes)
     * @return the list of users (athletes) sorted by name (in ascending order), if the query has
     *         returned some results, otherwise NULL
     */
    @Query("SELECT trainer_user_list.* " +
           "FROM (" +
                "SELECT " + User.TABLE_NAME + ".* FROM " + User.TABLE_NAME + " " +
                "WHERE " + User.TABLE_NAME + "." + User.COLUMN_ID + " != :trainerUserId" + " " +
                "UNION" + " " +
                "SELECT " + User.TABLE_NAME + ".* FROM " + User.TABLE_NAME + " " +
                "INNER JOIN " + Athlete.TABLE_NAME + " " +
                "ON " + Athlete.TABLE_NAME + "." + Athlete.COLUMN_USER_ID + " = " + User.TABLE_NAME + "." + User.COLUMN_ID + " " +
                "WHERE " + Athlete.TABLE_NAME + "." + Athlete.COLUMN_TRAINER_USER_ID + " = :trainerUserId" + " " +
                ") AS trainer_user_list" + " " +
           "ORDER BY trainer_user_list." + User.COLUMN_DISPLAY_NAME + " ASC")
    public abstract List<User> getByTrainerUserId(long trainerUserId);

}
