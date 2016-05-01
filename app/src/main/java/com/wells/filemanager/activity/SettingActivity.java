package com.wells.filemanager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.wells.filemanager.Config;
import com.wells.filemanager.R;
import com.wells.filemanager.util.PrefUtils;

/**
 * Created by wells on 16/4/30.
 */
public class SettingActivity extends TActivity {

    private EditText sizeEt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle(R.string.setting, true);
        addHeadRightBtn("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKeyboard(false);
                String size = sizeEt.getText().toString();
                if (size.isEmpty()) {
                    Snackbar("默认大小不能为空");
                    return;
                }

                int sizeValue = Integer.valueOf(size);
                PrefUtils.putIntValue(Config.SHARE_KEY_BIGFILE_SIZE, sizeValue);
                Snackbar("保存成功,本页将在1秒后关闭");
                mHandler.sendEmptyMessageDelayed(0, 1700);
            }
        });

        sizeEt = (EditText) findViewById(R.id.setting_bigfile_size);
        String lastSize = String.valueOf(PrefUtils.getIntValue(Config.SHARE_KEY_BIGFILE_SIZE));
        sizeEt.setText(lastSize);
        sizeEt.setSelection(lastSize.length());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };



}
