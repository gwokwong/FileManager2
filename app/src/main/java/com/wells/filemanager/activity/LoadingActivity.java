package com.wells.filemanager.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.wells.filemanager.Permission.PermissionListener;
import com.wells.filemanager.Permission.PermissionManager;
import com.wells.filemanager.R;
import com.wells.filemanager.util.UpdateAgent;

import cn.bmob.v3.update.UpdateResponse;

/**
 * Created by wells on 16/4/25.
 */
public class LoadingActivity extends TActivity {

    UpdateResponse ur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initData();
    }

    PermissionManager helper;

    private final static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;

    private void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //授权
            helper = PermissionManager.with(LoadingActivity.this)
                    //添加权限请求码
                    .addRequestCode(REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
                    //设置权限，可以添加多个权限
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //设置权限监听器
                    .setPermissionsListener(new PermissionListener() {

                        @Override
                        public void onGranted() {
//                        //当权限被授予时调用
//                        Toast.makeText(LoadingActivity.this, "Camera Permission granted", Toast.LENGTH_LONG).show();
                            UpdateAgent.update(LoadingActivity.this);
                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onDenied() {
                            //用户拒绝该权限时调用
                            Toast.makeText(LoadingActivity.this, "读写存储空间申请被拒绝", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onShowRationale(String[] permissions) {
                            //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）
                        Snackbar.make(findViewById(R.id.common_coordinator), "需要读写存储空间权限去搜索文件", Snackbar.LENGTH_INDEFINITE)
                                .setAction("ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //必须调用该`setIsPositive(true)`方法
                                        helper.setIsPositive(true);
                                        helper.request();
                                    }
                                }).show();
                        }
                    })
                    //请求权限
                    .request();
        } else {
            UpdateAgent.update(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
//        UpdateAgent.initAppVersion(this);


//        BmobUpdateAgent.initAppVersion(this);
//        BmobUpdateAgent.setUpdateOnlyWifi(false);
//        BmobUpdateAgent.update(this);
//        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
//
//            @Override
//            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//                // TODO Auto-generated method stub
//                if (updateStatus == UpdateStatus.Yes) {
//                    ur = updateInfo;
//                    if (ur != null) {
//                        File file = new File(Environment
//                                .getExternalStorageDirectory(), ur.path_md5 + ".apk");
//                        if (file != null) {
//                            file.delete();
//                        }
//                    }
//
////                    Toast.makeText(LoadingActivity.this, "该版本", Toast.LENGTH_SHORT).show();
//                } else if (updateStatus == UpdateStatus.IGNORED) {//新增忽略版本更新
//                    Toast.makeText(LoadingActivity.this, "该版本已经被忽略更新", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        //补丁检测

//        UpdateAgent.update(this);
//        startActivity(new Intent(this, MainActivity.class));
//        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                helper.onPermissionResult(permissions, grantResults);
                break;
        }
    }
}
