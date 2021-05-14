package com.example.security;

import com.example.common.entity.Result;
import com.example.common.error.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author caohaifengx@163.com 2021-05-13 14:28
 */
@Slf4j
public class AuthcFilter extends FormAuthenticationFilter {

    /*
    * 在需要登录而未登录时会调用此方法，即后台会话超时
    * */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            // 针对ajax请求
            HttpServletRequest req = WebUtils.toHttp(request);
            if (isAjax(req)){
                HttpServletResponse resp = WebUtils.toHttp(response);
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                //resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Result.error(ErrorCodeEnum.SESSION_OUT.getCode(), ErrorCodeEnum.SESSION_OUT.getMessage());
            }else{
                saveRequestAndRedirectToLogin(request, response);

            }
            return false;
        }
    }

    public boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("x-requested-with");
        return header != null && "XMLHttpRequest".equalsIgnoreCase(header);
    }
}
