package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleSetItemDao;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSetItemHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleSetItemDao scheduleSetItemDaoInstance;
    private final ExerciseHelper exerciseHelperInstance;
    private static ScheduleSetItemHelper instance = null;

    private ScheduleSetItemHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleSetItemDaoInstance = localDatabase.getScheduleSetItemDao();
        this.exerciseHelperInstance = localDatabase.getExerciseHelper();
    }

    /**
     * Creates a ScheduleSetItemHelper instance that will help us to work with ScheduleSetItem's
     * database table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleSetItemHelper instance
     */
    public static ScheduleSetItemHelper getInstance(@NonNull LocalDatabase localDatabase){
        if(instance == null){
            synchronized (ScheduleSetItemHelper.class) {
                if(instance == null){
                    instance = new ScheduleSetItemHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a list of ScheduleSetItems into the database. The method also attempts to insert /
     * update, for each ScheduleSetItem, the related Exercise object
     * @param scheduleSetItemList the list of ScheduleSetItems we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleSetItem> scheduleSetItemList) {
        boolean done = false;
        if(scheduleSetItemList != null && !scheduleSetItemList.isEmpty()){
            // Prepares the schedule set item's exercises list to insert
            List<Exercise> exerciseList = new ArrayList<Exercise>();
            for(ScheduleSetItem scheduleSetItem: scheduleSetItemList){
                if(scheduleSetItem != null) {
                    exerciseList.add(scheduleSetItem.getExercise());
                }
            }
            // Insert..
            this.localDatabase.beginTransaction();
            boolean inserted = this.exerciseHelperInstance.insertOrUpdateIfExists(exerciseList);
            if(inserted == true){
                long[] insertedIds = this.scheduleSetItemDaoInstance.insert(scheduleSetItemList);
                done = true;
                for(int i = 0; i < insertedIds.length; i++){
                    if(insertedIds[i] == -1){
                        done = false;
                        break;
                    }
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
     * Retrieves the list of ScheduleSetItems related to a particular ScheduleSet, given as param
     * It retrieves also, for each ScheduleSetItem, the related Exercise object
     * @param scheduleSetId for which ScheduleSet we want to retrieve the list of ScheduleSetItems
     * @return the list of ScheduleSetItems, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public List<ScheduleSetItem> getScheduleSetItemListByScheduleSetId(long scheduleSetId){
        this.localDatabase.beginTransaction();
        List<ScheduleSetItem> scheduleSetItemList = this.scheduleSetItemDaoInstance
                .getScheduleSetItemListByScheduleSetId(scheduleSetId);
        if(scheduleSetItemList != null){
            for(ScheduleSetItem scheduleSetItem : scheduleSetItemList){
                Exercise exercise = this.exerciseHelperInstance.getById(scheduleSetItem.getExerciseId());
                scheduleSetItem.setExercise(exercise);
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleSetItemList;
    }

    /**
     * Retrieves the ID of the ScheduleSetItem that has the given schedule set item's remote ID
     * @param scheduleSetItemRemoteId the remote ID of the schedule set item for which we want the ID
     * @return the ScheduleSetItem ID, if some result has been returned from the database
     *         -1 otherwise
     */
    public long getIdByRemoteId(long scheduleSetItemRemoteId){
        return this.scheduleSetItemDaoInstance.getIdByRemoteId(scheduleSetItemRemoteId);
    }

}
