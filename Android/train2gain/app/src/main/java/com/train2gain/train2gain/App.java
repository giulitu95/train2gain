package com.train2gain.train2gain;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.cloudinary.android.MediaManager;

public class App extends Application {

    private static Context appContext = null;

    // Shared prefs (for daily workout)
    private static SharedPreferences sharedPreferences = null;
    private final static String SHARED_PREF_NAME = "train2gain_prefs";

    @Override
    public void onCreate(){
        super.onCreate();

        MediaManager.init(getApplicationContext());
        appContext = this;
        sharedPreferences = getSharedPreferences(App.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Context getContext(){
        return appContext;
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

}
