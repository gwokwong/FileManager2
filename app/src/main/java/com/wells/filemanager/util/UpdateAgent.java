package com.wells.filemanager.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.wells.filemanager.Config;
import com.wells.filemanager.FileApplication;
import com.wells.filemanager.activity.UpdateActivity;
import com.wells.filemanager.bean.Version;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wells on 16/5/4.
 */
public class UpdateAgent {

    private static String TAG = UpdateAgent.class.getSimpleName();

    private static Context mContext;


    public static void initAppVersion(final Context context) {
        mContext = context;
        Version version = new Version();
        version.setIsforce(false);
        version.setPath(BmobFile.createEmptyFile());
        version.setTarget_size("");
        version.setUpdate_log("");
        version.setVersion("");
        version.setVersion_i(0);
        version.save(context, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public static void update(final Context context) {
        BmobQuery<Version> bmobQuery = new BmobQuery<Version>();
        bmobQuery.addWhereGreaterThan("version_i", AppUtils.getVersionCode(context));
        bmobQuery.order("-version_i");
        bmobQuery.findObjects(context, new FindListener<Version>() {
            @Override
            public void onSuccess(List<Version> list) {
                if (list != null && list.size() > 0) {
                    if(list.get(0).getIsforce()){
                        UpdateActivity.statrt2UpdateActivity(context,list.get(0));
                        return;
                    }

                    boolean isIgnore = PrefUtils.getBoolValue(Config.SHARE_KEY_IGNORE_UPDATE);
                    int versionCode = PrefUtils.getIntValue(Config.SHARE_KEY_IGNORE_UPDATE_VERSION);
                    if (isIgnore && versionCode == AppUtils.getVersionCode(context)) {
                        return;
                    }

                    File file = new File(Environment.getExternalStorageDirectory(), list.get(0).getPath().getFilename() + ".apk");
                    if (file.exists()) {
                        //启动安装
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                        return;
                    }

                    UpdateActivity.statrt2UpdateActivity(context,list.get(0));
                    return;

                }
            }

            @Override
            public void onError(int i, String s) {
                Log.v(TAG, "update error->" + s);

            }
        });
    }

    public static void download(String apkName,String apkUrl){
        File saveFile = new File(Environment.getExternalStorageDirectory(), apkName);
        BmobFile bmobFile = new BmobFile(apkName, null, apkUrl);
        bmobFile.download(FileApplication.getInstance(), saveFile, new DownloadFileListener() {
            @Override
            public void onSuccess(String savePath) {
                //安装文件
                File file = new File(savePath);
                if (file.exists()) {
                    //启动安装
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    FileApplication.getInstance().startActivity(intent);
                    return;
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Log.v("info",s);

            }
        });

    }

}
