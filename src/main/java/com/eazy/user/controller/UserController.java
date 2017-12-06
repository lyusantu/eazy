package com.eazy.user.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.dto.AjaxResult;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    // 跳转登录
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn() {
        return "user/login";
    }

    // ajax登录
    @RequestMapping(value = "/ajaxSignIn", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSignIn(HttpServletRequest request, User user, Model model) {
        user.setEmail(Base64.encode(user.getEmail()));
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user = userService.login(user);
        if (ObjectUtil.isNull(user)) {
            LOG.warn(user.getEmail() + "登录失败,用户名或密码输入错误");
            return new AjaxResult(1, "用户名或密码错误");
        } else {
//            baseService.addLoginRecord(user.getId(), Constants.getIpAddress(request)); // 记录本次登录信息
            request.getSession().setAttribute(Constants.LOGIN_USER, user);
            LOG.info(user.getEmail() + "登录成功");
            return new AjaxResult(0, null, "/");
        }
    }

    // 跳转注册
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "user/reg";
    }

    // ajax注册
    @RequestMapping(value = "/ajaxSignUp", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult ajaxSignUp(User user, HttpServletRequest request) {
        String rePassword = request.getParameter("repassword");
        if (!user.getPassword().equals(rePassword)) {
            return new AjaxResult(1, "两次输入的密码不一致");
        } else {
            user.setEmail(Base64.encode(user.getEmail()));
            if (userService.verifyAccountExists(user))
                return new AjaxResult(1, "您输入的邮箱已被注册");
            else {
                user.setStatus(0);
                user.setBalance(100);
                user.setNickName(Base64.encode(user.getNickName()));
                user.setPassword(SecureUtil.md5(user.getPassword()));
                user.setRegTime(new Timestamp(System.currentTimeMillis()));
                user.setId(userService.reg(user));
                LOG.info(user.getEmail() + "注册成功");
                // 为用户设置默认角色
                request.getSession().setAttribute(Constants.LOGIN_USER, user);
                return new AjaxResult(0, null, "/");
            }
        }
    }

}
