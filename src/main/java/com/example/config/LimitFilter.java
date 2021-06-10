package com.example.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author caohaifengx@163.com 2021-05-31 17:02
 */
@WebFilter(urlPatterns = {"/bbb"})
public class LimitFilter implements Filter {
    // @WebFilter 搭配 @ServletComponentScan 使用
    // @Component 会过滤所有路径， @WebFilter 过滤指定路径urlPatterns
    // @Component @WebFilter同时使用会注入两次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //if (true){
        //    throw new IllegalArgumentException("bbb");
        //}

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
