package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Gym;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.GymDao;

public class GymHelper {

    private final LocalDatabase localDatabase;
    private final GymDao gymDaoInstance;
    private static GymHelper instance = null;

    private GymHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.gymDaoInstance = localDatabase.getGymDao();
    }

    /**
     * Creates a GymHelper instance that will help us to work with Gym's database table
     * If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a GymHelper instance
     */
    public static GymHelper getInstance(@NonNull LocalDatabase localDatabase){
        if(instance == null){
            synchronized (GymHelper.class){
                if(instance == null){
                    instance = new GymHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a Gym into the database, if it doesn't already exists in it,
     * otherwise it attempts to update the database object's row
     * @param gym the Gym we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(Gym gym){
        boolean done = false;
        if(gym == null){
            this.localDatabase.beginTransaction();
            long insertedId = this.gymDaoInstance.insert(gym);
            if(insertedId == -1){
                int updatedRows = this.gymDaoInstance.update(gym);
                if(updatedRows == 1){
                    done = true;
                }
            }else{
                done = true;
            }
            if(done == true){
                this.localDatabase.setTransactionSuccessful();
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the Gym that has the given gym's ID
     * @param gymId the ID of the Gym we want to retrieve
     * @return a Gym object whose ID is the one given as parameter, if some results
     *         has been returned from the database, otherwise NULL
     */
    public Gym getById(long gymId){
        return this.gymDaoInstance.getById(gymId);
    }

}
