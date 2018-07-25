package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.AthleteDao;

public class AthleteHelper {

    private final LocalDatabase localDatabaseInstance;
    private final AthleteDao athleteDaoInstance;
    private static AthleteHelper instance = null;

    private AthleteHelper(@NonNull LocalDatabase localDatabaseInstance){
        this.localDatabaseInstance = localDatabaseInstance;
        this.athleteDaoInstance = localDatabaseInstance.getAthleteDao();
    }

    /**
     * Creates an AthleteHelper instance that will help us to work with Athlete's database table
     * If the instance had already been created previously, it simply returns it
     * @param localDatabaseInstance a local database instance used to retrieve Dao implementations
     *                              and other DB related things
     * @return an AthleteHelper instance
     */
    public static AthleteHelper getInstance(@NonNull LocalDatabase localDatabaseInstance){
        if(instance == null){
            synchronized (AthleteHelper.class){
                if(instance == null){
                    instance = new AthleteHelper(localDatabaseInstance);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts an Athlete into the database, if it doesn't already exists in it,
     * otherwise it attempts to update the database object's row
     * @param athleteUser the Athlete we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(Athlete athleteUser){
        boolean done = false;
        if(athleteUser != null){
            this.localDatabaseInstance.beginTransaction();
            long insertedId = this.athleteDaoInstance.insert(athleteUser);
            if(insertedId == -1){
                int updatedRows = athleteDaoInstance.update(athleteUser);
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
     * Retrieves the Athlete who has the given athlete user's ID
     * @param athleteUserId the ID of the Athlete we want to retrieve
     * @return an Athlete object whose ID is the one given as parameter, if some results has been
     *         returned from the database, otherwise NULL
     */
    public Athlete getById(long athleteUserId){
        return this.athleteDaoInstance.getById(athleteUserId);
    }

}
