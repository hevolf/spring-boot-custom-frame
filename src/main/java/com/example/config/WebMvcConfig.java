package com.example.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caohaifengx@163.com 2021-05-08 17:24
 *
 * （推荐）MappingJackson2HttpMessageConverter 能保留注解@JsonFormat生效，但会存在类型String转换 统一返回体 异常问题
 * 解决方式：advice里过滤掉String，String类型不做统一返回（String类型统一返回意义不大）
 *
 * FastJsonHttpMessageConverter 可解决String类型转换异常问题，但注解@JsonFormat会失效
 *
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

    /**
     * String类型默认采用StringHttpMessageConverter
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());

    }


    /**
     * spring boot web 默认集成，可全局设置时间格式（可配置在配置文件）
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 设置日期格式, 注意此方式未解决时区问题
        // 配合@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")可解决
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        converter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(list);
        return converter;
    }

    /**
     * @return
     */
    //@Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 此方式会使 注解@JsonFormat 失效,会导致Date类型展示成时间戳
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter=fastConverter;
        return fastConverter;
    }


    /**
     * 添加拦截器 并 关联需要拦截的url
     * @param registry
     */
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
