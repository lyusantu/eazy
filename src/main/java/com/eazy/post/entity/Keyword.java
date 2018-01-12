package com.eazy.post.entity;

import java.sql.Timestamp;

public class Keyword {

    private Integer id;

    private String keyword;

    private Integer pid;

    private Timestamp time;

    public Keyword() {
    }

    public Keyword(String keyword, Integer pid, Timestamp time) {
        this.keyword = keyword;
        this.pid = pid;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
