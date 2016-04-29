package com.wells.filemanager.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wells on 16/4/23.
 */
public class Feedback extends BmobObject {

    private String title;

    private String msg;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Feedback{" +"title='" + title + '\'' +", msg='" + msg + '\'' +'}';
    }
}
