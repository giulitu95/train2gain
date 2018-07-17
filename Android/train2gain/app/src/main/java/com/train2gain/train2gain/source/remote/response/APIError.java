package com.train2gain.train2gain.source.remote.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A simple wrapper class for error response returned by API web service
 * Defines how error data (error response body) in an API response is structured
 */
public class APIError {

    @NonNull
    @Expose @SerializedName("errorCode")
    private int statusCode;

    @NonNull
    @Expose @SerializedName("errorMessage")
    private String message;


    // GETTERS

    @NonNull public int getStatusCode() {
        return statusCode;
    }

    @NonNull public String getMessage() {
        return message;
    }


    // SETTERS

    public void setStatusCode(@NonNull int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

}
