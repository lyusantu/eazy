package com.eazy.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserFB implements Serializable {

    private Integer id;

    private Integer uid;

    private Integer fid;

    private Integer type;

    private Timestamp time;

    public UserFB() {
    }

    public UserFB(Integer uid, Integer type) {
        this.uid = uid;
        this.type = type;
    }

    public UserFB(Integer uid, Integer fid, Integer type) {
        this.uid = uid;
        this.fid = fid;
        this.type = type;
    }

    public UserFB(Integer uid, Integer fid, Timestamp time) {
        this.uid = uid;
        this.fid = fid;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
