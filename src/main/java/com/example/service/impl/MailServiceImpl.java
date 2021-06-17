package com.example.service.impl;

import com.example.mail.MailVo;
import com.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.util.Date;

/**
 * @author caohaifengx@163.com 2021-06-17 11:07
 */
@Service
public class MailServiceImpl implements MailService {

    // JavaMailSenderImpl 方便获取相关配置参数 使用接口JavaMailSender获取不方便;
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 用于发送模板邮件
     */
    @Autowired
    private TemplateEngine templateEngine;

    //@Async
    @Override
    public void sendMail(MailVo mailVo) {
        // 校验
        checkMail(mailVo);
        if (mailVo.isHtml()) {
            // 模板邮件
            sendTemplateMail(mailVo);
        } else {
            sendMimeMail(mailVo);
        }
        // 持久化
        saveMail(mailVo);
    }

    /**
     * 构建模板邮件
     */
    private void sendTemplateMail(MailVo mailVo){
        // 将模板与变量关联
        String emailContent = templateEngine.process(mailVo.getTemplatePath(), mailVo.getContext());
        mailVo.setText(emailContent);
        sendMimeMail(mailVo);
    }

    /**
     * 构建复杂邮件信息类
     */
    private void sendMimeMail(MailVo mailVo) {
        try {
            // true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            // 邮件发信人从配置项读取
            if (StringUtils.isEmpty(mailVo.getFrom())) {
                mailVo.setFrom(getMailSendFrom());
            }
            // 邮件发信人
            messageHelper.setFrom(mailVo.getFrom());
            // 邮件收信人
            messageHelper.setTo(mailVo.getTo().split(","));
            // 邮件主题
            messageHelper.setSubject(mailVo.getSubject());
            // 邮件内容 默认为false - text/plain  (true - text/html: 内容为html格式)
            messageHelper.setText(mailVo.getText(), mailVo.isHtml());
            // 抄送
            if (!StringUtils.isEmpty(mailVo.getCc())) {
                messageHelper.setCc(mailVo.getCc().split(","));
            }
            // 密送
            if (!StringUtils.isEmpty(mailVo.getBcc())) {
                messageHelper.setCc(mailVo.getBcc().split(","));
            }
            // 添加邮件附件
            if (mailVo.getMultipartFiles() != null) {
                for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
                }
            }
            // 发送时间
            if (StringUtils.isEmpty(mailVo.getSentDate())) {
                mailVo.setSentDate(new Date());
                messageHelper.setSentDate(mailVo.getSentDate());
            }
            // 正式发送邮件
            mailSender.send(messageHelper.getMimeMessage());
            mailVo.setStatus("ok");
        } catch (Exception e) {
            // 发送失败
            throw new RuntimeException(e);
        }
    }


    /**
     * 检测邮件信息
     */
    private void checkMail(MailVo mailVo) {
        if (StringUtils.isEmpty(mailVo.getTo())) {
            throw new IllegalArgumentException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getSubject())) {
            throw new IllegalArgumentException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(mailVo.getText())) {
            throw new IllegalArgumentException("邮件内容不能为空");
        }
        if (mailVo.isHtml()){
            if (StringUtils.isEmpty(mailVo.getContext())) {
                throw new IllegalArgumentException("邮件模板context不能为空");
            }
            if (StringUtils.isEmpty(mailVo.getTemplatePath())) {
                throw new IllegalArgumentException("邮件模板path不能为空");
            }
        }

    }


    /**
     * 持久化
     */
    private void saveMail(MailVo mailVo) {
 
        //将邮件保存到数据库..
    }

    /**
     * 获取 properties 属性
     */
    private String getMailSendFrom() {
        // 获取邮件发信人
        return mailSender.getJavaMailProperties().getProperty("from");
    }

}
