package com.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author caohaifengx@163.com 2021-01-04 22:47
 */
@Slf4j
@Configuration
@ConfigurationProperties("config")
@Data
public class TestConfig {
    private String name;
    private Integer age;
}
