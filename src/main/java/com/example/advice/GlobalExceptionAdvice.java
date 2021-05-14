package com.example.advice;

import com.example.common.entity.Result;
import com.example.common.error.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author caohaifengx@163.com 2021-05-12 16:48
 * com.example.controller.rest 指定扫描包，用于区分
 * （或者写个BaseController继承 assignableTypes = xxxController.class）
 * 对Exception进行增强
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:{}", e.getMessage(), e);
        return Result.error(ErrorCodeEnum.PARAMS_ILLEGAL.getCode(), ErrorCodeEnum.PARAMS_ILLEGAL.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("Exception:{}", e.getMessage(), e);
        return Result.error(ErrorCodeEnum.UNDEF_ERR.getCode(), ErrorCodeEnum.UNDEF_ERR.getMessage());
    }


}
