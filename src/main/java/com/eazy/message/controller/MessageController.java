package com.eazy.message.controller;

import com.eazy.commons.Constants;
import com.eazy.message.entity.Message;
import com.eazy.message.service.MessageService;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/nums", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject nums(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("status", 0).put("count", 0);
        else
            return new JSONObject().put("status", 0).put("count", messageService.countMyMsg(user.getId()));
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject read(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if(ObjectUtil.isNotNull(user))
            messageService.emptyStatus(user.getId());
        return new JSONObject().put("status", 0);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject remove(HttpServletRequest request) {
        String id = request.getParameter("id");
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        messageService.removeMsg(id, user.getId());
        return new JSONObject().put("status", 0);
    }

}
