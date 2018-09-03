package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleLoadDao;

import java.util.Iterator;
import java.util.List;

public class ScheduleLoadHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleLoadDao scheduleLoadDaoInstance;
    private final ScheduleSetItemHelper scheduleSetItemHelperInstance;
    private static ScheduleLoadHelper instance = null;

    private ScheduleLoadHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleLoadDaoInstance = localDatabase.getScheduleLoadDao();
        this.scheduleSetItemHelperInstance = localDatabase.getScheduleSetItemHelper();
    }

    /**
     * Creates a ScheduleLoadHelper instance that will help us to work with ScheduleLoad's database
     * table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleLoadHelper instance
     */
    public static ScheduleLoadHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (ScheduleLoadHelper.class){
                if(instance == null){
                    instance = new ScheduleLoadHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a ScheduleLoad into the database.
     * The schedule set item FK ID in the schedule load object must be already set.
     * @param scheduleLoad the schedule load we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(ScheduleLoad scheduleLoad){
        boolean done = false;
        if(scheduleLoad != null){
            long insertedId = this.scheduleLoadDaoInstance.insert(scheduleLoad);
            if(insertedId != -1){
                done = true;
            }
        }
        return done;
    }

    /**
     * Inserts a list of ScheduleLoads into the database.
     * If a ScheduleLoad already exists, it will not be inserted.
     * @param scheduleLoadList the list of ScheduleLoads we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleLoad> scheduleLoadList){
        boolean done = false;
        if(scheduleLoadList != null && !scheduleLoadList.isEmpty()){
            this.localDatabase.beginTransaction();
            for(Iterator<ScheduleLoad> iterator = scheduleLoadList.iterator(); iterator.hasNext();){
                ScheduleLoad scheduleLoad = iterator.next();
                if(scheduleLoad != null){
                    boolean exists = this.scheduleLoadDaoInstance
                            .checkByRemoteId(scheduleLoad.getRemoteId());
                    if(exists == true){
                        iterator.remove();
                    }else{
                        long scheduleSetItemId = this.scheduleSetItemHelperInstance
                                .getIdByRemoteId(scheduleLoad.getRemoteScheduleSetItemId());
                        if(scheduleSetItemId != -1){
                            scheduleLoad.setScheduleSetItemId(scheduleSetItemId);
                        }else{
                            scheduleLoadList = null;
                            break;
                        }
                    }
                }else{
                    scheduleLoadList = null;
                    break;
                }
            }
            if(scheduleLoadList != null && !scheduleLoadList.isEmpty()){
                long[] insertedIds = this.scheduleLoadDaoInstance.insert(scheduleLoadList);
                for(long i : insertedIds){
                    if(i == -1){
                        scheduleLoadList = null;
                        break;
                    }
                }
                if(scheduleLoadList != null){
                    this.localDatabase.setTransactionSuccessful();
                    done = true;
                }
            }else if(scheduleLoadList != null && scheduleLoadList.isEmpty()){
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the list of ScheduleLoads related to a particular Schedule
     * @param scheduleId for which Schedule we want to retrieve the list of ScheduleLoads
     * @return the list of ScheduleLoads, if some results has been returned from the database,
     *         otherwise NULL
     */
    public List<ScheduleLoad> getScheduleLoadListByScheduleId(long scheduleId){
        return this.scheduleLoadDaoInstance.getScheduleLoadListByScheduleId(scheduleId);
    }

}
