package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gym {

    @NonNull
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @Expose @SerializedName("gymName")
    private String name;

    @Nullable
    @Expose @SerializedName("logoUrl")
    private String logoUrl = null;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @NonNull public String getName() {
        return name;
    }

    @Nullable public String getLogoUrl() {
        return logoUrl;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setLogoUrl(@Nullable String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
