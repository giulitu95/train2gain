package com.train2gain.train2gain.source.remote.converter;

import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.train2gain.train2gain.model.enums.MuscleGroup;

import java.lang.reflect.Type;

public class MuscleGroupConverter implements JsonDeserializer<MuscleGroup>, JsonSerializer<MuscleGroup> {

    /**
     * Converts a JSON element, containing the muscle group (as int value), into a MuscleGroup enum
     * @param jsonElement the JSON element containing the muscle group as int value
     * @param typeOfJsonElement
     * @param context
     * @return a MuscleGroup enum that represents the muscle group value read from JSON element, if no
     *         errors has occurred, NULL otherwise
     * @throws JsonParseException Thrown if there was something wrong in the JSON element syntax
     * @throws IllegalArgumentException Thrown if the muscle group value was not valid (not an MuscleGroup)
     */
    @Override
    public MuscleGroup deserialize(@NonNull JsonElement jsonElement, Type typeOfJsonElement,
                                   JsonDeserializationContext context)
            throws JsonParseException, IllegalArgumentException {
        MuscleGroup muscleGroup = MuscleGroup.getFromKey(jsonElement.getAsInt());
        if(muscleGroup != null){
            return muscleGroup;
        }else{
            throw new IllegalArgumentException("JsonElement : Invalid muscle group value.");
        }
    }

    /**
     * Converts a MuscleGroup enum into a JSON element representing it as int value
     * @param muscleGroup the MuscleGroup enum that we want to convert into the JSON element value
     * @param typeOfMuscleGroup
     * @param context
     * @return a JSON element containing the muscle group (as int value) stored in the MuscleGroup enum
     */
    @Override
    public JsonElement serialize(@NonNull MuscleGroup muscleGroup, Type typeOfMuscleGroup, JsonSerializationContext context) {
        return new JsonPrimitive(muscleGroup.getKey());
    }

}
