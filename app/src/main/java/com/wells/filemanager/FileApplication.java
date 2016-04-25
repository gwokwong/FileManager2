package com.wells.filemanager;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

import cn.bmob.v3.Bmob;

/**
 * Created by wells on 16/4/23.
 */
public class FileApplication extends Application {

    public static FileApplication instance;

    public static FileApplication getInstance(){
        return  instance;
    }

    public PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(getApplicationContext(), Config.BMOB_KEY);

        // 初始化patch管理类
        mPatchManager = new PatchManager(this);
        // 初始化patch版本
        mPatchManager.init(getVersionName(this));
        // 加载已经添加到PatchManager中的patch
        mPatchManager.loadPatch();


        //添加patch，只需指定patch的路径即可，补丁会立即生效
//        mPatchManager.addPatch(path);

        //删除所有已加载的patch文件 apk版本升级
//        mPatchManager.removeAllPatch();


    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
