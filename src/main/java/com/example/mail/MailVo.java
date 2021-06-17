package com.example.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import java.util.Date;

/**
 * @author caohaifengx@163.com 2021-06-17 11:00
 */
@Data
public class MailVo {

    /**
     * 邮件id
     */
    private String id;

    /**
     * 邮件发送人
     */
    private String from;

    /**
     * 邮件接收人 多个收件人用 “,” 隔开
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 正文是否是html格式（一般用于发送模板邮件）
     * 默认false - text/plain
     * 当 设置成true时，需同时设置templatePath和context
     */
    private boolean html = false;

    /**
     * 模板邮件路径（模板邮件使用）
     */
    private String templatePath;

    /**
     * 发送时间
     */
    private Date sentDate;

    /**
     * 抄送
     */
    private String cc;

    /**
     * 密送
     */
    private String bcc;

    /**
     * 状态
     */
    private String status;

    /**
     * 报错信息
     */
    private String error;

    /**
     * 邮件附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;

    /**
     * 模板中变量对应的参数
     */
    @JsonIgnore
    private Context context;


}
