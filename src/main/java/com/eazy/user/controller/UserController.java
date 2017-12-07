package com.eazy.user.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.QiNiuUtil;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    // 跳转登录
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "登录");
        return "user/login";
    }

    // 跳转注册
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "注册");
        return "user/reg";
    }

    // 跳转设置
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(HttpServletRequest request) {
        request.setAttribute(Constants.TITLE, "账号设置");
        return "user/set";
    }

    // 跳转主页
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
        user = userService.login(user);
        if (ObjectUtil.isNull(user)) {
            LOG.warn(email + "登录失败,用户名或密码输入错误");
            return new AjaxResult(1, "用户名或密码错误");
        } else {
            if (user.getStatus() == 0 || user.getStatus() == 1) {
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

    // ajax注册
    @RequestMapping(value = "/ajaxSignUp", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSignUp(User user, HttpServletRequest request) {
        String rePassword = request.getParameter("repassword");
        if (!user.getPassword().equals(rePassword)) {
            return new AjaxResult(1, "两次输入的密码不一致");
        } else {
            if (userService.verifyAccountExists(user))
                return new AjaxResult(1, "您输入的邮箱已被注册");
            else {
                user.setVip(0);
                user.setStatus(0);
                user.setGender(0); // 默认为男
                user.setBalance(100);
                user.setPassword(SecureUtil.md5(user.getPassword()));
                user.setRegTime(new Timestamp(System.currentTimeMillis()));
                user.setAvatar("/res/images/avatar/" + new Random().nextInt(12) + ".jpg");
                user.setId(userService.reg(user));
                LOG.info(Base64.decode(user.getEmail()) + "注册成功");
                // 为用户设置默认角色
                request.getSession().setAttribute(Constants.LOGIN_USER, user);
                return new AjaxResult(0, null, "/");
            }
        }
    }

    // 注销
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return "redirect:/";
    }

    // ajax设置个人信息
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
        return new AjaxResult(0, null, "/user/set");
    }

    // ajax设置新的密码
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
                    return new AjaxResult(0, null, "/user/signin");
                } else
                    return new AjaxResult(1, "两次输入的密码不一致");
            }
        }
    }

    // 上传头像
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult upload(@Param("file") MultipartFile file, HttpSession session) throws IOException {
        String upload = Constants.QINIU_CHAIN + new QiNiuUtil().upload(file);
        User user = (User) session.getAttribute(Constants.LOGIN_USER);
        user.setAvatar(upload);
        userService.update(user);
        session.setAttribute(Constants.LOGIN_USER, user);
        LOG.info(user.getEmail() + "上传了新的头像");
        return new AjaxResult(0, null, null, upload);
    }

}
