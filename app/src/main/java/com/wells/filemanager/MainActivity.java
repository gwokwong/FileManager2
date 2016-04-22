package com.wells.filemanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button deleteBtn;
    private String sdAbsolutePath;
    private File sdFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);
    }

    /**
     * 删除SD卡下空目录
     */
    private void deleteSDEmptyDirectory() {
        if(FileUtils.isSDAvailable()){
            sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sdFile = new File(sdAbsolutePath);
            FileUtils.deleteEmptyDirectory(sdFile);
            Log.v("deleteFile", "deleteCount->" + FileUtils.deleteCount);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deleteBtn:
                deleteSDEmptyDirectory();
                break;
            default:
                break;
        }
    }
}
