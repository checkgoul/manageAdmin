package com.nynu.goule.exception;

/**
 * 抛出异常
 */
public class GeneralException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    //用于抛出异常信息
    public GeneralException(String message) {
        super(message);
    }
}
