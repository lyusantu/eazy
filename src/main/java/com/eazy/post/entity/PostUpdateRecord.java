package com.eazy.post.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostUpdateRecord implements Serializable {

    private Integer id;

    private Integer pid;

    private Timestamp time;

    public PostUpdateRecord() {
    }

    public PostUpdateRecord(Integer pid, Timestamp time) {
        this.pid = pid;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
