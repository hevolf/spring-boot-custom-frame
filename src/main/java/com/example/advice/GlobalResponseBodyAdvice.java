package com.example.advice;

import com.example.common.entity.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author caohaifengx@163.com 2021-05-13 10:49
 * 对ResponseBody进行增强（可进行加解密）
 */
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 是否进行统一返回体操作（如：使用注解方式）
        //!returnType.hasMethodAnnotation(IgnoreRestBody.class);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (null == body) {
            return Result.success();
        } else if (body instanceof Result) {
            return body;
        } else {
            return Result.success(body);
        }
    }
}
