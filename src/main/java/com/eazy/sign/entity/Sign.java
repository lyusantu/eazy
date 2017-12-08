package com.eazy.sign.entity;

import com.eazy.user.entity.User;

import java.sql.Timestamp;

public class Sign {

    private int id;

    private int uid;

    private Timestamp startTime;

    private Timestamp endTime;

    private User user;

    public Sign() {

    }

    public Sign(int id, Timestamp endTime) {
        this.id = id;
        this.endTime = endTime;
    }

    public Sign(int uid, Timestamp startTime, Timestamp endTime) {
        this.uid = uid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Sign(int id, int uid, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.uid = uid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", uid=" + uid +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
