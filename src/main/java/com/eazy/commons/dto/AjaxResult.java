package com.eazy.commons.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * ajax 请求的返回类型封装JSON结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResult<T> implements Serializable {

    private int status;

    private String msg;

    private String action;

    public AjaxResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public AjaxResult(int status, String msg, String action) {
        this.status = status;
        this.msg = msg;
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}