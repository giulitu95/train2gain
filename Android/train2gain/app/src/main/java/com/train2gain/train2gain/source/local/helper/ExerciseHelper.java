package com.train2gain.train2gain.source.local.helper;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.dao.ExerciseDao;

import java.util.ArrayList;
import java.util.List;

public class ExerciseHelper {

    private final LocalDatabase localDatabase;
    private final ExerciseDao exerciseDaoInstance;
    private static ExerciseHelper instance = null;

    private ExerciseHelper(@NonNull LocalDatabase localDatabase){
        this.localDatabase = localDatabase;
        this.exerciseDaoInstance = localDatabase.getExerciseDao();
    }

    /**
     * Creates an ExerciseHelper instance that will help us to work with Exercise's database table
     * If the instance had already been created previously, it simply returns it
     * @param localDatabase a local database instance used to retrieve Dao implementations
     *                      and other DB related things
     * @return an ExerciseHelper instance
     */
    public static ExerciseHelper getInstance(@NonNull LocalDatabase localDatabase){
        if(instance == null){
            synchronized (ExerciseHelper.class){
                if(instance == null){
                    instance = new ExerciseHelper(localDatabase);
                }
            }
        }
        return instance;
    }

    /**
     * Inserts a list of Exercises into the database. For all of those that already exist
     * in it, the method attempts to update their rows in the database
     * @param exerciseList the list of Exercises we want to insert into the database
     * @return true if insert / update operations have been executed successfully
     *         false otherwise
     */
    public boolean insertOrUpdateIfExists(List<Exercise> exerciseList){
        boolean done = false;
        if(exerciseList != null && !exerciseList.isEmpty()){
            this.localDatabase.beginTransaction();
            long[] insertedIds = this.exerciseDaoInstance.insert(exerciseList);
            List<Exercise> notInsertedExerciseList = new ArrayList<Exercise>();
            for(int i = 0; i < insertedIds.length; i++){
                if(insertedIds[i] == -1){
                    notInsertedExerciseList.add(exerciseList.get(i));
                }
            }
            if(!notInsertedExerciseList.isEmpty()){
                int updatedRows = this.exerciseDaoInstance.update(notInsertedExerciseList);
                if(updatedRows == notInsertedExerciseList.size()){
                    done = true;
                }
            }else{
                done = true;
            }
            if(done == true){
                this.localDatabase.setTransactionSuccessful();
            }
            this.localDatabase.endTransaction();
        }
        return done;
    }

    /**
     * Retrieves the Exercise that has the given exercise's ID
     * @param exerciseId the ID of the Exercise we want to retrieve
     * @return an Exercise object whose ID is the one given as parameter, if some results
     *         has been returned from the database, otherwise NULL
     */
    public Exercise getById(long exerciseId){
        return this.exerciseDaoInstance.getById(exerciseId);
    }

    /**
     * Retrieves all the Exercises stored in the database
     * @return the list of Exercises stored in the database, if some results has been
     *         returned from the database, otherwise NULL
     */
    public List<Exercise> getAll(){
        return this.exerciseDaoInstance.getAll();
    }

}
