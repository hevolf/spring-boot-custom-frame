package com.example.security;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author caohaifengx@163.com 2021-05-06 16:13
 */
@Configuration
public class ShiroConfig {

    /**
     * 可自行定义（默认采用springboot注入）
     */
    @Autowired
    protected SecurityManager securityManager;

    protected List<String> globalFilters() {
        return Collections.singletonList(DefaultFilter.invalidRequest.name());
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("");

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setGlobalFilters(globalFilters());
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        // 添加自定义filter
        factoryBean.setFilters(CustomShiroFilterEnum.createInstanceMap(null));
        return factoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 插件
        chain.addPathDefinition("/swagger/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/v2/api-docs", DefaultFilter.anon.name());
        chain.addPathDefinition("/swagger-ui.html", DefaultFilter.anon.name());
        chain.addPathDefinition("/webjars/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/swagger-resource/**", DefaultFilter.anon.name());

        // 静态资源
        chain.addPathDefinition("/css/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/img/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/js/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/json/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/lib/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/plugin/**", DefaultFilter.anon.name());
        chain.addPathDefinition("/tpl/**", DefaultFilter.anon.name());

        // 测试用
        chain.addPathDefinition("/index", "authc");

        // todo 权限待放开 "/**" 必须放在最后
        chain.addPathDefinition("/**", DefaultFilter.anon.name());
        return chain;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        // 解决因@RequireRole等shiro注解导致的404问题
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        JavaUuidSessionIdGenerator idGenerator = new JavaUuidSessionIdGenerator();
        return idGenerator;
    }

    @Bean
    public SessionListener sessionListener() {
        CustomSessionListener sessionListener = new CustomSessionListener();
        return sessionListener;
    }


    // 集成本地 session 管理
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // http://localhost:8080/login;JSESSIONID=ae3b2bee-fc9d-462f-b715-f6604efdb9c9 报错400
        // 解决第一次请求时，重定向url会被重写成带JSESSIONID问题 （因为第一次请求时无cookie或cookie中无JSESSIONID）
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationInterval(30 * 60 * 1800);
        // 全局session过期时间
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);

        // 注入session监听器
        List<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(sessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        return sessionManager;
    }

    // ========= 集成redis session 管理 =========================


}
