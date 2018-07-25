package com.train2gain.train2gain.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.model.entity.Gym;
import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.model.entity.ScheduleDailyWorkout;
import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.model.entity.ScheduleSet;
import com.train2gain.train2gain.model.entity.ScheduleSetItem;
import com.train2gain.train2gain.model.entity.ScheduleStep;
import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.model.entity.TrainingHistory;
import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.source.local.converter.DateConverter;
import com.train2gain.train2gain.source.local.dao.AthleteDao;
import com.train2gain.train2gain.source.local.dao.ExerciseDao;
import com.train2gain.train2gain.source.local.dao.GymDao;
import com.train2gain.train2gain.source.local.dao.ScheduleDailyWorkoutDao;
import com.train2gain.train2gain.source.local.dao.ScheduleDao;
import com.train2gain.train2gain.source.local.dao.ScheduleLoadDao;
import com.train2gain.train2gain.source.local.dao.ScheduleNoteDao;
import com.train2gain.train2gain.source.local.dao.ScheduleSetDao;
import com.train2gain.train2gain.source.local.dao.ScheduleSetItemDao;
import com.train2gain.train2gain.source.local.dao.ScheduleStepDao;
import com.train2gain.train2gain.source.local.dao.TrainerDao;
import com.train2gain.train2gain.source.local.dao.TrainingHistoryDao;
import com.train2gain.train2gain.source.local.dao.UserDao;
import com.train2gain.train2gain.source.local.helper.AthleteHelper;
import com.train2gain.train2gain.source.local.helper.ExerciseHelper;
import com.train2gain.train2gain.source.local.helper.GymHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleDailyWorkoutHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleLoadHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleNoteHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleSetHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleSetItemHelper;
import com.train2gain.train2gain.source.local.helper.ScheduleStepHelper;
import com.train2gain.train2gain.source.local.helper.TrainerHelper;
import com.train2gain.train2gain.source.local.helper.TrainingHistoryHelper;
import com.train2gain.train2gain.source.local.helper.UserHelper;

@Database(
    entities = {
        Athlete.class,
        Exercise.class,
        Gym.class,
        Schedule.class,
        ScheduleDailyWorkout.class,
        ScheduleLoad.class,
        ScheduleNote.class,
        ScheduleSet.class,
        ScheduleSetItem.class,
        ScheduleStep.class,
        Trainer.class,
        TrainingHistory.class,
        User.class
    },
    version = 1
)
@TypeConverters({
    DateConverter.class
})
public abstract class LocalDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "train2gain_db";
    private static LocalDatabase instance = null;

    /**
     * Creates a custom instance of Room database (if not already exists) and returns it
     * @param context
     * @return a custom Room database instance
     */
    public static LocalDatabase getInstance(final Context context) {
        if(instance == null){
            synchronized (LocalDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }


    // DAO GETTERS

    public abstract AthleteDao getAthleteDao();
    public abstract ExerciseDao getExerciseDao();
    public abstract GymDao getGymDao();
    public abstract ScheduleDao getScheduleDao();
    public abstract ScheduleDailyWorkoutDao getScheduleDailyWorkoutDao();
    public abstract ScheduleLoadDao getScheduleLoadDao();
    public abstract ScheduleNoteDao getScheduleNoteDao();
    public abstract ScheduleSetDao getScheduleSetDao();
    public abstract ScheduleSetItemDao getScheduleSetItemDao();
    public abstract ScheduleStepDao getScheduleStepDao();
    public abstract TrainerDao getTrainerDao();
    public abstract TrainingHistoryDao getTrainingHistoryDao();
    public abstract UserDao getUserDao();


    // HELPER GETTERS

    public AthleteHelper getAthleteHelper(){
        return AthleteHelper.getInstance(this);
    }
    public ExerciseHelper getExerciseHelper() {
        return ExerciseHelper.getInstance(this);
    }
    public GymHelper getGymHelper(){
        return GymHelper.getInstance(this);
    }
    public ScheduleHelper getScheduleHelper(){
        return ScheduleHelper.getInstance(this);
    }
    public ScheduleDailyWorkoutHelper getScheduleDailyWorkoutHelper(){
        return ScheduleDailyWorkoutHelper.getInstance(this);
    }
    public ScheduleLoadHelper getScheduleLoadHelper(){
        return ScheduleLoadHelper.getInstance(this);
    }
    public ScheduleNoteHelper getScheduleNoteHelper(){
        return ScheduleNoteHelper.getInstance(this);
    }
    public ScheduleSetHelper getScheduleSetHelper(){
        return ScheduleSetHelper.getInstance(this);
    }
    public ScheduleSetItemHelper getScheduleSetItemHelper(){
        return ScheduleSetItemHelper.getInstance(this);
    }
    public ScheduleStepHelper getScheduleStepHelper(){
        return ScheduleStepHelper.getInstance(this);
    }
    public TrainerHelper getTrainerHelper(){
        return TrainerHelper.getInstance(this);
    }
    public TrainingHistoryHelper getTrainingHistoryHelper(){
        return TrainingHistoryHelper.getInstance(this);
    }
    public UserHelper getUserHelper(){
        return UserHelper.getInstance(this);
    }

}
