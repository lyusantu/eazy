package com.eazy.commons.auth;

import com.eazy.commons.Constants;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User currUser = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
        if (ObjectUtil.isNull(currUser)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                User user = new User();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equalsIgnoreCase("email"))
                        user.setEmail(cookie.getValue());
                    else if (cookie.getName().equalsIgnoreCase("password"))
                        user.setPassword(cookie.getValue());
                }
                if (ObjectUtil.isNotNull(user.getEmail()) && ObjectUtil.isNotNull(user.getPassword())) {
                    user = userService.login(user);
                    if (ObjectUtil.isNotNull(user)) {
                        LOG.info(user.getEmail() + " 从cookie中登录..");
                        request.getSession().setAttribute(Constants.LOGIN_USER, user);
                    }
                }
            }
        }

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            if (authPassport == null || authPassport.validate() == false) // 没有声明需要权限,或声明不验证权限
                return true;
            else {
                User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);// 实现权限验证
                if (ObjectUtil.isNotNull(user))
                    return true;
                else {
                    response.sendRedirect("/user/signin");
                    return false;
                }
            }
        } else
            return true;
    }
}
