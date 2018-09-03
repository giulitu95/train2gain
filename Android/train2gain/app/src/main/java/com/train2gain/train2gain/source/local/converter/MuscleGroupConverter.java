package com.train2gain.train2gain.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.train2gain.train2gain.model.enums.MuscleGroup;

public class MuscleGroupConverter {

    /**
     * Converts an integer value coming from the local database into a MuscleGroup object
     * NOTE: the integer value should be one of the possible MuscleGroup enum value's key
     * @param keyValue the integer value retrieved from the local database, that we want to convert
     * @return a MuscleGroup enum object representing the given int value, if it is a valid key
     *         a NULL value object otherwise
     * @throws IllegalArgumentException thrown if the integer value, given as parameter, is not a
     *                                  valid MuscleGroup enum value key
     */
    @TypeConverter
    public MuscleGroup muscleGroupFromInt(int keyValue) throws IllegalArgumentException {
        MuscleGroup muscleGroup = MuscleGroup.getFromKey(keyValue);
        if(muscleGroup != null){
            return muscleGroup;
        }else{
            throw new IllegalArgumentException("DB muscleGroupFromInt : Invalid muscle group " +
                    "key value.");
        }
    }

    /**
     * Converts a MuscleGroup enum object into an integer value, that represents the enum's key
     * This integer value then will be stored in the local database
     * @param muscleGroup the MuscleGroup enum we want to convert into an integer value
     * @return an integer value representing the key of the MuscleGroup enum, if it is not NULL
     * @throws IllegalArgumentException thrown if the MuscleGroup enum, given as parameter, is NULL
     */
    @TypeConverter
    public int muscleGroupToInt(MuscleGroup muscleGroup) throws IllegalArgumentException{
        if(muscleGroup != null){
            return muscleGroup.getKey();
        }else{
            throw new IllegalArgumentException("DB muscleGroupToInt : Invalid muscle group. " +
                    "Muscle group object can not be NULL.");
        }
    }

}
