package com.github.fakerUZI.exception;

/**
 * @author 曹聪
 * @create 2025/2/24-15:38
 * @description sdk中
 **/
public class SdkRuntimeException extends RuntimeException {

    public SdkRuntimeException(Throwable e) {
        super(e);
    }

    public SdkRuntimeException(String msg) {
        super(msg);
    }

    public SdkRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}