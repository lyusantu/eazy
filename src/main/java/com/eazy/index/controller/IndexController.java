package com.eazy.index.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        LOG.info("invoke----------index");
        request.setAttribute("tab_column", "home");
        return "index";
    }

}
