package com.example.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author caohaifengx@163.com 2021-05-12 16:20
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements ErrorCode {

    SESSION_OUT("SYS401", "会话超时"),
    UNDEF_ERR("UNDEF_001", "未知异常"),

    PARAMS_ERR("SYS1001", "参数错误"),
    PARAMS_UNDEF("SYS1002", "参数未定义"),
    PARAMS_ILLEGAL("SYS1003", "参数非法"),
    ;

    private String code;
    private String message;
}
