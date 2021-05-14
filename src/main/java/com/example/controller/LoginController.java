package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caohaifengx@163.com 2021-05-10 17:19
 */

@RequestMapping
@Controller
public class LoginController {

    @RequestMapping("/tet2")
    public String tet(){
        return "jjjjjjjj";
    }
}
