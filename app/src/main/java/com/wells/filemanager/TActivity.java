package com.wells.filemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * Created by wei on 16/4/23.
 */
public abstract class TActivity extends AppCompatActivity {
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    public void setTitle(int resId,boolean isDisplayHomeAsUpEnabled){
        Toolbar toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        if(null!=toolbar){
            toolbar.setTitle(resId);
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
//            if(isDisplayHomeAsUpEnabled){
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }
        }
    }

    protected  void toast(CharSequence msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }


}
