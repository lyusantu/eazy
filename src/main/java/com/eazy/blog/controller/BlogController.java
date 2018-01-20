package com.eazy.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request) {

        return "blog/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(HttpServletRequest request) {
        String content = request.getParameter("content");
        request.setAttribute("content",content);
        return "blog/show";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(HttpServletRequest request) {

        return "blog/show";
    }

}
