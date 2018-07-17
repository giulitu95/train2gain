package com.train2gain.train2gain.model.enums;

import com.train2gain.train2gain.R;

public enum ScheduleStepType {

    /* Enum values */
    STANDARD_SET(1, R.string.schedule_step_type_standard_set),
    SUPER_SET(2, R.string.schedule_step_type_super_set),
    STRIPPING_SET(3, R.string.schedule_step_type_stripping_set);

    /* Enum properties */
    private int key;
    private int resourceId;

    /**
     * Builds our custom enum assigning the given properties
     * @param key the int value representing the enum we want to build
     * @param resourceId the Android string resource ID associated with a string that represents
     *                   the enum string value for the enum we want to build
     */
    ScheduleStepType(int key, int resourceId){
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
     * Retrieves the ScheduleStepType enum value corresponding to the given int value
     * @param key an int value representing a possible ScheduleStepType enum value
     * @return a ScheduleStepType enum value if the given int value represents a valid ScheduleStepType enum value
     *         NULL otherwise
     */
    public static ScheduleStepType getFromKey(int key){
        for(ScheduleStepType scheduleStepType: ScheduleStepType.values()){
            if(scheduleStepType.getKey() == key){
                return scheduleStepType;
            }
        }
        return null;
    }

}
