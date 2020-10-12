package com.nynu.goule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ExceptionResponse {
    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public SystemMessage generalException(GeneralException e){
        return new SystemMessage("-9999", e.getMessage());
    }
}
