package com.eazy.post.controller;

import com.eazy.collection.entity.PostCollection;
import com.eazy.collection.service.CollectionService;
import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.Constants;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("id") int id) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNotNull(user)) { // 是否收藏
            PostCollection collection = new PostCollection(id, user.getId());
            collection = collectionService.verifyCollection(collection);
            request.setAttribute("collection", collection);
        }
        Post post = postService.getPost(id);
        post.setReaders(post.getReaders() + 1);
        Post updatePost = new Post(post.getId(), post.getReaders());
        postService.update(updatePost);
        request.setAttribute("post", post);
        request.setAttribute("tab_order", request.getParameter("order"));
        request.setAttribute("tab_column", post.getColumn().getSuffix());
        return "post/index";
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
        Verify verify = verifyService.randVerify();
        request.setAttribute("verify", verify);
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
            Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
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

    @AuthPassport
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        postService.delete(id);
        return new AjaxResult(0, null, null, "/");
    }

    @AuthPassport
    @RequestMapping(value = "/collection/{type}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult collection(HttpServletRequest request, @PathVariable("type") String type) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "请先登入");
        else {
            int id = Integer.parseInt(request.getParameter("cid"));
            PostCollection collection = new PostCollection(id, user.getId(), new Timestamp(System.currentTimeMillis()));
            if (type.equals("add"))
                collectionService.addCollection(collection);
            else if (type.equals("remove"))
                collectionService.removeCollection(collection);
            return new AjaxResult(0);
        }
    }
}
