package com.wells.filemanager.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wells.filemanager.R;
import com.wells.filemanager.TActivity;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wells on 16/4/23.
 */
public class FeedbackActivity extends TActivity {

    private EditText nameEt,callNumEt,suggestionEt;
    private RadioGroup calltypeRg;
    private RadioButton QQRb,EmailRb,phoneRb,weChatRb;
    private Button submitBtn;
    private int callType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        if(null!=toolbar){
//            toolbar.setTitle(R.string.feedback);
//            toolbar.setTitleTextColor(Color.WHITE);
//            setSupportActionBar(toolbar);
////            if(isDisplayHomeAsUpEnabled){
////                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////            }
//        }
        initViews();
        setTitle(R.string.feedback,true);
    }

    private void initViews(){
        nameEt = (EditText)findViewById(R.id.feedback_name);
        callNumEt = (EditText)findViewById(R.id.feedback_call_num);
        suggestionEt = (EditText)findViewById(R.id.feedback_suggestion);
        calltypeRg = (RadioGroup)findViewById(R.id.feedback_call_type_rg);
        QQRb = (RadioButton)findViewById(R.id.feedback_qq);
        EmailRb = (RadioButton)findViewById(R.id.feedback_email);
        phoneRb = (RadioButton)findViewById(R.id.feedback_phone);
        weChatRb = (RadioButton)findViewById(R.id.feedback_wechat);
        submitBtn = (Button)findViewById(R.id.feedback_submit);

        calltypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (radioButtonId){
                    case R.id.feedback_qq:
                        callType =Feedback.CALL_TYPE_QQ;
                        break;
                    case R.id.feedback_email:
                        callType =Feedback.CALL_TYPE_EMAIL;
                        break;
                    case R.id.feedback_phone:
                        callType =Feedback.CALL_TYPE_PHONE;
                        break;
                    case R.id.feedback_wechat:
                        callType =Feedback.CALL_TYPE_WECHAT;
                        break;
                    default:
                        break;

                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(suggestionEt.getText().toString())){
                    toast("意见为空,请输入您的意见或者建议后再提交");
                    return;
                }

                Feedback feedback = new Feedback();
                feedback.setCallNum(callNumEt.getText().toString());
                feedback.setCallType(callType);
                feedback.setMsg(suggestionEt.getText().toString());
                feedback.setName(nameEt.getText().toString());

                feedback.save(FeedbackActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("感谢您的提交,我们会尽快查看您的建议和意见");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("提交失败");

                    }
                });

            }
        });





    }
}
