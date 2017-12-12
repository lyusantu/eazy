package com.eazy.post.controller;

import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.Constants;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index() {
        return "post/add";
    }

    @RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxAdd(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "未登入");
        else
            return new AjaxResult(0, null, "/post/add");
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
        } else {
            LOG.info("从缓存中加载column");
        }
        return "post/add";
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult addPost(HttpServletRequest request, Post post) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "未登入");
        else if (user.getBalance() < post.getReward())
            return new AjaxResult(1, "飞吻不足");
        else {
            post.setAuthor(user.getId());
            post.setCreateTime(new Timestamp(System.currentTimeMillis()));
            postService.addPost(post);
            user.setBalance(user.getBalance() - post.getReward());
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setBalance(user.getBalance());
            userService.update(updateUser); // 更改用户飞吻
            request.getSession().setAttribute(Constants.LOGIN_USER, user);
            return new AjaxResult(0, null, "/"); // 此处应该跳转到用户发表的对应目录的首页
        }
    }
}
