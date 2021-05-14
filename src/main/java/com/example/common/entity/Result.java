package com.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caohaifengx@163.com 2021-05-12 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 状态 true/false
     */
    private boolean status;

    /**
     * 状态码
     */
    private String code;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    public static Result success() {
        return success(null);
    }

    public static <T> Result success(T data) {
        return success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result success(String code, String message, T data){
        return new Result(true, code, message, data);

    }

    public static Result error() {
        return error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }

    public static Result error(String message) {
        return error(ResultEnum.ERROR.getCode(), message);
    }

    public static Result error(String code, String message) {
        return new Result(false, code, message, null);
    }


    @AllArgsConstructor
    @Getter
    private enum ResultEnum {

        SUCCESS("SYS200", "操作成功"),
        ERROR("SYS400", "操作失败"),
        ;

        private String code;
        private String message;
    }
}
