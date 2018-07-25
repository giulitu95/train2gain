package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleNoteDao;

import java.util.List;

public class ScheduleNoteHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleNoteDao scheduleNoteDaoInstance;
    private final ScheduleStepHelper scheduleStepHelperInstance;
    private static ScheduleNoteHelper instance = null;

    private ScheduleNoteHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleNoteDaoInstance = localDatabase.getScheduleNoteDao();
        this.scheduleStepHelperInstance = localDatabase.getScheduleStepHelper();
    }

    /**
     * Creates a ScheduleNoteHelper instance that will help us to work with ScheduleNote's database
     * table. If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleNoteHelper instance
     */
    public static ScheduleNoteHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (ScheduleNoteHelper.class) {
                if(instance == null){
                    instance = new ScheduleNoteHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a ScheduleNote into the database.
     * The schedule step FK ID in the schedule note object must be already set
     * @param scheduleNote the schedule note we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(ScheduleNote scheduleNote){
        boolean done = false;
        if(scheduleNote != null){
            long insertedId = this.scheduleNoteDaoInstance.insert(scheduleNote);
            if(insertedId == -1){
                done = true;
            }
        }
        return done;
    }

    /**
     * Inserts a list of ScheduleNotes into the database.
     * If a ScheduleNote already exists, it will not be inserted.
     * @param scheduleNoteList the list of ScheduleNotes we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(List<ScheduleNote> scheduleNoteList){
        boolean done = false;
        if(scheduleNoteList != null && !scheduleNoteList.isEmpty()){
            this.localDatabase.beginTransaction();
            for(ScheduleNote scheduleNote : scheduleNoteList){
                if(scheduleNote != null){
                    boolean exists = this.scheduleNoteDaoInstance
                            .checkByRemoteId(scheduleNote.getRemoteId());
                    if(exists == true){
                        scheduleNoteList.remove(scheduleNote);
                    }else {
                        long scheduleStepId = this.scheduleStepHelperInstance
                                .getIdByRemoteId(scheduleNote.getRemoteScheduleStepId());
                        if (scheduleStepId != -1) {
                            scheduleNote.setScheduleStepId(scheduleStepId);
                        } else {
                            scheduleNoteList = null;
                            break;
                        }
                    }
                }else{
                    scheduleNoteList = null;
                    break;
                }
            }
            if(scheduleNoteList != null && !scheduleNoteList.isEmpty()){
                long[] insertedIds = this.scheduleNoteDaoInstance.insert(scheduleNoteList);
                for(long i : insertedIds){
                    if(i == -1){
                        scheduleNoteList = null;
                        break;
                    }
                }
                if(scheduleNoteList != null){
                    this.localDatabase.setTransactionSuccessful();
                    done = true;
                }
            }else if(scheduleNoteList != null && scheduleNoteList.isEmpty()){
                this.localDatabase.setTransactionSuccessful();
                done = true;
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the list of ScheduleNotes related to a particular ScheduleStep
     * @param scheduleStepId for which ScheduleStep we want to retrieve the list of ScheduleNotes
     * @return the list of ScheduleNotes, if some results has been returned from the database,
     *         otherwise NULL
     */
    public List<ScheduleNote> getScheduleNoteListByScheduleStepId(long scheduleStepId){
        return this.scheduleNoteDaoInstance.getScheduleNoteListByScheduleStepId(scheduleStepId);
    }

}
