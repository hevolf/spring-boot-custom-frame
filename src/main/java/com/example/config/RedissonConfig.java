package com.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caohaifengx@163.com 2021-06-11 13:45
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        // 单机模式
        config.useSingleServer().setAddress("redis://192.168.68.10:6379").setPassword("12345678");

        return Redisson.create(config);
    }

}
