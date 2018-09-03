package com.train2gain.train2gain.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.train2gain.train2gain.App;

public class NetworkUtil {

    /**
     * Gets the network info
     * @return network info
     */
    private static NetworkInfo getNetworkInfo(){
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null) ? cm.getActiveNetworkInfo() : null;
    }

    /**
     * Checks if there is any connectivity
     * @return true if there is some connectivity
     *         false otherwise
     */
    public static boolean isConnected(){
        NetworkInfo networkInfo = NetworkUtil.getNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
