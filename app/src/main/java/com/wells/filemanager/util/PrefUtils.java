package com.wells.filemanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wells.filemanager.Config;
import com.wells.filemanager.FileApplication;

/**
 * Created by wells on 16/4/30.
 */
public class PrefUtils {

    private static SharedPreferences shared;

    private static String DEFAULT_STR_VALUE = "";

    private static int DEFAULT_INT_VALUE = 15;

    static {
        shared = FileApplication.getInstance().getSharedPreferences(Config.SHARE_NAME, Context.MODE_PRIVATE);
    }


    public static void putStrValue(String key, String value) {
        shared.edit().putString(key, value).apply();
    }

    public static String getStrValue(String key) {
        return shared.getString(key, DEFAULT_STR_VALUE);
    }

    public static void putIntValue(String key, int value) {
        shared.edit().putInt(key, value).apply();
    }

    public static int getIntValue(String key) {
        return shared.getInt(key, DEFAULT_INT_VALUE);
    }

    public static void putBoolValue(String key, boolean value) {
        shared.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolValue(String key) {
        return shared.getBoolean(key, false);
    }

}
