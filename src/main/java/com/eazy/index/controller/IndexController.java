package com.eazy.index.controller;

import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        LOG.info("invoke----------index");
        request.setAttribute("tab_column", "home");
        List<Post> postList = postService.listTop(); // 置顶帖
        request.setAttribute("postList", postList);
        return "index";
    }

}
