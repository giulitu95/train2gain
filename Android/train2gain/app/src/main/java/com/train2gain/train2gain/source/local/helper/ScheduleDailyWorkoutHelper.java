package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleDailyWorkoutDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScheduleDailyWorkoutHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleDailyWorkoutDao scheduleDailyWorkoutDaoInstance;
    private final ScheduleStepHelper scheduleStepHelperInstance;
    private static ScheduleDailyWorkoutHelper instance = null;

    private ScheduleDailyWorkoutHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleDailyWorkoutDaoInstance = localDatabase.getScheduleDailyWorkoutDao();
        this.scheduleStepHelperInstance = localDatabase.getScheduleStepHelper();
    }

    /**
     * Creates a ScheduleDailyWorkoutHelper instance that will help us to work with
     * ScheduleDailyWorkout's database table. If the instance had already been created previously,
     * it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleDailyWorkoutHelper instance
     */
    public static ScheduleDailyWorkoutHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (ScheduleDailyWorkoutHelper.class) {
                if(instance == null){
                    instance = new ScheduleDailyWorkoutHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a list of ScheduleDailyWorkouts into the database. If a ScheduleDailyWorkout
     * already exists, it will not be inserted. The method also attempts to insert, for each
     * ScheduleDailyWorkout, the related ScheduleStep list.
     * @param scheduleDailyWorkoutList the list of ScheduleDailyWorkouts we want to insert into
     *                                 the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleDailyWorkout> scheduleDailyWorkoutList){
        boolean done = false;
        if(scheduleDailyWorkoutList != null && !scheduleDailyWorkoutList.isEmpty()){
            this.localDatabase.beginTransaction();
            for(Iterator<ScheduleDailyWorkout> iterator = scheduleDailyWorkoutList.iterator();
                iterator.hasNext();){
                ScheduleDailyWorkout scheduleDailyWorkout = iterator.next();
                if(scheduleDailyWorkout != null && scheduleDailyWorkout.getRemoteId() != -1){
                    boolean isPresent = this.scheduleDailyWorkoutDaoInstance
                            .checkByRemoteId(scheduleDailyWorkout.getRemoteId());
                    if(isPresent == true){
                        iterator.remove();
                    }
                }
            }
            if(!scheduleDailyWorkoutList.isEmpty()){
                long[] scheduleDailyWorkoutIds = this.scheduleDailyWorkoutDaoInstance
                        .insert(scheduleDailyWorkoutList);
                List<ScheduleStep> scheduleStepList = new ArrayList<ScheduleStep>();
                for(int i=0; (i<scheduleDailyWorkoutIds.length) && (scheduleStepList != null); i++){
                    if(scheduleDailyWorkoutIds[i] != -1){
                        ScheduleDailyWorkout scheduleDailyWorkout = scheduleDailyWorkoutList.get(i);
                        List<ScheduleStep> tmpScheduleStepList = scheduleDailyWorkout.getScheduleStepList();
                        if(tmpScheduleStepList != null && !tmpScheduleStepList.isEmpty()){
                            for(ScheduleStep scheduleStep : tmpScheduleStepList) {
                                if (scheduleStep != null) {
                                    scheduleStep.setScheduleDailyWorkoutId(scheduleDailyWorkoutIds[i]);
                                    scheduleStepList.add(scheduleStep);
                                } else {
                                    scheduleStepList = null;
                                    break;
                                }
                            }
                        }else{
                            scheduleStepList = null;
                        }
                    }else{
                        scheduleStepList = null;
                    }
                }
                boolean inserted = this.scheduleStepHelperInstance.insert(scheduleStepList);
                if(inserted == true){
                    this.localDatabase.setTransactionSuccessful();
                    done = true;
                }
            }else{
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Updates the date (last time) when the ScheduleDailyWorkout, given as param, was executed
     * @param lastExecutionDate the date (last time) when the ScheduleDailyWorkout was executed
     * @param scheduleDailyWorkoutId the DailyWorkout we want to update with the new last
     *                               execution's date
     * @return true if update operations have been executed successfully
     *         false otherwise
     */
    public boolean updateScheduleDailyWorkoutLastExecutionDate(Date lastExecutionDate,
                                                               long scheduleDailyWorkoutId){
        boolean done = false;
        if(lastExecutionDate != null){
            this.localDatabase.beginTransaction();
            int updatedRows = this.scheduleDailyWorkoutDaoInstance
                    .updateScheduleDailyWorkoutLastExecutionDate(lastExecutionDate,
                            scheduleDailyWorkoutId);
            if(updatedRows == 1){
                done = true;
                this.localDatabase.setTransactionSuccessful();
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the ScheduleDailyWorkout that has the given schedule daily workout's ID
     * It retrieves also the nested ScheduleSetItem list object
     * @param scheduleDailyWorkoutId the ID of the ScheduleDailyWorkout we want to retrieve
     * @return the ScheduleDailyWorkout, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public ScheduleDailyWorkout getById(long scheduleDailyWorkoutId){
        this.localDatabase.beginTransaction();
        ScheduleDailyWorkout scheduleDailyWorkout = this.scheduleDailyWorkoutDaoInstance
                .getById(scheduleDailyWorkoutId);
        if(scheduleDailyWorkout != null){
            List<ScheduleStep> scheduleStepList = this.scheduleStepHelperInstance
                    .getScheduleStepListByScheduleDailyWorkoutId(scheduleDailyWorkoutId);
            if(scheduleStepList != null && !scheduleStepList.isEmpty()){
                scheduleDailyWorkout.setScheduleStepList(scheduleStepList);
            }else{
                scheduleDailyWorkout = null;
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleDailyWorkout;
    }

    /**
     * Retrieves the list of ScheduleDailyWorkouts related to a particular Schedule, given as
     * param. It retrieves also, for each ScheduleDailyWorkout, the related ScheduleStep list
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleDailyWorkouts
     * @return the list of ScheduleDailyWorkouts, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public List<ScheduleDailyWorkout> getScheduleDailyWorkoutListByScheduleId(long scheduleId){
        this.localDatabase.beginTransaction();
        List<ScheduleDailyWorkout> scheduleDailyWorkoutList = this.scheduleDailyWorkoutDaoInstance
                .getScheduleDailyWorkoutListByScheduleId(scheduleId);
        if(scheduleDailyWorkoutList != null){
            for(ScheduleDailyWorkout scheduleDailyWorkout : scheduleDailyWorkoutList){
                List<ScheduleStep> scheduleStepList = this.scheduleStepHelperInstance
                        .getScheduleStepListByScheduleDailyWorkoutId(scheduleDailyWorkout.getId());
                if(scheduleStepList != null){
                    scheduleDailyWorkout.setScheduleStepList(scheduleStepList);
                }else{
                    scheduleDailyWorkoutList = null;
                    break;
                }
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleDailyWorkoutList;
    }

    /**
     * Retrieves the list of ScheduleDailyWorkouts related to a particular Schedule
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleDailyWorkouts
     * @return the list of ScheduleDailyWorkouts, if some results has been returned from the
     *         database, otherwise NULL
     */
    public List<ScheduleDailyWorkout> getScheduleDailyWorkoutListByScheduleIdMinimal(long scheduleId){
        return this.scheduleDailyWorkoutDaoInstance.getScheduleDailyWorkoutListByScheduleId(scheduleId);
    }

    /**
     * Retrieves the ScheduleDailyWorkout that was executed less recently.
     * @param scheduleId for which schedule we want to retrieve the ScheduleDailyWorkout that was
     *                   executed less recently
     * @return the ScheduleDailyWorkout that was executed less recently, if some results has been
     *         returned from the database, otherwise NULL
     */
    public ScheduleDailyWorkout getScheduleDailyWorkoutOfTheDayByScheduleIdMinimal(long scheduleId){
        return this.scheduleDailyWorkoutDaoInstance.getScheduleDailyWorkoutOfTheDayByScheduleId(scheduleId);
    }

    /**
     * Retrieves the ScheduleDailyWorkout that was executed less recently.
     * It retrieves also, for the ScheduleDailyWorkout, the related ScheduleStep list
     * @param scheduleId for which schedule we want to retrieve the ScheduleDailyWorkout that was
     *                   executed less recently
     * @return the ScheduleDailyWorkout that was executed less recently, if some results has been
     *         returned from the database, otherwise NULL
     */
    public ScheduleDailyWorkout getScheduleDailyWorkoutOfTheDayByScheduleId(long scheduleId){
        this.localDatabase.beginTransaction();
        ScheduleDailyWorkout scheduleDailyWorkout = this.scheduleDailyWorkoutDaoInstance
                .getScheduleDailyWorkoutOfTheDayByScheduleId(scheduleId);
        if(scheduleDailyWorkout != null){
            List<ScheduleStep> scheduleStepList = this.scheduleStepHelperInstance
                    .getScheduleStepListByScheduleDailyWorkoutId(scheduleDailyWorkout.getId());
            if(scheduleStepList != null && !scheduleStepList.isEmpty()){
                scheduleDailyWorkout.setScheduleStepList(scheduleStepList);
            }else{
                scheduleDailyWorkout = null;
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleDailyWorkout;
    }

    /**
     * Retrieves the ID of the ScheduleDailyWorkout that has the given schedule daily workout's
     * remote ID
     * @param scheduleDailyWorkoutRemoteId the remote ID of the schedule daily workout for which
     *                                    we want the ID
     * @return the ScheduleDailyWorkout ID, if some result has been returned from the database
     *         -1 otherwise
     */
    public long getIdByRemoteId(long scheduleDailyWorkoutRemoteId){
        return this.scheduleDailyWorkoutDaoInstance.getIdByRemoteId(scheduleDailyWorkoutRemoteId);
    }

}
