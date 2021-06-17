package com.example.controller.rest;

import com.example.mail.MailVo;
import com.example.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.util.UUID;

/**
 * @author caohaifengx@163.com 2021-01-04 21:52
 */
@RestController
@Slf4j
public class TestMailController {


    @Autowired
    MailService mailService;

    @RequestMapping("/mail")
    public void mail(){
        //发送邮件
        MailVo mailVo = new MailVo();
        mailVo.setFrom("caohaifengx@126.com");
        mailVo.setTo("caohaifengx@163.com");
        mailVo.setSubject("收到您朋友【】的信件,Ta的邮箱:");
        mailVo.setText(UUID.randomUUID().toString());
        mailVo.setHtml(true);
        mailVo.setTemplatePath("mail");

        Context context = new Context();
        context.setVariable("project", "demo");
        context.setVariable("author", "yimcarson");
        context.setVariable("code", "aaaaa");
        mailVo.setContext(context);

        mailService.sendMail(mailVo);

    }



}
