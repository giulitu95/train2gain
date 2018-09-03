package com.train2gain.train2gain.source.remote.converter;

import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.train2gain.train2gain.model.enums.UserType;

import java.lang.reflect.Type;

public class UserTypeConverter implements JsonDeserializer<UserType>, JsonSerializer<UserType> {

    /**
     * Converts a JSON element, containing the user type (as int value), into a UserType enum
     * @param jsonElement the JSON element containing the user type as int value
     * @param typeOfJsonElement
     * @param context
     * @returna a UserType enum that represents the user type value read from JSON element, if no
     *          errors has occurred, NULL otherwise
     * @throws JsonParseException Thrown if there was something wrong in the JSON element syntax
     * @throws IllegalArgumentException Thrown if the user type value was not valid (not a UserType)
     */
    @Override
    public UserType deserialize(@NonNull JsonElement jsonElement, Type typeOfJsonElement,
                                JsonDeserializationContext context)
            throws JsonParseException, IllegalArgumentException {
        UserType userType = UserType.getFromKey(jsonElement.getAsInt());
        if(userType != null){
            return userType;
        }else{
            throw new IllegalArgumentException("JsonElement : Invalid user type value.");
        }
    }

    /**
     * Converts a UserType enum into a JSON element representing it as int value
     * @param userType the UserType enum that we want to convert into the JSON element value
     * @param typeOfUserType
     * @param context
     * @return a JSON element containing the user type (as int value) stored in the UserType enum
     */
    @Override
    public JsonElement serialize(@NonNull UserType userType, Type typeOfUserType, JsonSerializationContext context) {
        return new JsonPrimitive(userType.getKey());
    }

}
