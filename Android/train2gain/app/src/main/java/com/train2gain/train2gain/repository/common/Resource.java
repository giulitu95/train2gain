package com.train2gain.train2gain.repository.common;

import android.support.annotation.NonNull;

/**
 * A data wrapper containing the data and its status (API retrieving status)
 * @param <T> represents the Resource data type (it should be an Entity class or a List)
 */
public class Resource<T> {

    /**
     * Enum that represents all possible Resource's status
     * Status depends mainly on the API data retrieving operations
     * Ex. SUCCESS = data has been retrieved correctly from the server
     *               the database has been updated with the new data
     *               the database returned the new data
     * Ex. ERROR = error while retrieving the new data from the server
     *             the database returned old data
     */
    public enum Status {
        SUCCESS, ERROR, LOADING
    }

    @NonNull
    private final Status status;
    private final T data;
    private final String message;

    /**
     * Builds a custom Resource object assigning the given properties
     * @param status the status of the resource that we use to know if resource is updated or not
     *               or to know if something went wrong while retrieving data from network
     * @param data the data that comes from the database (= single source of truth)
     * @param message if not NULL, it contains the API error message
     */
    private Resource(@NonNull Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    // INIT Resource Object for each STATUS

    public static <T> Resource<T> success(@NonNull T data){
        return new Resource(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> loading(T data){
        return new Resource(Status.LOADING, data, null);
    }

    public static <T> Resource<T> error(T data, @NonNull String message){
        return new Resource(Status.ERROR, data, message);
    }


    // GETTERS

    @NonNull public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}
