package com.eazy.post.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostRewardRecord implements Serializable {

    private Integer id;

    private Integer uid;

    private Integer pid;

    private Integer num;

    private Timestamp time;

    public PostRewardRecord() {
    }

    public PostRewardRecord(Integer uid, Integer pid, Integer num, Timestamp time) {
        this.uid = uid;
        this.pid = pid;
        this.num = num;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
