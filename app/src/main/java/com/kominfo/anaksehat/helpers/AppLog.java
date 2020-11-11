package com.kominfo.anaksehat.helpers;

import android.util.Log;

import com.kominfo.anaksehat.BuildConfig;

public class AppLog {
    public final static String TAG = "Stunting App";

    public static void d(String log){
        if(BuildConfig.DEBUG)
            if(log!=null) {
                Log.d(TAG, log);
            } else
                Log.d(TAG, "null");
    }
}
