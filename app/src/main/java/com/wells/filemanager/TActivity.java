package com.wells.filemanager;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wells on 16/4/23.
 */
public class TActivity extends AppCompatActivity {
    protected Handler handler;

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    public void setTitle(int resId, boolean isDisplayHomeAsUpEnabled) {
        View toolbarView = findViewById(R.id.common_head);
        Toolbar toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
        if (null != toolbarView && null != toolbar) {
            toolbar.setTitle(resId);
//            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
            if (isDisplayHomeAsUpEnabled) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                toolbar.setNavigationIcon(R.mipmap.back);
            }
        }

    }

    protected void toast(CharSequence msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
