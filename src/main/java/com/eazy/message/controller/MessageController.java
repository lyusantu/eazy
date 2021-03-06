package com.eazy.message.controller;

import com.eazy.commons.Constants;
import com.eazy.message.entity.Message;
import com.eazy.message.service.MessageService;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/message")
@Api(value = "/message", tags = "消息接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/nums", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject nums(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_COUNT, 0);
        else
            return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_COUNT, messageService.countMyMsg(user.getId()));
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject read(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNotNull(user))
            messageService.emptyStatus(user.getId());
        return new JSONObject().put(Constants.SET_STATUS, 0);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject remove(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "未登入");
        String id = request.getParameter(Constants.GET_ID);
        String all = request.getParameter(Constants.GET_ALL);
        if (ObjectUtil.isNull(all)) {
            Message message = messageService.getMsg(Integer.parseInt(id), user.getId());
            if (ObjectUtil.isNull(message))
                return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "不要试图删除不是你的消息");
        }
        messageService.removeMsg(id, user.getId());
        return new JSONObject().put(Constants.SET_STATUS, 0);
    }

}
