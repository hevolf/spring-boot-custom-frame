package com.example.controller.rest;

import com.example.entity.TestConfig;
import com.example.entity.User;
import com.example.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author caohaifengx@163.com 2021-01-04 21:52
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping("/test")
    public String methodA(String id, String name, int a){
        log.debug("------------");
        String s = service.methodA(id, name, a);
        return s;
    }

    @RequestMapping("/tet")
    public String tet(){
        return "jjjjjjjj";
    }

    @RequestMapping("/tet4")
    public String tet2(){
        return "jjj2jjjjj";
    }

    @RequestMapping("/tet3")
    public String tet2(@RequestBody TestConfig testConfig){
        return "jjjjjjjj2";
    }

    @RequestMapping("/getUser")
    public User getUser(){
        User user = new User();
        user.setName("tom");
        user.setAge(18);
        user.setBrithDay(new Date());
        user.setAaa(new Date());
        user.setBbb(new Date());
        return user;
    }
}
