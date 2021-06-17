package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author caohaifengx@163.com 2021-06-17 11:11
 */
//@Configuration
public class MailConfig {

    /**
     * 如果使用自定义的bean， 需设置host等相关参数，否则host默认为localhost
     * 建议使用JavaMailSenderImpl 而非JavaMailSender
     * 参考类 MailSenderPropertiesConfiguration
     */
    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        return javaMailSender;
    }
}
