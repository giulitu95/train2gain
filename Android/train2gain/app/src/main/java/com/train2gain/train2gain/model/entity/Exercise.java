package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.train2gain.train2gain.model.enums.MuscleGroup;

public class Exercise {

    @NonNull
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @Expose @SerializedName("name")
    private String name;

    @Nullable
    @Expose @SerializedName("description")
    private String description = null;

    @NonNull
    @Expose @SerializedName("imageUrl")
    private String imageUrl;

    @NonNull
    @Expose @SerializedName("muscleGroupId")
    private MuscleGroup muscleGroup;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @NonNull public String getName() {
        return name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    @NonNull public String getImageUrl() {
        return imageUrl;
    }

    @NonNull public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMuscleGroup(@NonNull MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

}
