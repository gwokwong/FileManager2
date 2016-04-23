package com.wells.filemanager;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by wei on 16/4/23.
 */
public class FileApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),Config.BMOB_KEY);

    }
}
