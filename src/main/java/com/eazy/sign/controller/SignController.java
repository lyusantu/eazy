package com.eazy.sign.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.dto.SignResult;
import com.eazy.sign.entity.Sign;
import com.eazy.sign.service.SignService;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/sign")
public class SignController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SignService signService;

    // 签到
    @RequestMapping(value = "/in", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SignResult in(HttpServletRequest request) throws IOException {
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new SignResult(1, "请先登入");
        else {
            int count = signService.getSignInReocrd(user.getId());// 查询已签到记录
            Sign sign = new Sign();
            sign.setStatus(0);
            sign.setUid(user.getId());
            sign.setTime(new Timestamp(System.currentTimeMillis()));
            signService.signIn(sign);
            if (count == 0) { // 第一次签到
                json.put("days", 1).put("experience", getSignInReward(1)).put("signed", true);
            } else {
                count++;
                json.put("days", count).put("experience", getSignInReward(count)).put("signed", true);
            }
            return new SignResult(0, json);
        }
    }

    // 初始化
    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SignResult status(HttpServletRequest request) throws IOException {
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new SignResult(0, "未登入");
        else {
            int count = signService.getSignInReocrd(user.getId());// 查询已签到记录
            json.put("days", count).put("experience", getSignInReward(count)).put("signed", signService.status(user.getId()));
            return new SignResult(0, json);
        }
    }

    // 初始化
    @RequestMapping(value = "/activeTopList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SignResult activeTopList(HttpServletRequest request) throws IOException {
        JSONObject jsonNew = null;
        JSONArray arrayNew = new JSONArray();
        List<Sign> listNew = signService.listSignInNew();
        if (listNew != null && listNew.size() > 0)
            for (Sign sign : listNew) {
                jsonNew = new JSONObject();
                jsonNew.put("uid", sign.getUser().getId()).put("time", sign.getTime()).put("user", new JSONObject().put("username", sign.getUser().getNickName()).put("avatar", sign.getUser().getAvatar()));
                arrayNew.put(jsonNew);
            }
        JSONObject jsonFast = null;
        JSONArray arrayFast = new JSONArray();
        List<Sign> listFast = signService.listSignInFast();
        if (listFast != null && listFast.size() > 0)
            for (Sign sign : listFast) {
                jsonFast = new JSONObject();
                jsonFast.put("uid", sign.getUser().getId()).put("time", sign.getTime()).put("user", new JSONObject().put("username", sign.getUser().getNickName()).put("avatar", sign.getUser().getAvatar()));
                arrayFast.put(jsonFast);
            }
        JSONObject jsonAll = new JSONObject();
        jsonAll.put("uid", 1).put("days", 99).put("time", "2017-12-7 21:37:47").put("user", new JSONObject().put("username", "志远").put("avatar", "http://q.qlogo.cn/qqapp/101235792/3C039374444019CF9C93F6AA00D5A66E/100"));
        JSONArray arrayAll = new JSONArray();
        arrayAll.put(jsonAll);
        return new SignResult(0, new JSONArray().put(arrayNew).put(arrayFast).put(arrayAll));
    }

    /**
     * 根据签到天数获取对应奖励
     *
     * @param days
     * @return
     */
    private int getSignInReward(int days) {
        if (days >= 30)
            return 20;
        else if (days >= 15)
            return 15;
        else if (days >= 5)
            return 10;
        else
            return 5;
    }

}
