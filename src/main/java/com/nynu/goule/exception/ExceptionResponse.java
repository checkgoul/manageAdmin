package com.nynu.goule.exception;

import com.nynu.goule.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ExceptionResponse {
    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public SystemMessage generalException(GeneralException e) {
        return new SystemMessage(Result.RTN_CODE.ERROR, e.getMessage());
    }
}
