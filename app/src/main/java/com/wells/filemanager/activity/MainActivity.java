package com.wells.filemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name, false);
        executeDialog = new ProgressWheelDialog(this);
        executeDialog.setMessage("正在执行操作...");
    }

    public void menuClick(View view) {
        switch (view.getId()) {
            case R.id.deleteEmptyDirBtn:  //删除空目录
                executeDialog.show();
//                ProgressWheelDialog.getInstance(MainActivity.this).show();
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
//                    ProgressWheelDialog.getInstance(MainActivity.this).dismiss();
                    toast("删除完成");
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
