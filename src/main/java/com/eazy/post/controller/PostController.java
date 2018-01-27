package com.eazy.post.controller;

import com.eazy.collection.entity.PostCollection;
import com.eazy.collection.service.CollectionService;
import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.index.service.IndexService;
import com.eazy.message.entity.Message;
import com.eazy.message.service.MessageService;
import com.eazy.post.entity.Keyword;
import com.eazy.post.entity.Post;
import com.eazy.post.entity.PostUpdateRecord;
import com.eazy.post.entity.Reply;
import com.eazy.post.service.PostService;
import com.eazy.post.service.ReplyService;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.ansj.app.keyword.KeyWordComputer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Transactional
@RequestMapping("/post")
@Api(value = "/post", tags = "主题接口")
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
    private ReplyService replyService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable(Constants.GET_ID) int id) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNotNull(user)) { // 是否收藏
            PostCollection collection = new PostCollection(id, user.getId());
            collection = collectionService.verifyCollection(collection);
            request.setAttribute(Constants.SET_COLLECTION, collection);
        }
        Post post = postService.getPost(id);
        if(ObjectUtil.isNull(post)){
            request.setAttribute(Constants.SET_MSG, "主题不存在");
            return "err/err";
        }
        if (post.getDelete() == 0) {
            List<Column> columnList = columnService.listColumn(new Column(0));
            Column column = new Column(columnService.getPidById(post.getType())); // 获取pid
            List<Column> columnList1 = columnService.listColumn(column);
            for (Column column1 : columnList) {
                if (column1.getId().equals(columnList1.get(0).getPid())) {
                    request.setAttribute(Constants.TAB1_SELECT, column1.getSuffix());
                    break;
                }
            }
            post.setReaders(post.getReaders() + 1);
            Post updatePost = new Post(post.getId(), post.getReaders());
            postService.update(updatePost);
            request.setAttribute(Constants.ENTITY_POST, post);
            // 加载主题更改历史
            List<PostUpdateRecord> postUpdateRecordList = postService.listPostUpdateRecord(post.getId());
            request.setAttribute(Constants.LIST_POST_UPDATE_RECORD, postUpdateRecordList);
            // 加载评论
            Reply reply = new Reply(id);
            String p = request.getParameter(Constants.GET_P);
            Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
            page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
            page.setTotalCount(replyService.countListReply(reply));
            List<Reply> replyList = replyService.listReply(reply, page);
            request.setAttribute(Constants.PAGE, page);
            request.setAttribute(Constants.TAB1, columnList);
            request.setAttribute(Constants.LIST, replyList);
            request.setAttribute(Constants.TITLE, post.getTitle());
            request.setAttribute(Constants.WEEK_HOT, postService.weeklyTop());// 本周热议
        }
        request.setAttribute(Constants.KEYWORDS, postService.getKeyword(post.getId())); // 关键字
        request.setAttribute(Constants.SET_MSG, "该主题已被删除");
        return post.getDelete() == 0 ? Constants.URL_POST_INDEX : Constants.URL_ERR_ERR;
    }

    @RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxAdd(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "未登入");
        else
            return new AjaxResult(0, null, Constants.URL_POST_ADD);
    }

    @AuthPassport
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "创建新主题");
        request.setAttribute(Constants.VERIFY, verifyService.randVerify());
        request.setAttribute(Constants.LIST_TYPE, columnService.listColumnSecondary());
        request.setAttribute(Constants.TAB1, columnService.listColumn(new Column(0)));
        return Constants.URL_POST_ADD;
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
            if (post.getReward() < 20)
                return new AjaxResult(1, "不要试图更改飞吻值");
            Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
            else {
                post.setAllow(ObjectUtil.isNull(request.getParameter("post_allow")) ? 1 : 0);
                post.setAuthor(user.getId());
                post.setCreateTime(new Timestamp(System.currentTimeMillis()));
                postService.addPost(post); // 添加文章
                user.setBalance(user.getBalance() - post.getReward());
                User updateUser = new User(user.getId(), user.getBalance());
                userService.update(updateUser); // 更改用户飞吻
                request.getSession().setAttribute(Constants.LOGIN_USER, user);
                KeyWordComputer kwc = new KeyWordComputer(5); // 分词,关键字提取
                List<org.ansj.app.keyword.Keyword> keywords = kwc.computeArticleTfidf(post.getTitle(), post.getContent());
                if (ObjectUtil.isNotNull(keywords) && keywords.size() > 0) {
                    keywords.forEach(
                            keyword -> postService.addKeyword(new Keyword(keyword.getName(), post.getId(), new Timestamp(System.currentTimeMillis())))
                    );
                }
                push(post.getTitle(), post.getContent(),post.getId(), user.getId(), 0,3); // 推送
                return new AjaxResult(0, null, "/"); // 此处应该跳转到用户发表的对应目录的首页
            }
        }

    }

    @AuthPassport
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult delete(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (!user.getType().equalsIgnoreCase(Constants.ROLE_ADMIN))
            return new AjaxResult(1, "权限不足");
        int id = Integer.parseInt(request.getParameter(Constants.GET_ID));
        postService.delete(id);
        postService.delKeyword(id);
        return new AjaxResult(0, null, null, "/");
    }

    @AuthPassport
    @RequestMapping(value = "/collection/{type}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult collection(HttpServletRequest request, @PathVariable(Constants.GET_TYPE) String type) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "请先登入");
        else {
            int id = Integer.parseInt(request.getParameter(Constants.GET_CID));
            PostCollection collection = new PostCollection(id, user.getId(), new Timestamp(System.currentTimeMillis()));
            if (type.equals("add"))
                collectionService.addCollection(collection);
            else if (type.equals("remove"))
                collectionService.removeCollection(collection);
            return new AjaxResult(0);
        }
    }

    // 评论
    @RequestMapping(value = "/reply", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject reply(HttpServletRequest request, Reply reply) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "未登录");
        else {
            Post post = postService.getPost(reply.getPid());
            if (!user.getId().equals(post.getAuthor())) { // 奖赏制度
                if(user.getBalance() < Constants.DEFAULT_REDUCE)
                    return new JSONObject().put(Constants.SET_STATUS, 1).put(Constants.SET_MSG, "您的飞吻不足，无法评论");
                // 扣除评论人的飞吻
                User updateUser = new User(user.getId(), user.getBalance() - Constants.DEFAULT_REDUCE);
                userService.update(updateUser);
                Constants.resetUserInfo(request, user);
                // 为主题发表者添加飞吻
                User postUser = userService.getUser(new User(post.getAuthor()));
                updateUser = new User(postUser.getId(), postUser.getBalance() + Constants.DEFAULT_REDUCE);
                userService.update(updateUser);
            }
            // 添加评论
            reply.setUid(user.getId());
            reply.setAccept(0);
            reply.setTime(new Timestamp(System.currentTimeMillis()));
            replyService.addReply(reply);
            // 更新评论数
            Post updatePost = new Post(post.getId());
            updatePost.setComments(post.getComments() + 1);
            postService.update(updatePost);
            this.push(post.getTitle(), reply.getContent(),post.getId(), user.getId(), reply.getId(), 1); // @的推送
            LOG.info("----------用户\"" + user.getNickName() + "\"评论了主题\"" + post.getTitle() + "\"----------");
            LOG.info("----------开始进行评论的推送----------");
            if(!post.getAuthor().equals(user.getId())) {
                Message message = new Message(post.getId(), user.getId(), post.getAuthor(), 0, null, new Timestamp(System.currentTimeMillis()), 0, reply.getId());
                messageService.addMsg(message);
                LOG.info("----------成功向主题所有者推送了一条来自用户\""  + user.getNickName() + "\"的评论信息----------");
            }
            else
                LOG.info("----------\""  + user.getNickName() + "\"评论了自己的主题\"" + post.getTitle() +"\"，取消评论的推送----------");
            LOG.info("----------评论的推送结束----------");
            return new JSONObject().put(Constants.SET_STATUS, 0).put(Constants.SET_MSG, "评论成功").put(Constants.SET_ACTION, false);
        }
    }

    // 设置置顶加精
    @AuthPassport
    @RequestMapping(value = "/set", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult set(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (user.getType().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            String id = request.getParameter(Constants.GET_ID);
            String rank = request.getParameter(Constants.GET_RANK);
            String field = request.getParameter(Constants.GET_FIEID);
            postService.set(id, rank, field);
            return new AjaxResult(0);
        } else
            return new AjaxResult(1, "权限不足");
    }

    // 采纳
    @AuthPassport
    @RequestMapping(value = "/accept", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult accept(HttpServletRequest request) {
        String id = request.getParameter(Constants.GET_ID);
        replyService.update(id);
        Post post = new Post(Integer.parseInt(request.getParameter(Constants.GET_PID)));
        post.setStatus(1);
        postService.update(post);
        Reply reply = new Reply();
        reply.setId(Integer.parseInt(id));
        reply = replyService.getReply(reply);
        post = postService.getPost(reply.getPid());
        User user = new User(reply.getUid());
        User updateUser = userService.getUser(user);
        user.setBalance(updateUser.getBalance() + post.getReward());
        userService.update(user);
        updateUser.setBalance(user.getBalance());
        return new AjaxResult(0);
    }

    // 删除评论
    @AuthPassport
    @RequestMapping(value = "/delReply", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult delReply(HttpServletRequest request) {
        String id = request.getParameter(Constants.GET_ID);
        Reply reply = new Reply();
        reply.setId(Integer.parseInt(id));
        reply = replyService.getReply(reply);
        if (reply.getAccept() == 1) {
            Post post = new Post(reply.getPid());
            post.setStatus(0);
            postService.update(post); // 改为未采纳,从用户账户内扣除飞吻
            post = postService.getPost(post.getId());
            User user = new User(reply.getUid());
            User updateUser = userService.getUser(user);
            user.setBalance(updateUser.getBalance() - post.getReward()); // 更改用户余额
            userService.update(user);
        }
        Post post = postService.getPost(reply.getPid());
        Post updatePost = new Post(post.getId());
        updatePost.setComments(post.getComments() - 1); // 删除评论统计
        postService.update(updatePost);
        replyService.delReply(reply); // 删除评论
        return new AjaxResult(0);
    }

    @AuthPassport
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @PathVariable(Constants.GET_ID) int id) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        Post post = postService.getPost(id);
        if (post == null) {
            request.setAttribute(Constants.SET_MSG, "主题不存在");
            return Constants.URL_ERR_ERR;
        } else {
            if (post.getDelete() == 1)
                request.setAttribute(Constants.SET_MSG, "该主题已被删除");
            else {
                if (post.getAuthor() != user.getId()) {
                    request.setAttribute(Constants.SET_MSG, "不要试图修改不是你的主题");
                    return Constants.URL_ERR_ERR;
                } else {
                    request.setAttribute(Constants.TITLE, "编辑主题 - " + post.getTitle());
                    request.setAttribute(Constants.TAB1, columnService.listColumn(new Column(0)));
                    Verify verify = verifyService.randVerify();
                    request.setAttribute(Constants.VERIFY, verify);
                    request.setAttribute(Constants.ENTITY_POST, post);
                    request.setAttribute(Constants.LIST_TYPE, columnService.listColumnSecondary());
                }
            }
            return post.getDelete() == 0 ? Constants.URL_POST_EDIT : Constants.URL_ERR_ERR;
        }
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult updatePost(HttpServletRequest request, Post post) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "未登入");
        else {
            Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
            else {
                post.setAllow(ObjectUtil.isNull(request.getParameter("post_allow")) ? 1 : 0);
                postService.update(post);
                postService.delKeyword(post.getId());// 删除已有关键字
                KeyWordComputer kwc = new KeyWordComputer(5); // 分词,关键字提取
                List<org.ansj.app.keyword.Keyword> keywords = kwc.computeArticleTfidf(post.getTitle(), post.getContent());
                if (ObjectUtil.isNotNull(keywords) && keywords.size() > 0) {
                    keywords.forEach(
                            keyword -> postService.addKeyword(new Keyword(keyword.getName(), post.getId(), new Timestamp(System.currentTimeMillis())))
                    );
                }
                push(post.getTitle(), post.getContent(),post.getId(), user.getId(), 0,3); // 推送
                postService.addPostUpdateRecord(new PostUpdateRecord(post.getId(), new Timestamp(System.currentTimeMillis())));// 插入一条更改记录
                return new AjaxResult(0, null, "/post/" + post.getId()); // 此处应该跳转到用户发表的对应目录的首页
            }
        }
    }

    @RequestMapping(value = "/thanks", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult thanks(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new AjaxResult(1, "未登入");
        else {
            if(user.getBalance() < Constants.DEFAULT_THANKS)
                return new AjaxResult(1, "飞吻不足");
            Reply reply = new Reply();
            reply.setId(Integer.parseInt(request.getParameter("id")));
            reply = replyService.getReply(reply);
            User updateUser = new User(reply.getUid());
            User getUser = userService.getUser(updateUser);
            updateUser.setBalance(getUser.getBalance() + Constants.DEFAULT_THANKS);
            userService.update(updateUser);
            updateUser = new User(user.getId());
            user.setBalance(user.getBalance() - Constants.DEFAULT_THANKS);
            updateUser.setBalance(user.getBalance());
            userService.update(updateUser);
            Constants.resetUserInfo(request, user);
            Post post = postService.getPost(reply.getPid());
            // 感谢成功后进行感谢推送
            Message message = new Message(post.getId(), user.getId(), reply.getUid(), 4, null, new Timestamp(System.currentTimeMillis()), 0, reply.getId());
            messageService.addMsg(message);
            return new AjaxResult(0, "感谢成功");
        }
    }

    @RequestMapping(value = "/tags/{tag}", method = RequestMethod.GET)
    public String tags(HttpServletRequest request, @PathVariable(Constants.GET_TAG) String tag) {
        String p = request.getParameter(Constants.GET_P);
        String type = request.getParameter(Constants.GET_TYPE);
        Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
        page.setTotalCount(postService.countTags(type, tag));
        List<Post> postList = postService.listTags(page, type, tag);
        request.setAttribute(Constants.TITLE, "标签：" + tag);
        request.setAttribute(Constants.SEARCH_TAG, tag);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.POST_LIST, postList);
        request.setAttribute(Constants.TYPE, type);
        request.setAttribute(Constants.REPLY_LIST, replyService.weeklyTop());// 回帖周榜
        request.setAttribute(Constants.HOT_WEEKLY_LIST, postService.weeklyTop());// 本周热议
        request.setAttribute(Constants.FS_LIST, indexService.listFriendsSite());// 友链
        request.setAttribute(Constants.SPONSOR_LIST, indexService.listSponsor(1));
        request.setAttribute(Constants.KEYWORD_LIST, indexService.listKeyword());// 最热标签
        return Constants.URL_POST_TAGS;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request) {
        String p = request.getParameter(Constants.GET_P);
        String search = request.getParameter(Constants.GET_Q);
        String type = request.getParameter(Constants.GET_TYPE);
        Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
        page.setTotalCount(postService.countSearch(type, search));
        List<Post> postList = postService.listSearch(page, type, search);
        request.setAttribute(Constants.TITLE, "搜索：" + search);
        request.setAttribute(Constants.SEARCH_TAG, search);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.POST_LIST, postList);
        request.setAttribute(Constants.TYPE, type);
        request.setAttribute(Constants.REPLY_LIST, replyService.weeklyTop());// 回帖周榜
        request.setAttribute(Constants.HOT_WEEKLY_LIST, postService.weeklyTop());// 本周热议
        request.setAttribute(Constants.FS_LIST, indexService.listFriendsSite());// 友链
        request.setAttribute(Constants.SPONSOR_LIST, indexService.listSponsor(1));
        request.setAttribute(Constants.KEYWORD_LIST, indexService.listKeyword());// 最热标签
        return Constants.URL_POST_TAGS;
    }

    /**
     * 推送
     * @param title
     * @param content
     * @param postId
     * @param userId
     * @param type
     */
    private void push(String title, String content, Integer postId, Integer userId, int replyId, int type){
        LOG.info("----------开始进行@的推送----------");
        String reg = "@.\\S*";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(content);
        String name = "";
        Message message = null;
        User queryUser = null;
        while (mat.find()) {
            name = mat.group();
            name = name.substring(1, name.length());
            queryUser = userService.getUserByName(name); // 查询用户是否存在
            if (ObjectUtil.isNull(queryUser)) {
                LOG.info("----------@的用户 " + name + " 不存在----------");
            } else {
                if(!queryUser.getId().equals(userId)) {
                    message = new Message(postId, userId, queryUser.getId(), type, null, new Timestamp(System.currentTimeMillis()), 0, replyId);
                    messageService.addMsg(message);
                    LOG.info("----------成功向用户\"" + name + "\"推送了一条来自主题\"" + title + "\"中的@信息----------");
                }else
                    LOG.info("----------\""+ name +"\"在主题\"" + title + "\"中@了自己，取消@的推送----------");
            }
        }
        LOG.info("----------@的推送结束----------");
    }

}
