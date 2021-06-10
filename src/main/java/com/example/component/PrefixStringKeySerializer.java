package com.example.component;

import com.example.config.RedisKeySerializerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义redis - key的序列化和反序列化方式
 * @author caohaifengx@163.com 2021-06-10 15:56
 * RedisKeySerializerConfig 使用了 @Component 则不需要
 * @EnableConfigurationProperties(RedisKeySerializerConfig.class)
 */
@Component
public class PrefixStringKeySerializer extends StringRedisSerializer {

    private Charset charset = StandardCharsets.UTF_8;
    private static String SPLIT_SYMBOL = ":";
    private static String SPLIT_SYMBOL_DD = "::";

    @Autowired
    private RedisKeySerializerConfig prefix;


    /**
     * 反序列化
     * @param bytes
     * @return
     */
    @Override
    public String deserialize(@Nullable byte[] bytes) {
        String saveKey = new String(bytes, charset);
        if (prefix.getEnable() != null && prefix.getEnable()) {
            String prefixKey = spliceKey(prefix.getKey());
            int indexOf = saveKey.indexOf(prefixKey);
            if (indexOf > 0) {
                saveKey = saveKey.substring(indexOf);
            }
        }
        return (saveKey.getBytes() == null ? null : saveKey);
    }

    /**
     * 序列化
     * @param key
     * @return
     */
    @Override
    public byte[] serialize(@Nullable String key) {
        // enable为true 且 prefix已配置  todo 未配置可优化为报错
        if (prefix.getEnable() != null && prefix.getEnable() && !StringUtils.isEmpty(prefix.getKey())) {
            key = spliceKey(prefix.getKey()) + key;
        }
        return (key == null ? null : key.getBytes(charset));
    }

    /**
     * 前缀key格式化成 xxx:  或 xxx::
     * @param prefixKey
     * @return
     */
    private String spliceKey(String prefixKey) {
        if (!StringUtils.isEmpty(prefixKey) && !prefixKey.endsWith(SPLIT_SYMBOL)) {
            prefixKey = prefixKey + SPLIT_SYMBOL_DD;
        }
        return prefixKey;
    }
}
