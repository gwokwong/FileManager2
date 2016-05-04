package com.wells.filemanager.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by wells on 16/5/4.
 */
public class Version extends BmobObject {

    private String update_log;
    private String version;
    private Integer version_i;
    private Boolean isforce;
    private BmobFile path;
    private String target_size;

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getVersion_i() {
        return version_i;
    }

    public void setVersion_i(Integer version_i) {
        this.version_i = version_i;
    }

    public Boolean getIsforce() {
        return isforce;
    }

    public void setIsforce(Boolean isforce) {
        this.isforce = isforce;
    }

    public BmobFile getPath() {
        return path;
    }

    public void setPath(BmobFile path) {
        this.path = path;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    @Override
    public String toString() {
        return "Version{" +
                "update_log='" + update_log + '\'' +
                ", version='" + version + '\'' +
                ", version_i=" + version_i +
                ", isforce=" + isforce +
                ", path=" + path +
                ", target_size='" + target_size + '\'' +
                '}';
    }
}
