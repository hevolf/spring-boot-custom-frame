package com.example.model;

import lombok.Data;

/**
 * @author caohaifengx@163.com 2021-01-08 23:04
 */
@Data
public class User {
    /**
     *  报错：reuqired a bean named 'authorizer' that could not be found
     *  原因：默认注入的SessionsSecurityManager需要注入authorizer()
     *  解决方案：返回类型Realm 或 声明 beanName为 "authorizer"
     *  注入Realm
     */
    private String name;
    private int age;
}
