package com.eazy.user.controller;

import com.eazy.accountRecord.entity.AccountRecord;
import com.eazy.accountRecord.service.AccountRecordService;
import com.eazy.api.service.MailTaskService;
import com.eazy.collection.entity.PostCollection;
import com.eazy.collection.service.CollectionService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.Qiniu.QiNiuUtil;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.message.entity.Message;
import com.eazy.message.service.MessageService;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.eazy.post.service.ReplyService;
import com.eazy.user.entity.User;
import com.eazy.user.entity.UserFB;
import com.eazy.user.service.UserService;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/user")
@Api(value = "/user", tags = "User接口")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private MailTaskService mailTaskService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountRecordService accountRecordService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String index(@PathVariable(Constants.GET_ID) int id, HttpServletRequest request) {
        User user = new User(id);
        user = userService.getUser(user);
        if (ObjectUtil.isNull(user)) {
            request.setAttribute(Constants.SET_MSG, "用户未找到");
            return Constants.URL_ERR_ERR;
        } else {
            Page page = new Page(0, 20);
            request.setAttribute(Constants.TITLE, user.getNickName());
            request.setAttribute(Constants.ENTITY_USER, user);
            request.setAttribute(Constants.POST_LIST, postService.listMyPost(user.getId(), page)); // 最近的提问
            request.setAttribute(Constants.REPLY_LIST, replyService.listMyReply(user.getId(), page));// 最近的回答
            User currUser = Constants.getLoginUser(request);
            if (currUser != null && !currUser.getId().equals(user.getId())) {
                UserFB ufb = new UserFB(currUser.getId(), user.getId(), 0);
                request.setAttribute("follow", userService.countUserFB(ufb));
                ufb = new UserFB(currUser.getId(), user.getId(), 1);
                request.setAttribute("block", userService.countUserFB(ufb));
            }
            return Constants.URL_USER_USERHOME;
        }
    }

    // 跳转登录
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn(HttpServletRequest request) {
        Verify verify = verifyService.randVerify();
        request.setAttribute(Constants.VERIFY, verify);
        request.setAttribute(Constants.TITLE, "登入");
        return Constants.URL_USER_LOGIN;
    }

    // 跳转注册
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(HttpServletRequest request) {
        Verify verify = verifyService.randVerify();
        request.setAttribute(Constants.VERIFY, verify);
        request.setAttribute(Constants.TITLE, "注册");
        return Constants.URL_USER_REG;
    }

    // 跳转设置
    @AuthPassport
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "基本设置");
        return Constants.URL_USER_SET;
    }

    // 跳转主页
    @AuthPassport
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        user = userService.getUser(user);
        Page page = new Page(0, 20);
        request.setAttribute(Constants.POST_LIST, postService.listMyPost(user.getId(), page)); // 最近提问
        request.setAttribute(Constants.REPLY_LIST, replyService.listMyReply(user.getId(), page));// 最近的回答
        request.setAttribute(Constants.TITLE, "我的主页");
        return Constants.URL_USER_HOME;
    }

    // ajax登录
    @RequestMapping(value = "/ajaxSignIn", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "异步登录接口", notes = "根据用户名和密码进行登录", httpMethod = "POST", response = User.class)
    public AjaxResult ajaxSignIn(HttpServletRequest request, HttpServletResponse response, User user) {
        String email = user.getEmail();
        user.setPassword(SecureUtil.md5(user.getPassword()));
        Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
        verify = verifyService.getVerify(verify);
        if (ObjectUtil.isNull(verify))
            return new AjaxResult(1, "人类验证失败");
        else {
            user = userService.login(user);
            if (ObjectUtil.isNull(user)) {
                LOG.warn(email + "登录失败,用户名或密码输入错误");
                return new AjaxResult(1, "用户名或密码错误");
            } else {
                if (user.getStatus() == 0)
                    return new AjaxResult(1, "该账户未激活");
                else if (user.getStatus() == 1) {
                    // baseService.addLoginRecord(user.getId(), Constants.getIpAddress(request)); // 记录本次登录信息
                    request.getSession().setAttribute(Constants.LOGIN_USER, user);
                    LOG.info(email + "登录成功");
                    Cookie mailCookie = new Cookie(Constants.SET_EMAIL, user.getEmail());
                    mailCookie.setMaxAge(60 * 60 * 24 * 3);
                    mailCookie.setPath("/");
                    Cookie pwdCookie = new Cookie(Constants.SET_PASSWORD, user.getPassword());
                    pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                    pwdCookie.setPath("/");
                    response.addCookie(mailCookie);
                    response.addCookie(pwdCookie);
                    return new AjaxResult(0, null, "/");
                } else if (user.getStatus() == 2)
                    return new AjaxResult(1, "该账号已经封禁");
                else
                    return new AjaxResult(1, "未知状态");
            }
        }
    }

    // ajax注册
    @RequestMapping(value = "/ajaxSignUp", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSignUp(User user, HttpServletRequest request) {
        String rePassword = request.getParameter(Constants.SET_REPASSWORD);
        if (!user.getPassword().equals(rePassword)) {
            return new AjaxResult(1, "两次输入的密码不一致");
        } else {
            Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
            else {
                if (userService.verifyAccountExists(user))
                    return new AjaxResult(1, "您输入的邮箱已被注册");
                else if (userService.verifyNickNameExists(user))
                    return new AjaxResult(1, "您输入的昵称已被注册");
                else {
                    user.setVip(0);
                    user.setStatus(0);
                    user.setGender(0); // 默认为男
                    user.setBalance(Constants.DEFAULT_BALANCE);
                    user.setType(Constants.ROLE_USER);
                    user.setPassword(SecureUtil.md5(user.getPassword()));
                    user.setRegTime(new Timestamp(System.currentTimeMillis()));
                    user.setAvatar("/res/images/avatar/" + new Random().nextInt(10) + ".png");
                    user.setActiveCode(request.getSession().getId() + System.currentTimeMillis());
                    user.setReadType(0);
                    userService.reg(user);
                    AccountRecord arSet = new AccountRecord(user.getId(), 0, Constants.DEFAULT_BALANCE, 6, Constants.DEFAULT_BALANCE,
                            Constants.getAccountRecordDesc(6, null, null, null, Constants.DEFAULT_BALANCE), Constants.getTimeStamp());
                    accountRecordService.addAccountReocrd(arSet);
                    LOG.info(Base64.decode(user.getEmail()) + "注册成功");
                    LOG.info("----------激活邮件发送START----------");
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                    mailTaskService.sendSimpleMail("eazy社区账户激活邮件", "请点击链接激活您的账户：" + basePath + "user/activeAccount/" + user.getActiveCode(), user.getEmail());
                    LOG.info("----------激活邮件发送END----------");
                    LOG.info("----------推送消息欢迎新用户加入START----------");
                    Message message = new Message(1, 1, user.getId(), 2, "欢迎加入eazy社区", new Timestamp(System.currentTimeMillis()), 0, 0);
                    messageService.addMsg(message);
                    LOG.info("----------推送消息欢迎新用户加入END----------");
                    return new AjaxResult(0, "激活邮件已发送至您的邮箱,请激活后登录 :)", Constants.URL_USER_SIGNIN);
                }
            }
        }
    }

    // 注销
    @AuthPassport
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie emailCookie = new Cookie(Constants.SET_EMAIL, null);
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        Cookie pwdCookie = new Cookie(Constants.SET_PASSWORD, null);
        pwdCookie.setMaxAge(0);
        pwdCookie.setPath("/");
        response.addCookie(emailCookie);
        response.addCookie(pwdCookie);
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return "redirect:/";
    }

    // ajax设置个人信息
    @AuthPassport
    @RequestMapping(value = "/ajaxSet", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSet(User user, HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (!loginUser.getEmail().equals(user.getEmail())) { // 在邮箱已激活的情况下变更了邮箱,需要重新验证邮箱
            LOG.info("昵称为'" + loginUser.getNickName() + "'的用户将邮箱" + loginUser.getEmail() + "更改为 " + user.getEmail());
            // 发送新邮箱的激活邮件 userService.active();
            if (loginUser.getStatus() == 1)
                user.setStatus(0);
        }
        int id = loginUser.getId();
        BeanUtils.copyProperties(user, loginUser);
        loginUser.setId(id);
        userService.update(loginUser);
        loginUser = userService.getUser(loginUser);
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUser);
        return new AjaxResult(0, "修改资料成功", "/user/set");
    }

    // ajax设置新的密码
    @AuthPassport
    @RequestMapping(value = "/ajaxSetPwd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSetPwd(User user, HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (!loginUser.getPassword().equals(SecureUtil.md5(user.getPassword())))
            return new AjaxResult(1, "当前密码输入错误");
        else {
            String pass = request.getParameter(Constants.GET_PASS);
            if (user.getPassword().equals(pass))
                return new AjaxResult(1, "当前密码不能与新密码相同");
            else {
                String repass = request.getParameter(Constants.GET_REPASS);
                if (pass.equals(repass)) {
                    user.setId(loginUser.getId());
                    user.setPassword(SecureUtil.md5(pass));
                    userService.update(user);
                    request.getSession().removeAttribute(Constants.LOGIN_USER);
                    return new AjaxResult(0, "密码修改成功! 请重新登录", Constants.URL_USER_SIGNIN);
                } else
                    return new AjaxResult(1, "两次输入的密码不一致");
            }
        }
    }

    // 激活账户
    @RequestMapping(value = "/activeAccount/{code}", method = RequestMethod.GET)
    public String activeAccount(HttpServletRequest request, @PathVariable(Constants.GET_CODE) String code) {
        User user = userService.activeAccount(code);
        int err = 1;
        String msg = "激活失败！ 激活码不存在";
        if (ObjectUtil.isNotNull(user))
            if (user.getStatus() == 0) {
                err = 0;
                User activeUser = new User();
                activeUser.setId(user.getId());
                activeUser.setActiveCode(user.getActiveCode());
                userService.update(activeUser);
                msg = "激活成功！ <a href=\"/user/signin\">马上登录</a>";
            } else
                msg = "激活失败！激活码已失效";
        request.setAttribute(Constants.TITLE, "激活账户");
        request.setAttribute(Constants.SET_ERR, err);
        request.setAttribute(Constants.SET_MSG, msg);
        return "user/active";
    }

    // 上传头像
    @AuthPassport
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult upload(@Param(Constants.GET_FILE) MultipartFile file, HttpSession session) throws IOException {
        String upload = Constants.QINIU_CHAIN + new QiNiuUtil().upload(file);
        User user = (User) session.getAttribute(Constants.LOGIN_USER);
        User updateUser = new User(user.getId(), upload);
        userService.update(updateUser);
        user.setAvatar(upload);
        session.setAttribute(Constants.LOGIN_USER, user);
        LOG.info(user.getEmail() + "上传了新的头像");
        return new AjaxResult(0, null, null, upload);
    }

    @AuthPassport
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String post(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "我的主题");
        return Constants.URL_USER_POST;
    }

    // 我的帖子
    @AuthPassport
    @RequestMapping(value = "/myPost")
    public @ResponseBody
    JSONObject myPost(HttpServletRequest request) {
        int currPage = Integer.parseInt(request.getParameter(Constants.PAGE));
        int pageSize = Integer.parseInt(request.getParameter(Constants.LIMIT));
        Page page = new Page((currPage - 1) * pageSize, pageSize);
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        JSONObject json = new JSONObject();
        json.put(Constants.SET_CODE, 0);
        json.put(Constants.SET_COUNT, postService.countMyPost(user.getId()));
        JSONArray array = new JSONArray();
        final JSONObject[] data = {new JSONObject()};
        List<Post> postList = postService.listMyPost(user.getId(), page);
        if (ObjectUtil.isNotNull(postList) && postList.size() > 0) {
            postList.forEach(
                    post -> {
                        String delete = "<span style=\"color:#999;\">正常</span>";
                        if (post.getDelete() == 1)
                            delete = "<span style=\"color:#cc0000;\">删除</span>";
                        else if (post.getDelete() == 2)
                            delete = "<span style=\"color:##58A571;\">审核</span>";
                        String status = "<span style=\"color:#ccc;\">未结</span>";
                        if (post.getStatus() == 1)
                            status = "<span style=\"color:#5FB878;\">已结</span>";
                        data[0] = new JSONObject();
                        data[0].put("title", post.getTitle())
                                .put("id", post.getId())
                                .put("type", post.getColumn().getName())
                                .put("delete", delete)
                                .put("status", status)
                                .put("reward", post.getReward())
                                .put("createTime", post.getCreateTime())
                                .put("data", post.getReaders() + "阅/" + post.getComments() + "评")
                                .put("right", null);
                        array.put(data[0]);
                    }
            );
        }
        json.put("data", array);
        return json;
    }

    // 我的收藏
    @AuthPassport
    @RequestMapping(value = "/myCollection")
    public @ResponseBody
    JSONObject myCollection(HttpServletRequest request) {
        int currPage = Integer.parseInt(request.getParameter(Constants.PAGE));
        int pageSize = Integer.parseInt(request.getParameter(Constants.LIMIT));
        Page page = new Page((currPage - 1) * pageSize, pageSize);
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        List<PostCollection> list = collectionService.myCollection(user.getId(), page);
        JSONObject json = new JSONObject();
        json.put(Constants.SET_CODE, 0).put(Constants.SET_COUNT, collectionService.countMyCollection(user.getId()));
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            list.forEach(
                    collection -> {
                        array.put(new JSONObject().put("id", collection.getPost().getId()).put("title", collection.getPost().getTitle()).put("createTime", collection.getCollectionTime()));
                    }
            );
        }
        json.put("data", array);
        return json;
    }

    // 跳转找回密码
    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public String forget(HttpServletRequest request) {
        Verify verify = verifyService.randVerify();
        request.setAttribute(Constants.VERIFY, verify);
        request.setAttribute(Constants.SET_WAY, "forget");
        request.setAttribute(Constants.TITLE, "找回密码");
        return Constants.URL_USER_FORGET;
    }

    // 找回密码
    @RequestMapping(value = "/ajaxForget", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult ajaxForget(HttpServletRequest request, User user) {
        Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
        verify = verifyService.getVerify(verify);
        if (ObjectUtil.isNull(verify))
            return new AjaxResult(1, "人类验证失败");
        else {
            String email = request.getParameter("");
            user = userService.verifyEmail(user.getEmail());
            if (ObjectUtil.isNull(user))
                return new AjaxResult(1, "该邮箱不存在");
            else {
                if (user.getStatus() == 0)
                    return new AjaxResult(1, "该邮箱未激活");
                else if (user.getStatus() == 2)
                    return new AjaxResult(1, "该邮箱已被封禁");
                else if (user.getStatus() == 1) {
                    User updateUser = new User();
                    updateUser.setId(user.getId());
                    updateUser.setActiveCode(request.getSession().getId() + System.currentTimeMillis());
                    userService.updateActiveCode(updateUser);
                    LOG.info("----------激活邮件发送START----------");
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                    mailTaskService.sendSimpleMail("eazy社区密码修改邮件", "请点击链接修改您的密码：" + basePath + "user/updatePwd/" + updateUser.getActiveCode(), user.getEmail());
                    LOG.info("----------激活邮件发送END----------");
                    return new AjaxResult(0, "修改地址已发送至您的邮箱! 请及时修改");
                } else
                    return new AjaxResult(1, "未知状态");
            }
        }
    }

    // 跳转修改密码
    @RequestMapping(value = "/updatePwd/{code}", method = RequestMethod.GET)
    public String forget(HttpServletRequest request, @PathVariable(Constants.GET_CODE) String code) {
        Verify verify = verifyService.randVerify();
        request.setAttribute(Constants.VERIFY, verify);
        request.setAttribute(Constants.SET_WAY, "set");
        User user = userService.activeAccount(code);
        request.setAttribute(Constants.SET_STATUS, ObjectUtil.isNull(user));
        request.setAttribute(Constants.ENTITY_USER, user);
        request.setAttribute(Constants.TITLE, "修改密码");
        return Constants.URL_USER_FORGET;
    }

    // 修改密码
    @RequestMapping(value = "/ajaxUpdatePwd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult ajaxUpdatePwd(HttpServletRequest request, User user) {
        Verify verify = new Verify(Integer.parseInt(request.getParameter(Constants.VERIFY_ID)), request.getParameter(Constants.VERIFY_CODE));
        verify = verifyService.getVerify(verify);
        if (ObjectUtil.isNull(verify))
            return new AjaxResult(1, "人类验证失败");
        else {
            String repass = request.getParameter(Constants.GET_REPASS);
            if (!user.getPassword().equals(repass))
                return new AjaxResult(1, "两次输入的密码不一致");
            User loginUser = userService.activeAccount(user.getActiveCode());
            if (ObjectUtil.isNull(loginUser))
                return new AjaxResult(1, "激活码已失效");
            user.setPassword(SecureUtil.md5(user.getPassword()));
            if (loginUser.getPassword().equals(user.getPassword()))
                return new AjaxResult(1, "新的密码不能与当前密码相同");
            user.setId(loginUser.getId());
            user.setActiveCode(null);
            userService.update(user);
            user.setActiveCode(request.getSession().getId() + System.currentTimeMillis());
            userService.updateActiveCode(user);
            return new AjaxResult(0, "密码修改成功", Constants.URL_USER_SIGNIN);
        }
    }

    // 跳转我的消息
    @AuthPassport
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String message(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        messageService.emptyStatus(user.getId()); // 置空状态
        String p = request.getParameter("p");
        Page page = new Page(((p == null ? 1 : Integer.parseInt(p)) - 1) * 5, 5);
        page.setPageNumber(p == null ? 1 : Integer.parseInt((p)));
        page.setTotalCount(messageService.countMyMsgAll(user.getId()));
        request.setAttribute(Constants.PAGE, page);
        request.setAttribute(Constants.LIST, messageService.listMyMsg(user.getId(), page));
        request.setAttribute(Constants.TITLE, "我的消息");
        return Constants.URL_USER_MESSAGE;
    }

    // @的跳转
    @RequestMapping(value = "/jump/{username}", method = RequestMethod.GET)
    public String jump(@PathVariable(Constants.GET_USERNAME) String username, HttpServletRequest request) {
        if (username == null)
            System.out.println("null");
        User user = userService.getUserByName(username);
        if (ObjectUtil.isNull(user)) {
            request.setAttribute(Constants.SET_MSG, "用户不存在");
            return Constants.URL_ERR_ERR;
        } else
            return "redirect:/user/" + user.getId();
    }

    // 跳转社区设置
    @AuthPassport
    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public String custom(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "社区设置");
        return "user/custom";
    }

    // 社区设置
    @AuthPassport
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult customPost(HttpServletRequest request) {
        User currUser = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        String read = request.getParameter("read");
        User user = new User(currUser.getId());
        user.setReadType(ObjectUtil.isNull(read) ? 0 : 1);
        userService.update(user);
        currUser.setReadType(user.getReadType());
        request.getSession().setAttribute(Constants.LOGIN_USER, currUser);
        return new AjaxResult(0, "设置成功", Constants.URL_USER_CUSTOM);
    }

    // 收入支出明细
    @AuthPassport
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public String balance(HttpServletRequest request) {
        User user = Constants.getLoginUser(request);
        List<AccountRecord> accountRecordList = accountRecordService.listAccountRecord(user.getId());
        request.setAttribute("arList", accountRecordList);
        return "user/balance";
    }

    // 社区设置
    @RequestMapping(value = "/fb", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult follow(HttpServletRequest request) {
        User currUser = Constants.getLoginUser(request);
        if (ObjectUtil.isNull(currUser))
            return new AjaxResult(1, "用户未登录");
        String uid = request.getParameter("uid");
        String type = request.getParameter("type");
        UserFB ufb = new UserFB(currUser.getId(), Integer.parseInt(uid), Constants.getTimeStamp());
        if (type.equalsIgnoreCase("follow"))
            ufb.setType(0);
        else if (type.equalsIgnoreCase("block"))
            ufb.setType(1);
        if (type.equalsIgnoreCase("unfollow") || type.equalsIgnoreCase("unblock"))
            userService.delUserFB(ufb);
        else
            userService.insertUserFB(ufb);
        return new AjaxResult(0, "操作成功");
    }

}
