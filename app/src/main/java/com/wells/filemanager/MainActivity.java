package com.wells.filemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wells.filemanager.File.BigFileActivity;
import com.wells.filemanager.common.util.FileUtils;
import com.wells.filemanager.feedback.FeedbackActivity;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends TActivity {

    private String sdAbsolutePath;
    private File sdFile;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name,false);

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
//                List<File> list = new ArrayList<File>();
//                FileUtils.getGreaterSizeFiles(list,new File(Environment.getExternalStorageDirectory().getAbsolutePath()),100,FileUtils.TYPE_MB);
//                Toast.makeText(MainActivity.this,"file length is "+list.size(),Toast.LENGTH_LONG).show();

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
}
