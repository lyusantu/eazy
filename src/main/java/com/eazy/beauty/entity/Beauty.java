package com.eazy.beauty.entity;

import com.eazy.user.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;

public class Beauty implements Serializable {

    private Integer id;

    private Integer uid;

    private String pic;

    private Timestamp time;

    private Integer praise;

    private Integer status;

    private User user;

    public Beauty() {
    }

    public Beauty(Integer uid, String pic, Timestamp time, Integer praise, Integer status) {
        this.uid = uid;
        this.pic = pic;
        this.time = time;
        this.praise = praise;
        this.status = status;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

