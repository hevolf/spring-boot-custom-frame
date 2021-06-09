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
@RestControllerAdvice(basePackages = "com.example.controller")
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 是否进行统一返回体操作（如：使用注解方式）
        //!returnType.hasMethodAnnotation(IgnoreRestBody.class);

        // 仅支持以下两种方式 返回类型void 或 非Result 和 非String类型
        return returnType.getParameterType().equals(Void.TYPE)
                || (!returnType.getParameterType().equals(String.class)
                && !returnType.getParameterType().equals(Result.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getParameterType().equals(Void.TYPE)) {
            return Result.success(null);
        }
        if (!returnType.getParameterType().equals(Result.class)) {
            return Result.success(body);
        }
        return Result.success(null);
    }
}
