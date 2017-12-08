package com.eazy.sign.entity;

import com.eazy.user.entity.User;

import java.sql.Timestamp;

public class Sign {

    private int id;

    private int uid;

    private Timestamp time;

    private int status;

    private User user;

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", uid=" + uid +
                ", time=" + time +
                ", status=" + status +
                ", user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
