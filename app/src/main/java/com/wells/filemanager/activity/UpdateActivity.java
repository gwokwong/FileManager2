package com.wells.filemanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wells.filemanager.Config;
import com.wells.filemanager.FileApplication;
import com.wells.filemanager.R;
import com.wells.filemanager.bean.Version;
import com.wells.filemanager.util.PrefUtils;
import com.wells.filemanager.util.UpdateAgent;

/**
 * Created by wells on 16/5/4.
 */
public class UpdateActivity extends TActivity implements View.OnClickListener {

    private Context mContext;
    private CheckBox ignoreCb;
    private Button closeBtn, okbtn, cancelBtn, ignoreBtn;
    private TextView updateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmob_update_dialog);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        mContext = UpdateActivity.this;
        ignoreCb = (CheckBox) findViewById(R.id.bmob_update_id_check);
        closeBtn = (Button) findViewById(R.id.bmob_update_id_close);
        okbtn = (Button) findViewById(R.id.bmob_update_id_ok);
        cancelBtn = (Button) findViewById(R.id.bmob_update_id_cancel);
        ignoreBtn = (Button) findViewById(R.id.bmob_update_id_ignore);
        updateText = (TextView) findViewById(R.id.bmob_update_content);
    }

    private void setListener() {
        ((CheckBox) this.findViewById(R.id.bmob_update_id_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    okbtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                    ignoreBtn.setVisibility(View.VISIBLE);
                } else {
                    okbtn.setVisibility(View.VISIBLE);
                    cancelBtn.setVisibility(View.VISIBLE);
                    ignoreBtn.setVisibility(View.GONE);
                }
            }
        });
        okbtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        ignoreBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    private int versionCode = 0;
    String apkUrl = "";
    String apkName = "";

    boolean isForce = false;


    private void initData() {
        Intent intent = getIntent();

        String log = intent.getStringExtra(KEY_LOG);
        String version = intent.getStringExtra(KEY_VERSION_NAME);
        versionCode = intent.getIntExtra(KEY_VERSION_CODE, 0);
        updateText.setText("最新版本号:" + version + "\n" + log);
        apkUrl = intent.getStringExtra(KEY_PATH_URL);
        apkName = intent.getStringExtra(KEY_PATH_NAME);

        isForce = intent.getBooleanExtra(KEY_IS_FORCE, false);
        if (isForce) {
            okbtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.GONE);
            ignoreBtn.setVisibility(View.GONE);
        } else {
            okbtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);
            ignoreBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == okbtn) {
            UpdateAgent.download(apkName, apkUrl);
        } else if (view == cancelBtn) {


        } else if (view == ignoreBtn) {
            //忽略当前版本更新
            PrefUtils.putBoolValue(Config.SHARE_KEY_IGNORE_UPDATE, true);
            PrefUtils.putIntValue(Config.SHARE_KEY_IGNORE_UPDATE_VERSION, versionCode);
        } else if (view == closeBtn) {
            if (isForce) {
                FileApplication.getInstance().exit();
            }

        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isForce) {
                FileApplication.getInstance().exit();

            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public void onBackPressed() {
//        if (isForce) {
//            System.exit(0);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            return;
//        } else {
//            super.onBackPressed();
//        }
//    }

    /**
     * 跳转到UpdateActivity
     *
     * @param context
     * @param version
     */
    public static void statrt2UpdateActivity(Context context, Version version) {
        Intent intent = new Intent();
        intent.setClass(context, UpdateActivity.class);
        intent.putExtra(KEY_LOG, version.getUpdate_log());
        intent.putExtra(KEY_VERSION_NAME, version.getVersion());
        intent.putExtra(KEY_VERSION_CODE, version.getVersion_i());
        intent.putExtra(KEY_IS_FORCE, version.getIsforce());
        intent.putExtra(KEY_PATH_URL, version.getPath().getUrl());
        intent.putExtra(KEY_PATH_NAME, version.getPath().getFilename());
        intent.putExtra(KEY_SIZE, version.getTarget_size());
        context.startActivity(intent);
    }

    private static String KEY_LOG = "update_log";
    private static String KEY_VERSION_NAME = "version";
    private static String KEY_VERSION_CODE = "version_i";
    private static String KEY_IS_FORCE = "isforce";
    private static String KEY_PATH_URL = "path_url";
    private static String KEY_PATH_NAME = "path_name";
    private static String KEY_SIZE = "target_size";
}
