package com.example.controller;

import com.example.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caohaifengx@163.com 2021-01-08 22:56
 */

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/index")
    public String test(Model model) {
        model.addAttribute("msg", "controller 参数");
        User user = new User();
        user.setAge(22);
        user.setName("tomtom");
        model.addAttribute("user", user);
        return "views/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin")
    public void signin(@RequestParam String username, @RequestParam String password,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (Exception e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect(contextPath + "/login?error");
        }
        // 应该使用重定向，改变浏览器中url
        response.sendRedirect(contextPath + "/index");
    }


    @RequestMapping("/layui")
    public String test() {
        return "test.html";
    }

    @RequestMapping("/console")
    public String test1() {
        return "views/home/console";
    }

    @RequestMapping("/bbb/homepage1")
    public String test2() {
        return "views/home/homepage1";
    }


    @RequestMapping("/**/*.html")
    public String test(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        String servletPath = request.getServletPath();
        return "views" + servletPath;
    }
}
