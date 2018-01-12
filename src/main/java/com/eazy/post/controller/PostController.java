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
import com.eazy.post.entity.Reply;
import com.eazy.post.service.PostService;
import com.eazy.post.service.ReplyService;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.ObjectUtil;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Transactional
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
    private ReplyService replyService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @PathVariable("id") int id) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNotNull(user)) { // 是否收藏
            PostCollection collection = new PostCollection(id, user.getId());
            collection = collectionService.verifyCollection(collection);
            request.setAttribute("collection", collection);
        }
        Post post = postService.getPost(id);
        if (post.getDelete() == 0) {
            List<Column> columnList = (List<Column>) request.getSession().getAttribute("tab1");
            if (ObjectUtil.isNull(columnList)) {
                columnList = columnService.listColumn(new Column(0));
                request.getSession().setAttribute("tab1", columnList);
            }
            Column column = new Column(columnService.getPidById(post.getType())); // 获取pid
            List<Column> columnList1 = columnService.listColumn(column);
            request.setAttribute("tab2", columnList1);
            for (Column column1 : columnList) {
                if (column1.getId().equals(columnList1.get(0).getPid())) {
                    request.setAttribute("tab1_select", column1.getSuffix());
                    break;
                }
            }
            for (Column column1 : columnList1) {
                if (column1.getId().equals(post.getType())) {
                    request.setAttribute("tab2_select", column1.getSuffix());
                    break;
                }
            }

            post.setReaders(post.getReaders() + 1);
            Post updatePost = new Post(post.getId(), post.getReaders());
            postService.update(updatePost);
            request.setAttribute("post", post);
            // 加载评论
            Reply reply = new Reply(id);
            String p = request.getParameter("p");
            Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
            page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
            page.setTotalCount(replyService.countListReply(reply));
            List<Reply> replyList = replyService.listReply(reply, page);
            request.setAttribute("list", replyList);
            request.setAttribute("page", page);
            request.setAttribute("weekHot", postService.weeklyTop());// 本周热议
            request.setAttribute("sponsorList", indexService.listSponsor(2));
            request.setAttribute(Constants.TITLE, post.getTitle());
        }
        request.setAttribute("keywords", postService.getKeyword(post.getId())); // 关键字
        request.setAttribute("msg", "该主题已被删除");
        return post.getDelete() == 0 ? "post/index" : "err/err";
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
        request.setAttribute(Constants.TITLE, "创建新主题");
        request.setAttribute("verify", verifyService.randVerify());
        request.setAttribute("listType", columnService.listColumnSecondary());
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
            System.out.println(post.getReward());
            if (post.getReward() < 20)
                return new AjaxResult(1, "不要试图更改飞吻值");
            Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
            else {
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
        int id = Integer.parseInt(request.getParameter("id"));
        postService.delete(id);
        postService.delKeyword(id);
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

    // 评论
    @RequestMapping(value = "/reply", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    JSONObject reply(HttpServletRequest request, Reply reply) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(user))
            return new JSONObject().put("status", 1).put("msg", "未登录");
        else {
            reply.setUid(user.getId());
            reply.setAccept(0);
            reply.setTime(new Timestamp(System.currentTimeMillis()));
            replyService.addReply(reply);
            Post post = new Post(reply.getPid());
            post = postService.getPost(post.getId());
            Post updatePost = new Post(post.getId());
            updatePost.setComments(post.getComments() + 1);
            postService.update(updatePost);
            LOG.info("----------用户 " + user.getNickName() + " 评论了 " + post.getTitle() + "----------");
            LOG.info("----------开始进行@的推送----------");
            String reg = "@.\\S*";
            Pattern pat = Pattern.compile(reg);
            Matcher mat = pat.matcher(reply.getContent());
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
                    message = new Message(post.getId(), user.getId(), queryUser.getId(), 1, null, new Timestamp(System.currentTimeMillis()), 0, reply.getId());
                    messageService.addMsg(message);
                    LOG.info("----------成功向用户 " + name + " 推送了一条信息----------");
                }
            }
            LOG.info("----------@的推送结束----------");
            LOG.info("----------开始进行评论的推送----------");
            message = new Message(post.getId(), user.getId(), post.getAuthor(), 0, null, new Timestamp(System.currentTimeMillis()), 0, reply.getId());
            messageService.addMsg(message);
            LOG.info("----------评论的推送结束----------");
            return new JSONObject().put("status", 0).put("msg", "评论成功").put("action", false);
        }
    }

    // 设置置顶加精
    @AuthPassport
    @RequestMapping(value = "/set", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult set(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (user.getType().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            String id = request.getParameter("id");
            String rank = request.getParameter("rank");
            String field = request.getParameter("field");
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
        String id = request.getParameter("id");
        replyService.update(id);
        Post post = new Post(Integer.parseInt(request.getParameter("pid")));
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
        String id = request.getParameter("id");
        Reply reply = new Reply();
        reply.setId(Integer.parseInt(id));
        reply = replyService.getReply(reply);
        if (reply.getAccept() == 1) {
            // 改为未采纳,从用户账户内扣除飞吻
            Post post = new Post(reply.getPid());
            post.setStatus(0);
            postService.update(post);
            post = postService.getPost(post.getId());
            User user = new User(reply.getUid());
            User updateUser = userService.getUser(user);
            user.setBalance(updateUser.getBalance() - post.getReward());
            userService.update(user);
        }
        Post post = postService.getPost(reply.getPid());
        Post updatePost = new Post(post.getId());
        updatePost.setComments(post.getComments() - 1);
        postService.update(updatePost);
        replyService.delReply(reply);
        return new AjaxResult(0);
    }

    @AuthPassport
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @PathVariable("id") int id) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        Post post = postService.getPost(id);
        if (post == null) {
            request.setAttribute("msg", "主题不存在");
            return "err/err";
        } else {
            if (post.getDelete() == 1)
                request.setAttribute("msg", "该主题已被删除");
            else {
                if (post.getAuthor() != user.getId()) {
                    request.setAttribute("msg", "不要试图修改不是你的主题");
                    return "err/err";
                } else {
                    request.setAttribute(Constants.TITLE, "编辑主题 - " + post.getTitle());
                    Column column = new Column();
//                    column.setRole(user.getType());
                    request.getSession().setAttribute("listColumn", columnService.listColumn(column));
                    Verify verify = verifyService.randVerify();
                    request.setAttribute("verify", verify);
                    request.setAttribute("post", post);
                    request.setAttribute("listType", columnService.listColumnSecondary());
                }
            }
            return post.getDelete() == 0 ? "post/edit" : "err/err";
        }
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult updatePost(HttpServletRequest request, Post post) {
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
                postService.update(post);
                postService.delKeyword(post.getId());// 删除已有关键字
                KeyWordComputer kwc = new KeyWordComputer(5); // 分词,关键字提取
                List<org.ansj.app.keyword.Keyword> keywords = kwc.computeArticleTfidf(post.getTitle(), post.getContent());
                if (ObjectUtil.isNotNull(keywords) && keywords.size() > 0) {
                    keywords.forEach(
                            keyword -> postService.addKeyword(new Keyword(keyword.getName(), post.getId(), new Timestamp(System.currentTimeMillis())))
                    );
                }
                return new AjaxResult(0, null, "/post/" + post.getId()); // 此处应该跳转到用户发表的对应目录的首页
            }
        }
    }

    @RequestMapping(value = "/tags/{tag}", method = RequestMethod.GET)
    public String tags(HttpServletRequest request, @PathVariable("tag") String tag) {
        String p = request.getParameter("p");
        String type = request.getParameter("type");
        Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * Constants.NUM_PER_PAGE, Constants.NUM_PER_PAGE);
        page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
        page.setTotalCount(postService.countTags(type, tag));
        List<Post> postList = postService.listTags(page, type, tag);
        request.setAttribute(Constants.TITLE, tag);
        request.setAttribute(Constants.SEARCH_TAG, tag);
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.POST_LIST, postList);
        request.setAttribute(Constants.TYPE, type);
        request.setAttribute(Constants.REPLY_LIST, replyService.weeklyTop());// 回帖周榜
        request.setAttribute(Constants.HOT_WEEKLY_LIST, postService.weeklyTop());// 本周热议
        request.setAttribute(Constants.FS_LIST, indexService.listFriendsSite());// 友链
        request.setAttribute(Constants.SPONSOR_LIST, indexService.listSponsor(1));
        request.setAttribute(Constants.KEYWORD_LIST, indexService.listKeyword());// 最热标签
        return Constants.POST_TAGS;
    }

}
