package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author caohaifengx@163.com 2021-01-04 22:47
 */
@ConfigurationProperties("config")
@Data
public class TestConfig {
    private String name;
    private Integer age;
}
