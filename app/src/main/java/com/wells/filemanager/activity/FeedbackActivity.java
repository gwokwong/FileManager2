package com.wells.filemanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wells.filemanager.R;
import com.wells.filemanager.bean.Feedback;
import com.wells.filemanager.widget.ProgressWheelDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wells on 16/4/23.
 */
public class FeedbackActivity extends TActivity implements View.OnClickListener {

    private EditText titleEt, suggestionEt;
    private Button submitBtn;
    private Context mContext;
    private ProgressWheelDialog loadingDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initViews();
        setTitle(R.string.feedback, true);
        mContext = FeedbackActivity.this;
    }

    private void initViews() {
        titleEt = (EditText) findViewById(R.id.feedback_theme);
        suggestionEt = (EditText) findViewById(R.id.feedback_suggestion);
        submitBtn = (Button) findViewById(R.id.feedback_submit);
        submitBtn.setOnClickListener(this);
        loadingDialog = new ProgressWheelDialog(this);
        loadingDialog.setMessage(getString(R.string.submit_data));
    }

    @Override
    public void onClick(View view) {

        showKeyboard(false);

        if (TextUtils.isEmpty(titleEt.getText().toString())) {
            Snackbar(getString(R.string.theme_null));
            return;
        }

        if (TextUtils.isEmpty(suggestionEt.getText().toString())) {
            Snackbar(getString(R.string.suggestion_null_tip));
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setTitle(titleEt.getText().toString());
        feedback.setMsg(suggestionEt.getText().toString());
        feedback.save(new SaveListener<String>() {

            @Override
            public void onStart() {
                super.onStart();
                loadingDialog.show();
            }

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    titleEt.setText("");
                    suggestionEt.setText("");
                    Snackbar(getString(R.string.thanks_submit_close));
                    mHandler.sendEmptyMessageDelayed(0, 1700);
                } else {
                    Snackbar(getString(R.string.submit_failure));
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadingDialog.dismiss();
            }
        });
//        feedback.save(FeedbackActivity.this, new SaveListener() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                loadingDialog.show();
//            }
//
//
//            @Override
//            public void onSuccess() {
//                titleEt.setText("");
//                suggestionEt.setText("");
//                Snackbar(getString(R.string.thanks_submit_close));
//                mHandler.sendEmptyMessageDelayed(0, 1700);
//            }
//
//
//            @Override
//            public void onFailure(int i, String s) {
//                Snackbar(getString(R.string.submit_failure));
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                loadingDialog.dismiss();
//            }
//        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };


}
