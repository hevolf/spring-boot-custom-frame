package com.example.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caohaifengx@163.com 2021-05-08 17:24
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String FAVICON_URL = "/favicon.ico";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //configurer.setUseSuffixPatternMatch(true);
        //configurer.setUseRegisteredSuffixPatternMatch(
        //        this.mvcProperties.getPathmatch().isUseRegisteredSuffixPattern());
        //configurer.setUseTrailingSlashMatch(false);// /user 和 /user/是否匹配  （是否删除末尾/进行匹配）
    }

    /*
    * 解决统一返回体中String类型不能转换成json的问题
    * String类型默认采用StringHttpMessageConverter
    * */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        return new FastJsonHttpMessageConverter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置（模糊）匹配的url
        // todo 将需要管理的接口url配置至数据库，并进行缓存
        List<String> urlPatterns = new ArrayList();
        urlPatterns.add("/tet");
        urlPatterns.add("/api/v1/userinfo/*");

        registry.addInterceptor(limitRequestInterceptor())
                .addPathPatterns(urlPatterns).excludePathPatterns(FAVICON_URL);
    }

    @Bean
    public LimitRequestInterceptor limitRequestInterceptor() {
        return new LimitRequestInterceptor();
    }
}
