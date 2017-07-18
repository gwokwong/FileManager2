package com.wells.filemanager.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.wells.filemanager.R;
import com.wells.filemanager.util.FileUtils;
import com.wells.filemanager.widget.ProgressWheelDialog;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends TActivity {

    private String sdAbsolutePath;
    private File sdFile;

    private static final int DELETE_DIR = 10000;
    private ProgressWheelDialog executeDialog = null;

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name, false);
        executeDialog = new ProgressWheelDialog(this);
        executeDialog.setMessage(getString(R.string.deleteing));

//        requestDrawOverLays();

//        LMGC.getInstance().sDC(this, "aa","qq");//设置Lkey和渠道
////        LMGC.getInstance().showOSOVi(this,true,4);//设置解琐屏显示广告（4是显示广告的间隔，0<取值范围是<=7）
//
//        LMGC.getInstance().showOneUs(MainActivity.this, LMGC.CAP_MAX);



    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(MainActivity.this)) {
            Toast.makeText(this, "can not DrawOverlays", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
            }
        }
    }

    public void menuClick(View view) {
        switch (view.getId()) {
            case R.id.deleteEmptyDirBtn:  //删除空目录
                executeDialog.show();
                new Thread(deleteDirThread).start();
                break;
            case R.id.feedbackBtn: //意见反馈
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.bigFileBtn: //大文件查找
                startActivity(new Intent(MainActivity.this, BigFileActivity.class));
                break;
            case R.id.setBtn: //大文件查找
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 删除SD卡下空目录
     */
    private void deleteSDEmptyDirectory() {
        if (FileUtils.isSDAvailable()) {
            sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sdFile = new File(sdAbsolutePath);
            FileUtils.deleteEmptyDirectory(sdFile);
        }
    }

    private Runnable deleteDirThread = new Runnable() {
        @Override
        public void run() {
            deleteSDEmptyDirectory();
            mHandler.sendEmptyMessageDelayed(DELETE_DIR, 1000);
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELETE_DIR:
                    executeDialog.dismiss();
                    Snackbar(getString(R.string.delete_success));
                    break;
            }
            super.handleMessage(msg);
        }
    };

//    private static class InnerHandler extends Handler {
//
//        private final WeakReference<MainActivity> mActivity;
//
//        public InnerHandler(MainActivity activity) {
//            mActivity = new WeakReference<MainActivity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            MainActivity activity = mActivity.get();
//            if (activity != null) {
//                switch (msg.what) {
//                    case DELETE_DIR:
//                        executeDialog.dismiss();
//                        Snackbar(getString(R.string.delete_success));
//                        break;
//                }
//            }
//        }
//    }


}
