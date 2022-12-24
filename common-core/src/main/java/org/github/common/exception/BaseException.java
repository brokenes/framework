package org.github.common.exception;

import org.github.common.ErrorMessage;
import org.github.common.Message;
import org.github.common.lang.CustomStringUtils;

import java.io.Serializable;

/***
 * 基础异常类
 */
public class BaseException extends RuntimeException implements Message {

    protected String code;

    public BaseException(Throwable cause) {
        super(cause);
        this.code = getDefaultCode();
    }


    public BaseException(String message,Throwable cause) {
        super(message,cause);
        this.code = getDefaultCode();
    }

    public BaseException(String message) {
        super(message);
        this.code = getDefaultCode();
    }

    public BaseException(final String code, final String message) {
        this(message);
        this.code = CustomStringUtils.defaultString(code, getDefaultCode());
    }

    public BaseException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = CustomStringUtils.defaultString(code, getDefaultCode());
    }


    @Override
    public Serializable getCode() {
        return null;
    }

    public String getDefaultCode() {
        return ErrorMessage.DEFAULT_ERROR_CODE;
    }
}
