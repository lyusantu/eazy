package com.eazy.err.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/err")
@Controller
public class ErrController {

    @RequestMapping("/{code}")
    public String index(@PathVariable("code") String code) {
        return "err/" + code;
    }

}
