package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleStepDao;

import java.util.ArrayList;
import java.util.List;

public class ScheduleStepHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleStepDao scheduleStepDaoInstance;
    private final ScheduleSetHelper scheduleSetHelperInstance;
    private static ScheduleStepHelper instance = null;

    private ScheduleStepHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleStepDaoInstance = localDatabase.getScheduleStepDao();
        this.scheduleSetHelperInstance = localDatabase.getScheduleSetHelper();
    }

    /**
     * Creates a ScheduleStepHelper instance that will help us to work with ScheduleStep's
     * database table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleStepHelper instance
     */
    public static ScheduleStepHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (ScheduleStepHelper.class){
                if(instance == null){
                    instance = new ScheduleStepHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a list of ScheduleSteps into the database. The method also attempts to insert, for
     * each ScheduleStep, the related ScheduleSet list.
     * @param scheduleStepList the list of ScheduleSteps we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleStep> scheduleStepList){
        boolean done = false;
        if(scheduleStepList != null && !scheduleStepList.isEmpty()){
            this.localDatabase.beginTransaction();
            long[] scheduleStepIds = this.scheduleStepDaoInstance.insert(scheduleStepList);
            List<ScheduleSet> scheduleSetList = new ArrayList<ScheduleSet>();
            for(int i=0; (i<scheduleStepIds.length) && (scheduleSetList !=null); i++){
                if(scheduleStepIds[i] != -1){
                    ScheduleStep scheduleStep = scheduleStepList.get(i);
                    List<ScheduleSet> tmpScheduleSetList = scheduleStep.getScheduleSetList();
                    if(tmpScheduleSetList != null && !tmpScheduleSetList.isEmpty()){
                        for(ScheduleSet scheduleSet : tmpScheduleSetList){
                            if(scheduleSet != null){
                                scheduleSet.setScheduleStepId(scheduleStepIds[i]);
                                scheduleSetList.add(scheduleSet);
                            }else{
                                scheduleSetList = null;
                                break;
                            }
                        }
                    }else{
                        scheduleSetList = null;
                    }
                }else{
                    scheduleSetList = null;
                }
            }
            boolean inserted = this.scheduleSetHelperInstance.insert(scheduleSetList);
            if(inserted == true){
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the list of ScheduleSteps related to a particular ScheduleDailyWorkout, given as
     * param. It retrieves also, for each ScheduleStep, the related ScheduleSet list
     * @param scheduleDailyWorkoutId for which ScheduleDailyWorkout we want to retrieve the list
     *                               of ScheduleSteps
     * @return the list of ScheduleSteps, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public List<ScheduleStep> getScheduleStepListByScheduleDailyWorkoutId(long scheduleDailyWorkoutId){
        this.localDatabase.beginTransaction();
        List<ScheduleStep> scheduleStepList = this.scheduleStepDaoInstance
                .getScheduleStepListByScheduleDailyWorkoutId(scheduleDailyWorkoutId);
        if(scheduleStepList != null){
            for(ScheduleStep scheduleStep : scheduleStepList){
                List<ScheduleSet> scheduleSetList = this.scheduleSetHelperInstance
                        .getScheduleSetListByScheduleStepId(scheduleStep.getId());
                if(scheduleSetList != null){
                    scheduleStep.setScheduleSetList(scheduleSetList);
                }else{
                    scheduleStepList = null;
                    break;
                }
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleStepList;
    }

    /**
     * Retrieves the ID of the ScheduleStep that has the given schedule step's remote ID
     * @param scheduleNoteRemoteId the remote ID of the schedule step for which we want the ID
     * @return the ScheduleNote ID, if some result has been returned from the database, otherwise -1
     */
    public long getIdByRemoteId(long scheduleNoteRemoteId){
        return this.scheduleStepDaoInstance.getIdByRemoteId(scheduleNoteRemoteId);
    }

}
