package com.train2gain.train2gain.source.remote.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A simple wrapper class for API web service response
 * @param <T> the type of content that should be present in the API web service response on SUCCESS
 *            it can be an entity, a list or another object
 */
public class APIResponse<T> {

    public enum Status {
        SUCCESS,    // the request was OK (all params OK); response received
        ERROR,      // the request was OK (but some params were wrong); error response received
        FAILURE     // some error occurred during the request (es. network) or response parsing
    }

    @NonNull
    private final Status status;

    @Nullable
    private final APIData<T> data;

    @Nullable
    private final APIError error;

    @Nullable
    private final Throwable throwable;

    /**
     * Builds a custom API web service response assigning the given properties
     * @param status represents the response status; it can be one of the Status enum values
     * @param data if status is equal to SUCCESS, it contains the data returned by the API web service
     * @param error if status status is equal to ERROR, it contains the error returned by the API web service
     * @param throwable if status is equal to FAILURE, it contains a throwable occurred during API request
     *                  or during response parsing
     */
    private APIResponse(Status status, APIData<T> data, APIError error, Throwable throwable){
        this.status = status;
        this.data = data;
        this.error = error;
        this.throwable = throwable;
    }


    // INIT all possible API web service responses

    public static <T> APIResponse success(@NonNull APIData<T> data){
        return new APIResponse(Status.SUCCESS, data, null, null);
    }

    public static APIResponse error(@NonNull APIError error){
        return new APIResponse(Status.ERROR, null, error, null);
    }

    public static APIResponse failure(@NonNull Throwable throwable){
        return new APIResponse(Status.FAILURE, null, null, throwable);
    }


    // GETTERS

    @NonNull public Status getStatus() {
        return status;
    }

    @Nullable public APIData<T> getData() {
        return data;
    }

    @Nullable public APIError getError() {
        return error;
    }

    @Nullable public Throwable getThrowable() {
        return throwable;
    }

}
