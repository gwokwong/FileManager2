package com.wells.filemanager;

import cn.bmob.v3.BmobObject;

/**
 * Created by wei on 16/4/23.
 */
public class Feedback extends BmobObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "name='" + name + '\'' +
                ", callType=" + callType +
                ", msg='" + msg + '\'' +
                '}';
    }

    private String name;

    private int callType;  //类别  1qq  2邮箱  3手机  4微信号

    private String msg;


}
