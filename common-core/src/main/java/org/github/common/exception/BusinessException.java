package org.github.common.exception;

/***
 * 定义业务异常
 */
public class BusinessException extends BaseException{

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String code, final String message) {
        super(code, message);
    }

    public BusinessException(final String code, final String message, final Throwable cause) {
        super(code, message, cause);
    }
}
