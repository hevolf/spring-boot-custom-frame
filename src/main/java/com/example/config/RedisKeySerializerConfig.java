package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author caohaifengx@163.com 2021-06-10 15:52
 */
@Data
@Component
@ConfigurationProperties(prefix = "serializer.redis.prefix")
public class RedisKeySerializerConfig {
    /**
     * redis - key 序列化前缀开关
     */
    private Boolean enable = Boolean.TRUE;

    /**
     * 前缀key - 建议使用项目名
     * prefix::key
     */
    private String key;
}
