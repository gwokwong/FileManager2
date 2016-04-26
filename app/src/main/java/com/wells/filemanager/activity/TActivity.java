package com.wells.filemanager.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
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

    public static TextView addRightClickableTextViewOnToolBar(Activity activity, String text) {
        View toolbarView = activity.findViewById(R.id.common_head);
        Toolbar toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
        if (null != toolbarView && null != toolbar) {
            View view = LayoutInflater.from(activity).inflate(R.layout.nim_action_bar_right_clickable_tv, null);
            TextView textView = (TextView) view.findViewById(R.id.action_bar_right_clickable_textview);
            textView.setText(text);
            return textView;
        }

        return null;


//        ActionBar actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(true);
//
//        View view = LayoutInflater.from(activity).inflate(R.layout.nim_action_bar_right_clickable_tv, null);
//        TextView textView = (TextView) view.findViewById(R.id.action_bar_right_clickable_textview);
//        textView.setText(text);
//        toolbar.setCu
//        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER));
//        return textView;
    }


}
