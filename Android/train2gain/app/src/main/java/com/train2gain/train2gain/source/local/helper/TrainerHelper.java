package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Gym;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.TrainerDao;

public class TrainerHelper {

    private final LocalDatabase localDatabase;
    private final TrainerDao trainerDaoInstance;
    private final GymHelper gymHelperInstance;
    private static TrainerHelper instance = null;

    private TrainerHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.trainerDaoInstance = localDatabase.getTrainerDao();
        this.gymHelperInstance = localDatabase.getGymHelper();
    }

    /**
     * Creates a TrainerHelper instance that will help us to work with Trainer's database table
     * If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a TrainerHelper instance
     */
    public static TrainerHelper getInstance(@NonNull LocalDatabase localDatabase){
        if(instance == null){
            synchronized (TrainerHelper.class) {
                if(instance == null){
                    instance = new TrainerHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a Trainer, along with the Gym where he / she works, into the database, if both the
     * Trainer and the Gym don't already exist in it. Otherwise the method will only attempt
     * to update the information
     * @param trainerUser the Trainer we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(Trainer trainerUser) {
        boolean done = false;
        if(trainerUser != null){
            this.localDatabase.beginTransaction();
            boolean inserted = this.gymHelperInstance.insertOrUpdateIfExists(trainerUser.getGym());
            if(inserted == true){
                long insertedId = this.trainerDaoInstance.insert(trainerUser);
                if(insertedId == -1){
                    int updatedRows = this.trainerDaoInstance.update(trainerUser);
                    if(updatedRows == 1){
                        done = true;
                    }
                }else{
                    done = true;
                }
            }
            if(done == true){
                this.localDatabase.setTransactionSuccessful();
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the Trainer user who has the given user's ID.
     * It retrieves, also, the object nested info like the gym where he / she works
     * @param trainerUserId the ID of the Trainer user we want to retrieve
     * @return a Trainer user object whose ID is the one given as parameter, if all the results
     *         have been retrieved correctly, otherwise NULL
     */
    public Trainer getById(long trainerUserId){
        this.localDatabase.beginTransaction();
        Trainer trainerUser = this.trainerDaoInstance.getById(trainerUserId);
        if(trainerUser != null){
            Gym gym = this.gymHelperInstance.getById(trainerUser.getGymId());
            trainerUser.setGym(gym);
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return trainerUser;
    }

}
