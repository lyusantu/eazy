package com.eazy.post.controller;

import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.post.service.PostService;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private ColumnService columnService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index() {
        return "post/add";
    }

    @AuthPassport
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request) {
        Column column = new Column();
        column.setRole(1);
        List<Column> listColumn = (List<Column>) request.getSession().getAttribute("listColumn");
        if (ObjectUtil.isNull(listColumn) || listColumn.size() == 0) {
            LOG.info("从数据中查询column");
            listColumn = columnService.listColumn(column);
            request.getSession().setAttribute("listColumn", listColumn);
        }else{
            LOG.info("从缓存中加载column");
        }
        return "post/add";
    }
}
