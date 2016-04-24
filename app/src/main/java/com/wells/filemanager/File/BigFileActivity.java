package com.wells.filemanager.File;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wells.filemanager.R;
import com.wells.filemanager.TActivity;

/**
 * Created by wells on 16/4/24.
 */
public class BigFileActivity extends TActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigfile);
        setTitle(R.string.call_num_hint,false);

    }
}
