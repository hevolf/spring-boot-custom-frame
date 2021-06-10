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
    private Boolean enable = Boolean.TRUE;
    private String key;
}
