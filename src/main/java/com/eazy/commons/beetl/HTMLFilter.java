package com.eazy.commons.beetl;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.servlet.ServletGroupTemplate;
import org.beetl.ext.web.SimpleCrossFilter;

import javax.servlet.*;
import java.io.IOException;

public class HTMLFilter extends SimpleCrossFilter implements Filter {

    @Override
    protected GroupTemplate getGroupTemplate() {
        return ServletGroupTemplate.instance().getGroupTemplate();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        super.doFilter(request, response, chain);
    }
}
