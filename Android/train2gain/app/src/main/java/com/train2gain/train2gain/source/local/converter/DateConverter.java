package com.train2gain.train2gain.source.local.converter;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import java.util.Date;

public class DateConverter {

    /**
     * Converts the timestamp value coming from local database into a Date object
     * @param timestamp the timestamp value retrieved from the local database
     * @return a Date object that represents the timestamp value, if it is greater or equal to zero
     *         a NULL value object otherwise
     */
    @TypeConverter
    public Date dateFromTimestamp(long timestamp) {
        if (timestamp < 0){
            return null;
        } else {
            return new Date(timestamp);
        }
    }

    /**
     * Converts the Date object into a timestamp integer value, that will be stored in the local database
     * @param date the Date object we want to convert to timestamp value (it can be NULL)
     * @return a timestamp integer value that represents the Date object value, if it is not NULL
     *         a -1 value otherwise
     */
    @TypeConverter
    public long dateToTimestamp(@Nullable Date date){
        if(date != null){
            return date.getTime();
        }else{
            return -1;
        }
    }

}
