package com.example.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author caohaifengx@163.com 2021-05-08 17:24
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

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
}
