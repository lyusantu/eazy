package com.eazy.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * ajax 请求的返回类型封装JSON 结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignResult implements Serializable {

    private int status;

    private Object data;

    private String msg;

    public SignResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public SignResult(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
