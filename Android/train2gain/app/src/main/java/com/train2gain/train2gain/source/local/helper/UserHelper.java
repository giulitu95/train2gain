package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    private final LocalDatabase localDatabaseInstance;
    private final UserDao userDaoInstance;
    private static UserHelper instance = null;

    private UserHelper(@NonNull LocalDatabase localDatabaseInstance){
        this.localDatabaseInstance = localDatabaseInstance;
        this.userDaoInstance = localDatabaseInstance.getUserDao();
    }

    /**
     * Creates a UserHelper instance that will help us to work with User's database table
     * If the instance had already been created previously, it simply returns it
     * @param localDatabaseInstance a local database instance used to retrieve Dao implementations
     *                              and other DB related things
     * @return a UserHelper instance
     */
    public static UserHelper getInstance(@NonNull LocalDatabase localDatabaseInstance){
        if(instance == null){
            synchronized (UserHelper.class){
                if(instance == null){
                    instance = new UserHelper(localDatabaseInstance);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a User into the database, if it doesn't already exists in it,
     * otherwise it attempts to update the database object's row
     * @param user the User we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(User user){
        boolean done = false;
        if(user != null){
            this.localDatabaseInstance.beginTransaction();
            long insertedId = this.userDaoInstance.insert(user);
            if(insertedId == -1){
                int updatedRows = this.userDaoInstance.update(user);
                if(updatedRows == 1){
                    done = true;
                }
            }else{
                done = true;
            }
            if(done == true){
                this.localDatabaseInstance.setTransactionSuccessful();
            }
            this.localDatabaseInstance.endTransaction();
        }
        return done;
    }

    /**
     * Inserts a list of Users into the database. For all of those that already exist in it,
     * the method attempts to update their rows in the database
     * @param userList the list of Users we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(List<User> userList){
        boolean done = false;
        if(userList != null && !userList.isEmpty()){
            this.localDatabaseInstance.beginTransaction();
            long[] insertedIds = this.userDaoInstance.insert(userList);
            List<User> notInsertedUserList = new ArrayList<User>();
            for(int i = 0; i < insertedIds.length; i++){
                if(insertedIds[i] == -1){
                    notInsertedUserList.add(userList.get(i));
                }
            }
            if(!notInsertedUserList.isEmpty()){
                int updatedRows = this.userDaoInstance.update(notInsertedUserList);
                if(updatedRows == notInsertedUserList.size()){
                    done = true;
                }
            }else{
                done = true;
            }
            if(done == true){
                this.localDatabaseInstance.setTransactionSuccessful();
            }
            this.localDatabaseInstance.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the User who has the given user's ID
     * @param userId the ID of the User we want to retrieve
     * @return a User object whose ID is the one given as parameter, if some results
     *         has been returned from the database, otherwise NULL
     */
    public User getById(long userId){
        return this.userDaoInstance.getById(userId);
    }

    /**
     * Retrieves the list of Users (athletes) which are followed by a particular Trainer
     * @param trainerUserId for which trainer we want to retrieve the list of users (athletes)
     * @return the list of Users (athletes), if some results has been returned from the database,
     *         otherwise NULL
     */
    public List<User> getByTrainerUserId(long trainerUserId){
        return this.userDaoInstance.getByTrainerUserId(trainerUserId);
    }

}
