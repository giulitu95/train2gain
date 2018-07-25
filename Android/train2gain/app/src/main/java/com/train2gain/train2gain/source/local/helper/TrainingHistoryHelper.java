package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.TrainingHistory;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.TrainingHistoryDao;

import java.util.List;

public class TrainingHistoryHelper {

    private final LocalDatabase localDatabase;
    private final TrainingHistoryDao trainingHistoryDaoInstance;
    private final ScheduleDailyWorkoutHelper scheduleDailyWorkoutHelperInstance;
    private static TrainingHistoryHelper instance = null;

    private TrainingHistoryHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.trainingHistoryDaoInstance = localDatabase.getTrainingHistoryDao();
        this.scheduleDailyWorkoutHelperInstance = localDatabase.getScheduleDailyWorkoutHelper();
    }

    /**
     * Creates a TrainingHistoryHelper instance that will help us to work with TrainingHistory's
     * database table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a TrainingHistoryHelper instance
     */
    public static TrainingHistoryHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (TrainingHistoryHelper.class){
                if(instance == null){
                    instance = new TrainingHistoryHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a TrainingHistory object into the database.
     * The schedule daily workout FK ID in the training history object must be already set.
     * @param trainingHistory the training history object we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(TrainingHistory trainingHistory){
        boolean done = false;
        if(trainingHistory != null){
            long insertedId = this.trainingHistoryDaoInstance.insert(trainingHistory);
            if(insertedId != -1){
                done = true;
            }
        }
        return done;
    }

    /**
     * Inserts a list of TrainingHistory objects into the database.
     * If a TrainingHistory object already exists, it will not be inserted.
     * @param trainingHistoryList the list of of TrainingHistory objects we want to insert into
     *                            the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<TrainingHistory> trainingHistoryList){
        boolean done = false;
        if(trainingHistoryList != null && !trainingHistoryList.isEmpty()){
            this.localDatabase.beginTransaction();
            for(TrainingHistory trainingHistory : trainingHistoryList){
                if(trainingHistory != null){
                    boolean exists = this.trainingHistoryDaoInstance
                            .checkByRemoteId(trainingHistory.getRemoteId());
                    if(exists == true){
                        trainingHistoryList.remove(trainingHistory);
                    }else{
                        long scheduleDailyWorkoutId = this.scheduleDailyWorkoutHelperInstance
                                .getIdByRemoteId(trainingHistory.getRemoteDailyWorkoutId());
                        if(scheduleDailyWorkoutId != -1){
                            trainingHistory.setDailyWorkoutId(scheduleDailyWorkoutId);
                        }else{
                            trainingHistoryList = null;
                            break;
                        }
                    }
                }else{
                    trainingHistoryList = null;
                    break;
                }
            }
            if(trainingHistoryList != null && !trainingHistoryList.isEmpty()){
                long[] insertedIds = this.trainingHistoryDaoInstance.insert(trainingHistoryList);
                for(long i : insertedIds){
                    if(i == -1){
                        trainingHistoryList = null;
                        break;
                    }
                }
                if(trainingHistoryList != null){
                    this.localDatabase.setTransactionSuccessful();
                    done = true;
                }
            }else if(trainingHistoryList != null && trainingHistoryList.isEmpty()){
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * TODO: to insert / implement database Helper GETTERS methods
     */
}
