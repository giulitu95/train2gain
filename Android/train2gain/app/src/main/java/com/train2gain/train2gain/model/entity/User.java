package com.train2gain.train2gain.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.source.local.converter.UserTypeConverter;

import java.util.Date;

@Entity(
    tableName = User.TABLE_NAME,
    primaryKeys = { User.COLUMN_ID },
    indices = {
        @Index(value = { User.COLUMN_SYNCED_WITH_SERVER })
    }
)
@TypeConverters({
    UserTypeConverter.class
})
public class User {

    // Table and columns name definitions
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DISPLAY_NAME = "display_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHOTO_URL = "photo_url";
    public static final String COLUMN_REGISTRATION_DATE = "registration_date";
    public static final String COLUMN_SYNCED_WITH_SERVER = "synced_with_server";

    @NonNull
    @ColumnInfo(name = COLUMN_ID)
    @Expose @SerializedName("id")
    private long id = 0;

    @NonNull
    @ColumnInfo(name = COLUMN_TYPE)
    @Expose @SerializedName("userType")
    private UserType type;

    @NonNull
    @ColumnInfo(name = COLUMN_DISPLAY_NAME)
    @Expose @SerializedName("displayName")
    private String displayName;

    @NonNull
    @ColumnInfo(name = COLUMN_EMAIL)
    @Expose @SerializedName("email")
    private String email;

    @Nullable
    @ColumnInfo(name = COLUMN_PHOTO_URL)
    @Expose @SerializedName("profileImageUrl")
    private String photoUrl = null;

    @NonNull
    @ColumnInfo(name = COLUMN_REGISTRATION_DATE)
    @Expose @SerializedName("registrationDate")
    private Date registrationDate;

    @NonNull
    @ColumnInfo(name = COLUMN_SYNCED_WITH_SERVER)
    private boolean syncedWithServer = false;


    public User(){

    }

    @Ignore
    public User(int id, UserType type, String displayName, String email, Date registrationDate){
        this.id = id;
        this.type = type;
        this.displayName = displayName;
        this.email = email;
        this.registrationDate = registrationDate;
    }

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

    @NonNull public boolean isSyncedWithServer() {
        return syncedWithServer;
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

    public void setSyncedWithServer(@NonNull boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }

}
