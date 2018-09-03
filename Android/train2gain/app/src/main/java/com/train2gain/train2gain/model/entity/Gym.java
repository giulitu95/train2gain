package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(
    tableName = Gym.TABLE_NAME,
    primaryKeys = { Gym.COLUMN_ID }
)
public class Gym {

    // Table and columns name definitions
    public static final String TABLE_NAME = "gym";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOGO_URL = "logo_url";

    @NonNull
    @ColumnInfo(name = COLUMN_ID)
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_NAME)
    @Expose @SerializedName("gymName")
    private String name;

    @Nullable
    @ColumnInfo(name = COLUMN_LOGO_URL)
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
