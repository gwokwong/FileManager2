package com.wells.filemanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int deleteCount = 0;
    private Button deleteBtn;
    private String sdAbsolutePath;
    private File sdFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);
    }

    private void deleteSDEmptyDirectory(){
        boolean isSDAvailable  = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
        if(isSDAvailable){
            sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            sdFile = new File(sdAbsolutePath);
            deleteFile(sdFile);
            Log.v("deleteFile","deleteCount->"+deleteCount);
        }
    }

    int exCount  = 0;

    private  void deleteFile(File file) {

        exCount++;
        Log.v("deleteFile","执行了->"+exCount+"次");
        try {
            if (file == null) {
                return;
            }
            if(file.exists()){
                if(file.isDirectory()){
                    File files[] = file.listFiles();
                    if(files.length==0){
                        file.delete();  //如果是空文件直接删除
                        deleteCount++;
                        deleteParentDir(file);
                    }else {
                        for (int i = 0; i < files.length; i++) {
                            deleteFile(files[i]);  //循环删除子目录
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("deleteFile", e.getMessage());
            return;
        }
    }

    private void deleteParentDir(File file){
        File parentFile = file.getParentFile();
        if(parentFile.isDirectory()){
            if(parentFile.listFiles().length==0){
                parentFile.delete();
                deleteCount++;
                deleteParentDir(parentFile);
            }
        }
    }

    @Override
    public void onClick(View view) {
        deleteSDEmptyDirectory();
    }
}
