package com.eazy.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xiaoleilu.hutool.json.JSONObject;

import java.io.Serializable;

/**
 * ajax 请求的返回类型封装JSON 结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignResult implements Serializable {

    private int status;

    private JSONObject data;

    public SignResult(int status, JSONObject data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

}
