package com.train2gain.train2gain.source.remote.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * A simple wrapper class for data returned by API web service
 * Defines how data (response body) in an API response is structured
 * @param <T> the response body content type; it can be an entity, a list or another object
 */
public class APIData<T> {

    @NonNull
    @Expose @SerializedName("currentDate")
    private Date serverTimestamp;

    @Nullable
    @Expose @SerializedName("content")
    private T content = null;


    // GETTERS

    @NonNull public Date getServerTimestamp() {
        return serverTimestamp;
    }

    @Nullable public T getContent() {
        return content;
    }


    // SETTERS

    public void setServerTimestamp(@NonNull Date serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public void setContent(@Nullable T content) {
        this.content = content;
    }

}
