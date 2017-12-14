package com.eazy.column.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{column}/{type}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("column") String column, @PathVariable("type") String type) {
        String p = request.getParameter("p");
        String order = request.getParameter("order");
        System.out.println(order==null);
        Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
        page.setTotalCount(postService.count(column, type));
        page.setTotalCount(1000);
        List<Post> postList = postService.list(page, column, type, order);
        request.setAttribute("page", page);
        request.setAttribute("postList", postList);
        request.setAttribute("tab_column", column); // curr check
        request.setAttribute("tab_filter", type);
        request.setAttribute("tab_order", order);
        return "t/index";
    }

}
