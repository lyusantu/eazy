package com.eazy.commons.auth;

import com.eazy.commons.Constants;
import com.eazy.user.entity.User;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            if (authPassport == null || authPassport.validate() == false) // 没有声明需要权限,或声明不验证权限
                return true;
            else {
                User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);// 实现权限验证
                if (ObjectUtil.isNotNull(user)) {
                    return true;
                } else {
                    response.sendRedirect("/user/signin");
                    return false;
                }
            }
        } else
            return true;
    }
}
