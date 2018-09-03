package com.train2gain.train2gain.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.train2gain.train2gain.model.enums.ScheduleStepType;

public class ScheduleStepTypeConverter {

    /**
     * Converts an integer value coming from the local database into a ScheduleStepType object
     * NOTE: the integer value should be one of the possible ScheduleStepType enum value's key
     * @param keyValue the integer value retrieved from the local database, that we want to convert
     * @return a ScheduleStepType enum object representing the given int value, if it is a valid key
     *         a NULL value object otherwise
     * @throws IllegalArgumentException thrown if the integer value, given as parameter, is not a
     *                                  valid ScheduleStepType enum value key
     */
    @TypeConverter
    public ScheduleStepType scheduleStepTypeFromInt(int keyValue) throws IllegalArgumentException{
        ScheduleStepType scheduleStepType = ScheduleStepType.getFromKey(keyValue);
        if(scheduleStepType != null){
            return scheduleStepType;
        }else{
            throw new IllegalArgumentException("DB scheduleStepTypeFromInt : Invalid schedule " +
                    "step type key value.");
        }
    }

    /**
     * Converts a ScheduleStepType enum object into an integer value, that represents the enum's key
     * This integer value then will be stored in the local database
     * @param scheduleStepType the ScheduleStepType enum we want to convert into an integer value
     * @return an integer value representing the key of the ScheduleStepType enum, if it is not NULL
     * @throws IllegalArgumentException thrown if the ScheduleStepType enum, given as parameter, is NULL
     */
    @TypeConverter
    public int scheduleStepTypeToInt(ScheduleStepType scheduleStepType) throws IllegalArgumentException {
        if(scheduleStepType != null){
            return scheduleStepType.getKey();
        }else{
            throw new IllegalArgumentException("DB scheduleStepTypeToInt : Invalid schedule step " +
                    "type. Schedule step type object can not be NULL.");
        }
    }

}
