package com.wells.filemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.wells.filemanager.R;
import com.wells.filemanager.util.FileUtils;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends TActivity implements View.OnClickListener {

    private String sdAbsolutePath;
    private File sdFile;
//    private Button bigFileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name,false);
//        bigFileBtn = (Button)findViewById(R.id.bigFileBtn);
//        bigFileBtn.setOnClickListener(this);

    }

    public void menuClick(View view){
        switch (view.getId()){
            case R.id.deleteEmptyDirBtn:  //删除空目录
                deleteSDEmptyDirectory();
                break;
            case R.id.feedbackBtn: //意见反馈
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.bigFileBtn: //大文件查找
                startActivity(new Intent(MainActivity.this, BigFileActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 删除SD卡下空目录
     */
    private void deleteSDEmptyDirectory() {
        if(FileUtils.isSDAvailable()){
            sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sdFile = new File(sdAbsolutePath);
            FileUtils.deleteEmptyDirectory(sdFile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.bigFileBtn:
//                startActivity(new Intent(MainActivity.this, BigFileActivity.class));
//                break;
        }

    }
}
