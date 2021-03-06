package com.eazy.index.controller;

import com.eazy.collection.service.CollectionService;
import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.index.entity.FriendsSite;
import com.eazy.index.service.IndexService;
import com.eazy.message.service.MessageService;
import com.eazy.post.entity.Post;
import com.eazy.post.entity.Reply;
import com.eazy.post.service.PostService;
import com.eazy.post.service.ReplyService;
import com.eazy.user.entity.User;
import com.eazy.user.entity.UserFB;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "/", tags = "index")
public class IndexController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        List<Post> postList = postService.list(new Page(0, Constants.NUM_PER_PAGE), null, null, null); // 置顶帖
        Column column = new Column(0); // 一级菜单
        List<Column> columnList = columnService.listColumn(column);
        Page page = new Page(0, Constants.NUM_PER_PAGE);
        page.setPageNumber(1);
        page.setTotalCount(postService.count(null, null, null));
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.TAB1, columnList);
        request.setAttribute(Constants.TAB1_SELECT, Constants.TAB_SELECT_ALL);
        request.setAttribute(Constants.POST_LIST, postList);
        request.setAttribute(Constants.REPLY_LIST, replyService.weeklyTop());// 回帖周榜
        request.setAttribute(Constants.HOT_WEEKLY_LIST, postService.weeklyTop());// 本周热议
        request.setAttribute(Constants.FS_LIST, indexService.listFriendsSite());// 友链
        request.setAttribute(Constants.KEYWORD_LIST, indexService.listKeyword());// 最热标签
        User user = Constants.getLoginUser(request);
        if(ObjectUtil.isNotNull(user)){
            request.setAttribute("countPost",postService.countMyPost(user.getId()));
            request.setAttribute("countCollection",collectionService.countMyCollection(user.getId()));
            request.setAttribute("countMessage",messageService.countMyMsg(user.getId()));
            UserFB ufb = new UserFB(user.getId(), 0);
            request.setAttribute("countFollow", userService.countUserFB(ufb));
        }
        request.setAttribute("countAllPost", postService.countAllPost());
        request.setAttribute("members", userService.countUser() );// 注册会员
        request.setAttribute("countAllReply", replyService.countAllReply());
        return Constants.URL_INDEX;
    }

}
