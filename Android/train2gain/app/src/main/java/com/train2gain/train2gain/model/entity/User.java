package com.train2gain.train2gain.model.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.train2gain.train2gain.model.enums.UserType;

import java.util.Date;

public class User {

    @NonNull
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @Expose @SerializedName("userType")
    private UserType type;

    @NonNull
    @Expose @SerializedName("displayName")
    private String displayName;

    @NonNull
    @Expose @SerializedName("email")
    private String email;

    @Nullable
    @Expose @SerializedName("profileImageUrl")
    private String photoUrl = null;

    @NonNull
    @Expose @SerializedName("registrationDate")
    private Date registrationDate;


    // GETTERS

    @NonNull public long getId() {
        return id;
    }

    @NonNull public UserType getType() {
        return type;
    }

    @NonNull public String getDisplayName() {
        return displayName;
    }

    @NonNull public String getEmail() {
        return email;
    }

    @Nullable public String getPhotoUrl() {
        return photoUrl;
    }

    @NonNull public Date getRegistrationDate() {
        return registrationDate;
    }


    // SETTERS

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setType(@NonNull UserType type) {
        this.type = type;
    }

    public void setDisplayName(@NonNull String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPhotoUrl(@Nullable String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setRegistrationDate(@NonNull Date registrationDate) {
        this.registrationDate = registrationDate;
    }

}
