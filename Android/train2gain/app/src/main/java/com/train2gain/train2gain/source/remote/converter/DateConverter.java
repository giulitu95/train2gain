package com.train2gain.train2gain.source.remote.converter;

import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

public class DateConverter implements JsonDeserializer<Date>, JsonSerializer<Date> {

    /**
     * Converts a JSON element containing a date (timestamp format) into a Java Date object
     * @param jsonElement the JSON element containing the date as timestamp format
     * @param typeOfJsonElement
     * @param context
     * @return  a Date object that represents (with timezone handling) the timestamp value, read from
     *          the JSON element, if no errors has occurred, NULL otherwise
     * @throws JsonParseException Thrown if there was something wrong in the JSON element syntax
     * @throws IllegalArgumentException Thrown if the timestamp value was not valid (not a Date)
     */
    @Override
    public Date deserialize(@NonNull JsonElement jsonElement, Type typeOfJsonElement,
                            JsonDeserializationContext context)
            throws JsonParseException, IllegalArgumentException {
        long dateTimestamp = jsonElement.getAsLong();
        if(dateTimestamp >= 0){
            return new Date(dateTimestamp);
        }else{
            throw new IllegalArgumentException("JsonElement : Invalid date timestamp value.");
        }
    }

    /**
     * Converts a Java Date object into a JSON element representing it as timestamp format
     * @param date the Java Date object that we want to convert into the JSON element value
     * @param typeOfDate
     * @param context
     * @return a JSON element containing the date (as timestamp format) stored in the Date Java object
     */
    @Override
    public JsonElement serialize(@NonNull Date date, Type typeOfDate, JsonSerializationContext context) {
        return new JsonPrimitive(date.getTime());
    }

}
