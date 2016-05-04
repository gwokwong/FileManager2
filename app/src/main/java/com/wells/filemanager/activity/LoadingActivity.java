package com.wells.filemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

    private void initData() {

//        UpdateAgent.initAppVersion(this);
        UpdateAgent.update(this);



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
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }
}
