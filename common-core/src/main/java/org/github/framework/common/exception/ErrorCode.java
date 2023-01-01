package org.github.framework.common.exception;

/**
 * 错误消息码
 */
public enum ErrorCode implements IError<String> {
    /**
     * 认证错误编码信息
     */
    AUTH("403","%s 认证异常"),

    BUSINESS("501","业务异常"),

    CRYPTO("9005","加密解密或加签验证异常"),

    NETWORK("9103","网络异常"),

    PERMISSION("401","没有权限"),

    SYSTEM("500","系统错误"),

    VIOLATION("9001","验证异常"),

    PERSISTANCE("9003","持久化异常"),

    TIMEOUT("9999","服务超时异常"),

    ;

    String code;
    String message;


    ErrorCode(String code,String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
