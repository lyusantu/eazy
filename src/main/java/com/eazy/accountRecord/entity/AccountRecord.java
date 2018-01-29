package com.eazy.accountRecord.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccountRecord implements Serializable {

    private Integer id;

    private Integer uid;

    private Timestamp time;

    /**
     * 0 = 收入
     * 1 = 支出
     */
    private Integer type;

    private Integer num;

    private Integer balance;

    public AccountRecord() {
    }

    /**
     * 0 = 发送感谢
     * 1 = 获取感谢
     * 2 = 每日签到奖励
     * 3 = 主题回复收益
     * 4 = 创建回复
     * 5 = 创建主题
     * 6 = 初始奖励
     */
    private Integer way;

    private String desc;

    public AccountRecord(Integer uid, Integer type, Integer num, Integer way, Integer balance, String desc, Timestamp time) {
        this.uid = uid;
        this.time = time;
        this.type = type;
        this.num = num;
        this.way = way;
        this.desc = desc;
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
