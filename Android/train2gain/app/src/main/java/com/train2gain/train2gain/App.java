package com.train2gain.train2gain;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context appContext = null;

    @Override
    public void onCreate(){
        super.onCreate();
        appContext = this;
    }

    public static Context getContext(){
        return appContext;
    }

}
