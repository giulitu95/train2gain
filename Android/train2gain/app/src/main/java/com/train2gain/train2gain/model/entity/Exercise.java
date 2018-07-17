package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.train2gain.train2gain.model.enums.MuscleGroup;

@Entity(
    tableName = Exercise.TABLE_NAME,
    primaryKeys = { Exercise.COLUMN_ID }
)
public class Exercise {

    // Table and columns name definitions
    public static final String TABLE_NAME = "exercise";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_MUSCLE_GROUP = "muscle_group";

    @NonNull
    @ColumnInfo(name = COLUMN_ID)
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_NAME)
    @Expose @SerializedName("name")
    private String name;

    @Nullable
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    @Expose @SerializedName("description")
    private String description = null;

    @NonNull
    @ColumnInfo(name = COLUMN_IMAGE_URL)
    @Expose @SerializedName("imageUrl")
    private String imageUrl;

    @NonNull
    @ColumnInfo(name = COLUMN_MUSCLE_GROUP)
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
