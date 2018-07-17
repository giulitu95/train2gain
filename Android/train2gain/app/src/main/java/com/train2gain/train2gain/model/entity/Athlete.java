package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Athlete {

    @NonNull
    @Expose @SerializedName("id")
    private long userId = 0;

    @Nullable
    @Expose @SerializedName("weight")
    private int weight = -1;

    @Nullable
    @Expose @SerializedName("height")
    private int height = -1;

    @Nullable
    @Expose @SerializedName("firstWorkoutDate")
    private Date firstWorkoutDate = null;

    @Nullable
    @Expose @SerializedName("trainerId")
    private long trainerUserId = -1;


    // GETTERS

    @NonNull public long getUserId() {
        return userId;
    }

    @Nullable public int getWeight() {
        return weight;
    }

    @Nullable public int getHeight() {
        return height;
    }

    @Nullable public Date getFirstWorkoutDate() {
        return firstWorkoutDate;
    }

    @Nullable public long getTrainerUserId() {
        return trainerUserId;
    }


    // SETTERS

    public void setUserId(@NonNull long userId) {
        this.userId = userId;
    }

    public void setWeight(@Nullable int weight) {
        this.weight = weight;
    }

    public void setHeight(@Nullable int height) {
        this.height = height;
    }

    public void setFirstWorkoutDate(@Nullable Date firstWorkoutDate) {
        this.firstWorkoutDate = firstWorkoutDate;
    }

    public void setTrainerUserId(@Nullable long trainerUserId) {
        this.trainerUserId = trainerUserId;
    }

}
