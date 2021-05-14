package com.example.controller.rest;

import com.example.model.TestConfig;
import com.example.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/tet3")
    public String tet2(@RequestBody TestConfig testConfig){
        return "jjjjjjjj2";
    }
}
