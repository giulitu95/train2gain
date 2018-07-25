package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleSetDao;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSetHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleSetDao scheduleSetDaoImpl;
    private final ScheduleSetItemHelper scheduleSetItemHelperInstance;
    private static ScheduleSetHelper instance = null;

    private ScheduleSetHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleSetDaoImpl = localDatabase.getScheduleSetDao();
        this.scheduleSetItemHelperInstance = localDatabase.getScheduleSetItemHelper();
    }

    /**
     * Creates a ScheduleSetHelper instance that will help us to work with ScheduleSet's
     * database table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleSetHelper instance
     */
    public static ScheduleSetHelper getInstance(@NonNull LocalDatabase localDatabase){
        if(instance == null){
            synchronized (ScheduleSetHelper.class) {
                if(instance == null){
                    instance = new ScheduleSetHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a list of ScheduleSets into the database. The method also attempts to insert, for
     * each ScheduleSet, the related ScheduleSetItem list.
     * @param scheduleSetList the list of ScheduleSets we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleSet> scheduleSetList) {
        boolean done = false;
        if(scheduleSetList != null && !scheduleSetList.isEmpty()){
            this.localDatabase.beginTransaction();
            long[] scheduleSetIds = this.scheduleSetDaoImpl.insert(scheduleSetList);
            List<ScheduleSetItem> scheduleSetItemList = new ArrayList<ScheduleSetItem>();
            for(int i=0; (i<scheduleSetIds.length) && (scheduleSetItemList !=null); i++){
                if(scheduleSetIds[i] != -1){
                    ScheduleSet scheduleSet = scheduleSetList.get(i);
                    List<ScheduleSetItem> tmpScheduleSetItemList = scheduleSet.getScheduleSetItemList();
                    if(tmpScheduleSetItemList != null && !tmpScheduleSetItemList.isEmpty()){
                        for(ScheduleSetItem scheduleSetItem : tmpScheduleSetItemList){
                            if(scheduleSetItem != null){
                                scheduleSetItem.setScheduleSetId(scheduleSetIds[i]);
                                scheduleSetItemList.add(scheduleSetItem);
                            }else{
                                scheduleSetItemList = null;
                                break;
                            }
                        }
                    }else{
                        scheduleSetItemList = null;
                    }
                }else{
                    scheduleSetItemList = null;
                }
            }
            boolean inserted = this.scheduleSetItemHelperInstance.insert(scheduleSetItemList);
            if(inserted == true){
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the list of ScheduleSets related to a particular ScheduleStep, given as param
     * It retrieves also, for each ScheduleSet, the related ScheduleSetItem list
     * @param scheduleStepId for which ScheduleStep we want to retrieve the list of ScheduleSets
     * @return the list of ScheduleSets, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public List<ScheduleSet> getScheduleSetListByScheduleStepId(long scheduleStepId){
        this.localDatabase.beginTransaction();
        List<ScheduleSet> scheduleSetList = this.scheduleSetDaoImpl
                .getScheduleSetListByScheduleStepId(scheduleStepId);
        if(scheduleSetList != null){
            for(ScheduleSet scheduleSet : scheduleSetList){
                List<ScheduleSetItem> scheduleSetItemList = this.scheduleSetItemHelperInstance
                        .getScheduleSetItemListByScheduleSetId(scheduleSet.getId());
                if(scheduleSetItemList != null){
                    scheduleSet.setScheduleSetItemList(scheduleSetItemList);
                }else{
                    scheduleSetList = null;
                    break;
                }
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return scheduleSetList;
    }

}
