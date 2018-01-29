package com.eazy.column.entity;

import java.io.Serializable;

public class Column implements Serializable {

    private Integer id;

    private String name;

    private String suffix;

    private Integer pid;

    private String desc;

    public Column() {
    }

    public Column(Integer pid) {
        this.pid = pid;
    }

    public Column(Integer pid, String desc) {
        this.pid = pid;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
