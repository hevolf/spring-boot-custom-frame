package com.example.service;

import com.example.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author caohaifengx@163.com 2021-05-12 17:44
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestConfig testConfig;


    @Override
    @Cacheable(cacheNames = "test", key = "#root.methodName + '_' + #id")
    public String methodA(String id, String name, int a){
        log.debug("-----------=====aaaa=========");
        System.out.println(name);
        return "hello world111";
    }
}
