package com.wells.filemanager.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.wells.filemanager.R;
import com.wells.filemanager.adapter.FileListAdapter;
import com.wells.filemanager.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wells on 16/4/24.
 */
public class BigFileActivity extends TActivity {
    private ListView fileListView;
    private CheckBox allCheckBox;
    private Button confirmBtn;
    private List<File> files = new ArrayList<File>();

    //默认设置超过100M为大文件
    private int defaultBigFileSize = 10;
    private int defaultSizeType = FileUtils.TYPE_MB;

    private FileListAdapter adapter;

    private static final int SEARCH = 10001;
    private static final int DELETE = 10002;

    private ProgressDialog loadingDialog = null;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEARCH:
                    if(loadingDialog!=null){
                        loadingDialog.dismiss();
                    }
                    adapter.setAllCheck(true);
                    allCheckBox.setChecked(true);
//                    adapter.notifyDataSetChanged();
                    break;
                case DELETE:
                    toast("删除成功!");
                    adapter.setDatas(new ArrayList<File>());
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigfile);
        setTitle(R.string.bigFile, true);
        initViews();
        initData();
    }
    private void initData() {
        adapter = new FileListAdapter(this, files, R.layout.item_list_file);
        fileListView.setAdapter(adapter);
        loadingDialog.show();
        searchThread.start();

    }

    private void initViews() {
        fileListView = (ListView) findViewById(R.id.bigfile_listview);
        allCheckBox = (CheckBox) findViewById(R.id.bigfile_all_check);
        confirmBtn = (Button) findViewById(R.id.bigfile_confirm_delete);

        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.v("info","checked status " +checked);
                if (checked) {
                    adapter.setAllCheck(true);
                } else {
                    adapter.setAllCheck(false);
                }
            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(deleteRun).start();
            }
        });

        loadingDialog = new ProgressDialog(this);
        //实例化
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        loadingDialog.setTitle("Google");
        //设置ProgressDialog 标题
        loadingDialog.setMessage("文件查找中...");
        //设置ProgressDialog 提示信息
        loadingDialog.setIcon(R.mipmap.ic_launcher);
        //设置ProgressDialog 标题图标
        loadingDialog.setButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //设置ProgressDialog 的一个Button
        loadingDialog.setIndeterminate(false);
        //设置ProgressDialog 的进度条是否不明确
        loadingDialog.setCancelable(true);
        //设置ProgressDialog 是否可以按退回按键取消


    }


    private Runnable deleteRun = new Runnable() {
        @Override
        public void run() {
            List<File> checkFiles = adapter.getCheckFiles();
            for (File file : checkFiles) {
                if(file.exists()){
                    file.delete();
                }
            }
            handler.sendEmptyMessage(DELETE);
        }
    };

    private Thread searchThread = new Thread(){
        @Override
        public void run() {
            FileUtils.getGreaterSizeFiles(files, new File(Environment.getExternalStorageDirectory().getAbsolutePath()), defaultBigFileSize, defaultSizeType);
            handler.sendEmptyMessage(SEARCH);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteRun =null;
        searchThread = null;
        loadingDialog = null;
    }
}
