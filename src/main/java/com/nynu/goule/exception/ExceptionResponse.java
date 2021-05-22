package com.nynu.goule.exception;

import com.nynu.goule.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 抽象类，抛出自定义异常入口
 */
public abstract class ExceptionResponse {
    @ExceptionHandler(GeneralException.class)
    //自定义异常来配合使用返回自定义响应状态码和自定义错误信息给客户端。
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public SystemMessage generalException(GeneralException e) {
        //获取异常状态码和异常信息
        return new SystemMessage(Result.RTN_CODE.ERROR, e.getMessage());
    }
}
