package com.wells.filemanager.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.wells.filemanager.R;
import com.wells.filemanager.adapter.FileListAdapter;
import com.wells.filemanager.util.FileUtils;
import com.wells.filemanager.widget.ProgressWheelDialog;

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
    private int defaultBigFileSize = 100;
    private int defaultSizeType = FileUtils.TYPE_MB;

    private FileListAdapter adapter;

    private static final int SEARCH = 10001;
    private static final int DELETE = 10002;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH:
                    ProgressWheelDialog.getInstance(BigFileActivity.this).dismiss();
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
        startScanSD();

    }

    private void startScanSD() {
        ProgressWheelDialog.getInstance(this).show();
        new Thread(searchRun).start();
    }

    private void initViews() {
        fileListView = (ListView) findViewById(R.id.bigfile_listview);
        allCheckBox = (CheckBox) findViewById(R.id.bigfile_all_check);
        confirmBtn = (Button) findViewById(R.id.bigfile_confirm_delete);

        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

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

        addHeadRightBtn("重新扫描", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanSD();
            }
        });
    }

    private Runnable deleteRun = new Runnable() {
        @Override
        public void run() {
            List<File> checkFiles = adapter.getCheckFiles();
            for (File file : checkFiles) {
                if (file.exists()) {
                    file.delete();
                }
            }
            handler.sendEmptyMessage(DELETE);

        }
    };

    private Runnable searchRun = new Runnable() {
        @Override
        public void run() {
            files.clear();
            FileUtils.getGreaterSizeFiles(files, new File(Environment.getExternalStorageDirectory().getAbsolutePath()), defaultBigFileSize, defaultSizeType);
            handler.sendEmptyMessageDelayed(SEARCH, 300);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteRun = null;
        searchRun = null;
    }
}
