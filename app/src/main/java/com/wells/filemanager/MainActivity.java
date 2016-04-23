package com.wells.filemanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends AppCompatActivity  {

    private String sdAbsolutePath;
    private File sdFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void menuClick(View view){
        switch (view.getId()){
            case R.id.deleteEmptyDirBtn:  //删除空目录
                deleteSDEmptyDirectory();
                break;
            case R.id.feedbackBtn: //意见反馈

                break;
            case R.id.bigFileBtn: //大文件查找
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
}
