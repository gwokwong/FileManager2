package com.wells.filemanager.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wells.filemanager.R;

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

    /**
     * 延时弹出键盘
     *
     * @param focus 键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 200);
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
