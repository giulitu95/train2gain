package com.train2gain.train2gain.source.remote.converter;

import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.train2gain.train2gain.model.enums.ScheduleStepType;

import java.lang.reflect.Type;

public class ScheduleStepTypeConverter implements JsonDeserializer<ScheduleStepType>, JsonSerializer<ScheduleStepType> {

    /**
     * Converts a JSON element, containing the schedule step type (as int value), into a ScheduleStepType enum
     * @param jsonElement the JSON element containing the schedule step type as int value
     * @param typeOfJsonElement
     * @param context
     * @return a ScheduleStepType enum that represents the schedule step type value read from JSON element,
     *         if no errors has occurred, NULL otherwise
     * @throws JsonParseException Thrown if there was something wrong in the JSON element syntax
     * @throws IllegalArgumentException Thrown if the schedule step type value was not valid (not a ScheduleStepType)
     */
    @Override
    public ScheduleStepType deserialize(@NonNull JsonElement jsonElement, Type typeOfJsonElement,
                                        JsonDeserializationContext context)
            throws JsonParseException, IllegalArgumentException {
        ScheduleStepType scheduleStepType = ScheduleStepType.getFromKey(jsonElement.getAsInt());
        if(scheduleStepType != null){
            return scheduleStepType;
        }else{
            throw new IllegalArgumentException("JsonElement : Invalid schedule step type value.");
        }
    }

    /**
     * Converts a ScheduleStepType enum into a JSON element representing it as int value
     * @param scheduleStepType the ScheduleStepType enum that we want to convert into the JSON element value
     * @param typeOfScheduleStepType
     * @param context
     * @return a JSON element containing the schedule step type (as int value) stored in the ScheduleStepType enum
     */
    @Override
    public JsonElement serialize(@NonNull ScheduleStepType scheduleStepType, Type typeOfScheduleStepType,
                                 JsonSerializationContext context) {
        return new JsonPrimitive(scheduleStepType.getKey());
    }

}
