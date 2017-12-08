package com.eazy.sign.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.dto.SignResult;
import com.eazy.sign.entity.Sign;
import com.eazy.sign.service.SignService;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.date.DateUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public SignResult in(HttpServletRequest request) throws IOException, ParseException {
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new SignResult(1, "请先登入");
        else {
            int reward = getSignInReward(1);
            Sign sign = signService.getSignInReocrd(user.getId());// 查询已签到记录
            if (sign == null) {
                sign = new Sign(user.getId(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
                json.put("days", 1).put("experience", getSignInReward(reward)).put("signed", true);
                signService.signIn(sign);
            } else {
                int days = 1;
                if (DateUtil.format(new Date(sign.getEndTime().getTime() + 1000 * 60 * 60 * 24), "yyyy-MM-dd").equals(DateUtil.format(new Date(), "yyyy-MM-dd"))) {
                    days = this.getDays(sign.getStartTime().getTime(), sign.getEndTime().getTime()) + 1;
                    sign = new Sign(sign.getId(), new Timestamp(System.currentTimeMillis()));
                } else
                    sign = new Sign(sign.getId(), user.getId(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
                signService.updateSignIn(sign);
                reward = this.getSignInReward(days);
                json.put("days", days).put("experience", reward).put("signed", true);
            }
            user.setBalance(user.getBalance() + reward);
            request.getSession().setAttribute(Constants.LOGIN_USER, user); // 增加奖励
        }
        return new SignResult(0, json);
    }


    // 初始化
    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SignResult status(HttpServletRequest request) throws IOException, ParseException {
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new SignResult(0, "未登入");
        else {
            Sign sign = signService.getSignInReocrd(user.getId());// 查询已签到记录
            int days = 0;
            if (ObjectUtil.isNull(sign)) {
                json.put("days", days).put("experience", getSignInReward(days)).put("signed", false);
            } else {
                if (DateUtil.format(new Date(sign.getEndTime().getTime()), "yyyy-MM-dd").equals(DateUtil.format(new Date(), "yyyy-MM-dd"))) { // 已签到
                    days = this.getDays(sign.getStartTime().getTime(), sign.getEndTime().getTime());
                    json.put("days", days).put("experience", getSignInReward(days)).put("signed", true);
                } else if (DateUtil.format(new Date(sign.getEndTime().getTime() + 1000 * 60 * 60 * 24), "yyyy-MM-dd").equals(DateUtil.format(new Date(), "yyyy-MM-dd"))) {
                    days = this.getDays(sign.getStartTime().getTime(), sign.getEndTime().getTime());
                    json.put("days", days).put("experience", getSignInReward(days)).put("signed", false);
                } else
                    json.put("days", days).put("experience", getSignInReward(days)).put("signed", false);
            }
            return new SignResult(0, json);
        }
    }

    // 活跃榜
    @RequestMapping(value = "/activeTopList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SignResult activeTopList(HttpServletRequest request) throws IOException, ParseException {
        JSONObject jsonNew = null;
        JSONArray arrayNew = new JSONArray();
        List<Sign> listNew = signService.listSignInNew();
        if (ObjectUtil.isNotNull(listNew) && listNew.size() > 0)
            for (Sign sign : listNew) {
                jsonNew = new JSONObject();
                jsonNew.put("uid", sign.getUser().getId()).
                        put("time", sign.getEndTime())
                        .put("user", new JSONObject().
                                put("username", sign.getUser().getNickName())
                                .put("avatar", sign.getUser().getAvatar()));
                arrayNew.put(jsonNew);
            }
        JSONObject jsonFast = null;
        JSONArray arrayFast = new JSONArray();
        List<Sign> listFast = signService.listSignInFast();
        if (ObjectUtil.isNotNull(listFast) && listFast.size() > 0) {
            for (Sign sign : listFast) {
                jsonFast = new JSONObject();
                jsonFast.put("uid", sign.getUser().getId())
                        .put("time", sign.getEndTime())
                        .put("user", new JSONObject()
                                .put("username", sign.getUser().getNickName())
                                .put("avatar", sign.getUser().getAvatar()));
                arrayFast.put(jsonFast);
            }
        }
        JSONObject jsonAll = null;
        JSONArray arrayAll = new JSONArray();
        List<Sign> listAll = signService.listSignInAll();
        if (ObjectUtil.isNotNull(listAll) && listAll.size() > 0) {
            for (Sign sign : listAll) {
                jsonAll = new JSONObject();
                jsonAll.put("uid", sign.getUser().getId())
                        .put("days", this.getDays(sign.getStartTime().getTime(), sign.getEndTime().getTime()))
                        .put("time", sign.getEndTime())
                        .put("user", new JSONObject()
                                .put("username", sign.getUser().getNickName())
                                .put("avatar", sign.getUser().getAvatar()));
                arrayAll.put(jsonAll);
            }
        }
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

    private int getDays(long start, long end) throws ParseException {
        start = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(start)).getTime();
        end = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(end)).getTime();
        long result = end - start;
        long day = 1000 * 60 * 60 * 24;
        return (int) (result / day + 1);
    }

}
