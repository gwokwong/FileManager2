package com.wells.filemanager.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wells on 16/4/23.
 */
public class Feedback extends BmobObject {

    public static int CALL_TYPE_QQ = 1;
    public static int CALL_TYPE_EMAIL = 2;
    public static int CALL_TYPE_PHONE = 3;
    public static int CALL_TYPE_WECHAT = 4;

    private String name;  //称呼

    private int callType;  //类别  1qq  2邮箱  3手机  4微信号

    private String msg; //建议或意见正文

    private String callNum;  //联系方式


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


    public String getCallNum() {
        return callNum;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "name='" + name + '\'' +
                ", callType=" + callType +
                ", msg='" + msg + '\'' +
                ", callNum='" + callNum + '\'' +
                '}';
    }


}
