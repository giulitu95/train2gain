package com.train2gain.train2gain.model.enums;

import com.train2gain.train2gain.R;

public enum UserType {

    /* Enum values */
    ATHLETE(1, R.string.user_type_athlete),
    TRAINER(2, R.string.user_type_trainer),
    BOTH(3, R.string.user_type_both);

    /* Enum properties */
    private int key;
    private int resourceId;

    /**
     * Builds our custom enum assigning the given properties
     * @param key the int value representing the enum we want to build
     * @param resourceId the Android string resource ID associated with a string that represents
     *                   the enum string value for the enum we want to build
     */
    UserType(int key, int resourceId){
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
     * Retrieves the UserType enum value corresponding to the given int value
     * @param key an int value representing a possible UserType enum value
     * @return an UserType enum value if the given int value represents a valid UserType enum value
     *         NULL otherwise
     */
    public static UserType getFromKey(int key){
        for(UserType userType: UserType.values()){
            if(userType.getKey() == key){
                return userType;
            }
        }
        return null;
    }

}
