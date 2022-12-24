package org.github.common.exception;

/**
 *
 * 认证异常信息类，当访问某个资料未登录或者未认证抛出此异常
 *
 */
public class AuthException extends BaseException {
    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(final String message,final Throwable cause) {
        super(message, cause);
        this.code = getDefaultCode();
    }

    public AuthException(String code, String message) {
        super(code, message);
    }

    public AuthException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    @Override
    public String getDefaultCode() {
        return ErrorCode.AUTH.value();
    }
}
