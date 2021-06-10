package com.example.config;

import com.example.interceptor.LimitRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caohaifengx@163.com 2021-05-31 16:33
 * 不建议使用 WebMvcConfigurationSupport 建议使用 WebMvcConfigurer
 */
//@Component
@Deprecated
public class InterceptorConfig extends WebMvcConfigurationSupport {
    //WebMvcConfigurerAdapter已经过时

    private static final String FAVICON_URL = "/favicon.ico";

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //设置（模糊）匹配的url
        // todo 将需要管理的接口url配置至数据库，并进行缓存
        List<String> urlPatterns = new ArrayList();
        //urlPatterns.add("/tet");
        urlPatterns.add("/api/v1/userinfo/*");

        registry.addInterceptor(methodInterceptor())
                .addPathPatterns(urlPatterns).excludePathPatterns(FAVICON_URL);
        super.addInterceptors(registry);
    }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * Override this method to add resource handlers for serving static resources
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/").addResourceLocations("/**");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 配置servlet处理
     */
    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LimitRequestInterceptor methodInterceptor() {
        return new LimitRequestInterceptor();
    }
}
