package com.eazy.index.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        List<Post> postList = postService.list(new Page(0, Constants.NUM_PER_PAGE), null, null); // 置顶帖
        List<Post> topList = new ArrayList<>();
        List<Post> otherList = new ArrayList<>();
        if (ObjectUtil.isNotNull(postList) && postList.size() > 0) {
            postList.forEach(
                    post -> {
                        if (post.getTop() == 0)
                            otherList.add(post);
                        else if (post.getTop() == 1)
                            topList.add(post);
                    }
            );
        }
        request.setAttribute("topList", topList);
        request.setAttribute("otherList", otherList);
        return "index";
    }

}
