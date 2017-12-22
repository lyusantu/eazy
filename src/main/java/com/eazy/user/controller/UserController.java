package com.eazy.user.controller;

import com.eazy.api.service.MailTaskService;
import com.eazy.collection.entity.PostCollection;
import com.eazy.collection.service.CollectionService;
import com.eazy.commons.Constants;
import com.eazy.commons.Page;
import com.eazy.commons.QiNiuUtil;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/user")
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

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String index(@PathVariable("id") int id, HttpServletRequest request) {
        User user = new User(id);
        user = userService.getUser(user);
        Page page = new Page(1, 20);
        List<Post> postList = postService.listMyPost(user.getId(), page);
        request.setAttribute("user", user);
        request.setAttribute("postList", postList);
        return "user/userhome";
    }

    // 跳转登录
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn(HttpServletRequest request) {
        Verify verify = verifyService.randVerify();
        request.setAttribute("verify", verify);
        request.setAttribute(Constants.TITLE, "登录");
        return "user/login";
    }

    // 跳转注册
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(HttpServletRequest request) {
        Verify verify = verifyService.randVerify();
        request.setAttribute("verify", verify);
        request.setAttribute(Constants.TITLE, "注册");
        return "user/reg";
    }

    // 跳转设置
    @AuthPassport
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "账号设置");
        return "user/set";
    }

    // 跳转主页
    @AuthPassport
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "用户主页");
        return "user/home";
    }

    // ajax登录
    @RequestMapping(value = "/ajaxSignIn", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSignIn(HttpServletRequest request, User user) {
        String email = user.getEmail();
        user.setPassword(SecureUtil.md5(user.getPassword()));
        Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
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
        String rePassword = request.getParameter("repassword");
        if (!user.getPassword().equals(rePassword)) {
            return new AjaxResult(1, "两次输入的密码不一致");
        } else {
            Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
            verify = verifyService.getVerify(verify);
            if (ObjectUtil.isNull(verify))
                return new AjaxResult(1, "人类验证失败");
            else {
                if (userService.verifyAccountExists(user))
                    return new AjaxResult(1, "您输入的邮箱已被注册");
                else {
                    user.setVip(0);
                    user.setStatus(0);
                    user.setGender(0); // 默认为男
                    user.setBalance(100);
                    user.setType("user");
                    user.setPassword(SecureUtil.md5(user.getPassword()));
                    user.setRegTime(new Timestamp(System.currentTimeMillis()));
                    user.setAvatar("/res/images/avatar/" + new Random().nextInt(12) + ".jpg");
                    user.setActiveCode(request.getSession().getId() + System.currentTimeMillis());
                    user.setId(userService.reg(user));
                    LOG.info(Base64.decode(user.getEmail()) + "注册成功");
                    LOG.info("----------激活邮件发送START----------");
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                    mailTaskService.sendSimpleMail("eazy社区账户激活邮件", "请点击链接激活您的账户：" + basePath + "user/activeAccount/" + user.getActiveCode(), user.getEmail());
                    LOG.info("----------激活邮件发送END----------");
                    return new AjaxResult(0, "激活邮件已发送至您的邮箱,请激活后登录 :)", "/user/signin");
                }
            }
        }
    }

    // 注销
    @AuthPassport
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
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
            String pass = request.getParameter("pass");
            if (user.getPassword().equals(pass))
                return new AjaxResult(1, "当前密码不能与新密码相同");
            else {
                String repass = request.getParameter("repass");
                if (pass.equals(repass)) {
                    user.setId(loginUser.getId());
                    user.setPassword(SecureUtil.md5(pass));
                    userService.update(user);
                    request.getSession().removeAttribute(Constants.LOGIN_USER);
                    return new AjaxResult(0, "密码修改成功! 请重新登录", "/user/signin");
                } else
                    return new AjaxResult(1, "两次输入的密码不一致");
            }
        }
    }

    // 激活账户
    @RequestMapping(value = "/activeAccount/{code}", method = RequestMethod.GET)
    public String activeAccount(HttpServletRequest request, @PathVariable("code") String code) {
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
        request.setAttribute("err", err);
        request.setAttribute("msg", msg);
        return "user/active";
    }

    // 上传头像
    @AuthPassport
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult upload(@Param("file") MultipartFile file, HttpSession session) throws IOException {
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
    public String post() {
        return "user/post";
    }

    // 我的帖子
    @AuthPassport
    @RequestMapping(value = "/myPost")
    public @ResponseBody
    JSONObject myPost(HttpServletRequest request) {
        int currPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        Page page = new Page((currPage - 1) * pageSize, pageSize);
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("count", postService.countMyPost(user.getId()));
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
                                .put("data", post.getReaders() + "阅/" + post.getComments() + "答")
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
        int currPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        Page page = new Page((currPage - 1) * pageSize, pageSize);
        User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        List<PostCollection> list = collectionService.myCollection(user.getId(), page);
        JSONObject json = new JSONObject();
        json.put("code", 0).put("count", collectionService.countMyCollection(user.getId()));
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
        request.setAttribute("verify", verify);
        request.setAttribute("way", "forget");
        return "user/forget";
    }

    // 找回密码
    @RequestMapping(value = "/ajaxForget", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult ajaxForget(HttpServletRequest request, User user) {
        Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
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
    public String forget(HttpServletRequest request, @PathVariable("code") String code) {
        Verify verify = verifyService.randVerify();
        request.setAttribute("verify", verify);
        request.setAttribute("way", "set");
        User user = userService.activeAccount(code);
        request.setAttribute("status", ObjectUtil.isNull(user));
        request.setAttribute("user", user);
        return "user/forget";
    }

    // 修改密码
    @RequestMapping(value = "/ajaxUpdatePwd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    AjaxResult ajaxUpdatePwd(HttpServletRequest request, User user) {
        Verify verify = new Verify(Integer.parseInt(request.getParameter("verid")), request.getParameter("vercode"));
        verify = verifyService.getVerify(verify);
        if (ObjectUtil.isNull(verify))
            return new AjaxResult(1, "人类验证失败");
        else {
            String repass = request.getParameter("repass");
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
            return new AjaxResult(0, "密码修改成功", "/user/signin");
        }
    }
}
