package com.train2gain.train2gain.source.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.train2gain.train2gain.model.enums.UserType;

public class UserTypeConverter {

    /**
     * Converts an integer value coming from the local database into a UserType object
     * NOTE: the integer value should be one of the possible UserType enum value's key
     * @param keyValue the integer value retrieved from the local database, that we want to convert
     * @return a UserType enum object representing the given int value, if it is a valid key
     *         a NULL value object otherwise
     * @throws IllegalArgumentException thrown if the integer value, given as parameter, is not a
     *                                  valid UserType enum value key
     */
    @TypeConverter
    public UserType userTypeFromInt(int keyValue) throws IllegalArgumentException{
        UserType userType = UserType.getFromKey(keyValue);
        if(userType != null){
            return userType;
        }else{
            throw new IllegalArgumentException("DB userTypeFromInt : Invalid user type key value.");
        }
    }

    /**
     * Converts a UserType enum object into an integer value, that represents the enum's key
     * This integer value then will be stored in the local database
     * @param userType the UserType enum we want to convert into an integer value
     * @return an integer value representing the key of the UserType enum, if it is not NULL
     * @throws IllegalArgumentException thrown if the UserType enum, given as parameter, is NULL
     */
    @TypeConverter
    public int userTypeToInt(UserType userType) throws IllegalArgumentException{
        if(userType != null){
            return userType.getKey();
        }else{
            throw new IllegalArgumentException("DB userTypeToInt : Invalid user type. User type " +
                    "object can not be NULL.");
        }
    }

}
