package com.wells.filemanager.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.wells.filemanager.Config;
import com.wells.filemanager.R;
import com.wells.filemanager.adapter.FileListAdapter;
import com.wells.filemanager.util.FileUtils;
import com.wells.filemanager.util.PrefUtils;
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
    private TextView countTv;

    //默认设置超过10M为大文件
    private int defaultBigFileSize = 10;
    private int defaultSizeType = FileUtils.TYPE_MB;

    private FileListAdapter adapter;

    private static final int SEARCH = 10001;
    private static final int DELETE = 10002;
    private static final int DELETE_ONE = 10003;

    //当前选中的要操作的文件
    private File nowChooseFile = null;
    //当前选中项的索引
    private int nowChoosePosition = -1;

    private ProgressWheelDialog executeDialog = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH:
                    executeDialog.dismiss();
                    adapter.setAllCheck(true);
                    allCheckBox.setChecked(true);
                    String text = String.format(getResources().getString(R.string.count_check), adapter.getCount());
                    countTv.setText(text);
                    break;
                case DELETE:
                    adapter.updateData();
                    break;
                case DELETE_ONE:
                    Snackbar("文件删除成功");
                    files.remove(nowChoosePosition);
                    adapter.notifyDataSetChanged();
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
        setListener();
        initData();
    }


    private void initViews() {
        fileListView = (ListView) findViewById(R.id.bigfile_listview);
        allCheckBox = (CheckBox) findViewById(R.id.bigfile_all_check);
        confirmBtn = (Button) findViewById(R.id.bigfile_confirm_delete);
        countTv = (TextView) findViewById(R.id.bigfile_count_check);
        registerForContextMenu(fileListView);
        executeDialog = new ProgressWheelDialog(this);
        executeDialog.setMessage("正在执行操作...");
    }

    private void setListener() {

        addHeadRightBtn("重新扫描", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanSD();
            }
        });

        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                adapter.setAllCheck(checked);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        new Thread(deleteRun).start();
                    }
                });

            }
        });

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                adapter.setmPos(pos);
            }
        });

    }

    private void initData() {
        adapter = new FileListAdapter(this, files, R.layout.item_list_file);
        fileListView.setAdapter(adapter);
        defaultBigFileSize = PrefUtils.getIntValue(Config.SHARE_KEY_BIGFILE_SIZE);
        startScanSD();
    }

    private void startScanSD() {
//        ProgressWheelDialog.getInstance(this).show();
        executeDialog.show();
        new Thread(searchRun).start();
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

    private Runnable deleteFileThread = new Runnable() {

        @Override
        public void run() {
            FileUtils.openFile(BigFileActivity.this, nowChooseFile);
            handler.sendEmptyMessageDelayed(DELETE_ONE, 300);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteRun = null;
        searchRun = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bigfile_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 得到当前被选中的item信息
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        nowChoosePosition = menuInfo.position;
        nowChooseFile = adapter.getItem(nowChoosePosition);
        switch (item.getItemId()) {
            case R.id.open:
                FileUtils.openFile(BigFileActivity.this, nowChooseFile);
                break;
            case R.id.delete:

                showConfirmDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        new Thread(deleteFileThread).start();
                    }
                });
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showConfirmDialog(DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("是否确定删除").setPositiveButton("确定", listener).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

}
