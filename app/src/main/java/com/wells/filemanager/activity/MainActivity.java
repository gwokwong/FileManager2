package com.wells.filemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wells.filemanager.R;
import com.wells.filemanager.util.FileUtils;

import java.io.File;

/**
 * Created by wells on 16/4/21.
 */

public class MainActivity extends TActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener {

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

        getToolbar().setOnMenuItemClickListener(this);

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        setOverflowIconVisible(featureId, menu);
//        return super.onMenuOpened(featureId, menu);
//    }

//    /**
//     * 显示OverflowMenu的Icon
//     *
//     * @param featureId
//     * @param menu
//     */
//    private void setOverflowIconVisible(int featureId, Menu menu) {
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                    Log.d("OverflowIconVisible", e.getMessage());
//                }
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


}
