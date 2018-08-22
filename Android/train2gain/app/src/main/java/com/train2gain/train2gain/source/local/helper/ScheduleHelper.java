package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ScheduleDao;

import java.util.List;

public class ScheduleHelper {

    private final LocalDatabase localDatabase;
    private final ScheduleDao scheduleDaoImpl;
    private final ScheduleDailyWorkoutHelper scheduleDailyWorkoutHelperInstance;
    private static ScheduleHelper instance = null;

    private ScheduleHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.scheduleDaoImpl = localDatabase.getScheduleDao();
        this.scheduleDailyWorkoutHelperInstance = localDatabase.getScheduleDailyWorkoutHelper();
    }

    /**
     * Creates a ScheduleHelper instance that will help us to work with Schedule's database table.
     * If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return a ScheduleHelper instance
     */
    public static ScheduleHelper getInstance(@NonNull LocalDatabase localDatabase) {
        if(instance == null){
            synchronized (ScheduleHelper.class){
                if(instance == null){
                    instance = new ScheduleHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a Schedule into the database. If a Schedule already exists, it will not be inserted.
     * The method also attempts to insert the related ScheduleDailyWorkout list.
     * @param schedule the Schedule we want to insert into the database
     * @return true if insert operations have been executed successfully
     *         false otherwise
     */
    public boolean insert(Schedule schedule){
        boolean done = false;
        if(schedule != null){
            this.localDatabase.beginTransaction();
            // Check if schedule is into the database
            boolean isPresent = false;
            if(schedule.getRemoteId() != -1){
                isPresent = this.scheduleDaoImpl.checkByRemoteId(schedule.getRemoteId());
            }
            // Insert schedule (if not already exists) and get its ID
            long scheduleId = -1;
            if(isPresent == false){
                scheduleId = this.scheduleDaoImpl.insert(schedule);
            }else{
                scheduleId = this.scheduleDaoImpl.getIdByRemoteId(schedule.getRemoteId());
            }
            // Insert DailyWorkouts
            if(scheduleId != -1){
                List<ScheduleDailyWorkout> scheduleDailyWorkoutList = schedule.getScheduleDailyWorkoutList();
                if(scheduleDailyWorkoutList != null && !scheduleDailyWorkoutList.isEmpty()){
                    for(ScheduleDailyWorkout scheduleDailyWorkout : scheduleDailyWorkoutList){
                        if(scheduleDailyWorkout != null){
                            scheduleDailyWorkout.setScheduleId(scheduleId);
                        }else{
                            scheduleDailyWorkoutList = null;
                            break;
                        }
                    }
                }
                boolean inserted = this.scheduleDailyWorkoutHelperInstance.insert(scheduleDailyWorkoutList);
                if(inserted == true){
                    this.localDatabase.setTransactionSuccessful();
                    done = true;
                }
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Inserts a list of Schedules into the database.
     * If a Schedule already exists, it will not be inserted.
     * @param scheduleList the list of Schedules we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertListMinimal(List<Schedule> scheduleList){
        boolean done = false;
        if(scheduleList != null && !scheduleList.isEmpty()){
            this.localDatabase.beginTransaction();
            for(Schedule schedule : scheduleList){
                if(schedule != null){
                    boolean exists = this.scheduleDaoImpl.checkByRemoteId(schedule.getRemoteId());
                    if(exists == true){
                        scheduleList.remove(schedule);
                    }
                }
            }
            if(!scheduleList.isEmpty()){
                long[] insertedIds = this.scheduleDaoImpl.insert(scheduleList);
                boolean foundInvalidId = false;
                for(long i : insertedIds){
                    if(i == -1){
                        foundInvalidId = true;
                        break;
                    }
                }
                if(!foundInvalidId){
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
     * Retrieves the Schedule that has the given schedule's ID
     * It retrieves also the nested ScheduleDailyWorkout list object
     * @param scheduleId the ID of the Schedule we want to retrieve
     * @return the Schedule, if all the results have been retrieved correctly
     *         NULL otherwise
     */
    public Schedule getById(long scheduleId){
        this.localDatabase.beginTransaction();
        Schedule schedule = this.scheduleDaoImpl.getById(scheduleId);
        if(schedule != null){
            List<ScheduleDailyWorkout> scheduleDailyWorkoutList =
                    this.scheduleDailyWorkoutHelperInstance
                            .getScheduleDailyWorkoutListByScheduleId(scheduleId);
            if(scheduleDailyWorkoutList != null && !scheduleDailyWorkoutList.isEmpty()){
                schedule.setScheduleDailyWorkoutList(scheduleDailyWorkoutList);
            }else{
                schedule = null;
            }
        }
        this.localDatabase.setTransactionSuccessful();
        this.localDatabase.endTransaction();
        return schedule;
    }

    /**
     * Retrieves the list of Schedules created by a Trainer for a particular Athlete user
     * @param athleteUserId for which athlete user we want to retrieves the schedules
     * @param trainerUserId the trainer who creates the schedules we want to retrieve
     * @return the list of Schedules, if some results has been returned from the database,
     *         otherwise NULL
     */
    public List<Schedule> getByAthleteAndTrainerUserIdMinimal(long athleteUserId, long trainerUserId){
        return this.scheduleDaoImpl.getByAthleteAndTrainerUserId(athleteUserId, trainerUserId);
    }

    /**
     * Retrieves the current schedule (current active schedule) for the given athlete
     * @param athleteUserId for which athlete user we want to retrieve the schedule
     * @return the current athlete's schedule, if some results has been returned from the database,
     *         NULL otherwise
     */
    public Schedule getCurrentByAthleteUserId(long athleteUserId){
        long currentScheduleId = this.scheduleDaoImpl.getCurrentScheduleIdByAthleteUserId(athleteUserId);
        return getById(currentScheduleId);
    }

}
