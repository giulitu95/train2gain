package com.train2gain.train2gain.model.enums;

import com.train2gain.train2gain.App;
import com.train2gain.train2gain.R;

public enum MuscleGroup {

    /* Enum values */
    CHEST(1, R.string.muscle_group_chest),
    TRICEPS(2, R.string.muscle_group_triceps),
    FEMORAL(3, R.string.muscle_group_femoral),
    QUADRICEPS(4, R.string.muscle_group_quadriceps),
    GLUTEUS(5, R.string.muscle_group_gluteus),
    CALF(6, R.string.muscle_group_calf),
    ABDOMINAL(7, R.string.muscle_group_abdominal),
    FOREARM(8, R.string.muscle_group_forearm),
    BICEPS(9, R.string.muscle_group_biceps),
    POSTERIOR_DELTOID(10, R.string.muscle_group_posterior_deltoid),
    DELTOID(11, R.string.muscle_group_deltoid),
    DORSAL(12, R.string.muscle_group_dorsal),
    GENERAL(13, R.string.muscle_group_general),
    LUMBAR(14, R.string.muscle_group_lumbar),
    TRAPEZIUS(15, R.string.muscle_group_trapezius);

    /* Enum properties */
    private int key;
    private int resourceId;

    /**
     * Builds our custom enum assigning the given properties
     * @param key the int value representing the enum we want to build
     * @param resourceId the Android string resource ID associated with a string that represents
     *                   the enum string value for the enum we want to build
     */
    MuscleGroup(int key, int resourceId){
        this.key = key;
        this.resourceId = resourceId;
    }

    /**
     * Returns the int value representing the enum instance
     * @return the key value associated with the enum instance
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns an Android string resource ID representing the string value associated with the enum instance
     * @return the Android string resource ID associated with the enum instance
     */
    public int getResourceId(){
        return resourceId;
    }

    /**
     * Retrieves the MuscleGroup enum value corresponding to the given int value
     * @param key an int value representing a possible MuscleGroup enum value
     * @return a MuscleGroup enum value if the given int value represents a valid MuscleGroup enum value
     *         NULL otherwise
     */
    public static MuscleGroup getFromKey(int key){
        for(MuscleGroup muscleGroup: MuscleGroup.values()){
            if(muscleGroup.getKey() == key){
                return muscleGroup;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return App.getContext().getResources().getString(this.resourceId);
    }

}
