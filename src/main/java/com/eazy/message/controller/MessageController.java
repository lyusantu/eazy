package com.eazy.message.controller;

import com.xiaoleilu.hutool.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController {

    @RequestMapping(value = "/nums", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject nums() {
        return new JSONObject().put("status", 0).put("count", 0);
    }

}
