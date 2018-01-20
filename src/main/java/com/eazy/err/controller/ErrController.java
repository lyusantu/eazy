package com.eazy.err.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/err")
@Controller
@Api(value = "/err", tags = "异常处理接口")
public class ErrController {

    @RequestMapping("/{code}")
    public String index(@PathVariable("code") String code) {
        return "err/" + code;
    }

}
