package org.github.framework.common.serializer;


import org.github.framework.common.exception.BaseException;

/**
 * SerializeException
 * 序列化异常，当使用对象序列化器出现问题时，将抛出此异常
 *
 */
public class SerializeException extends BaseException {

    public SerializeException(Throwable cause) {
        super(cause);
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message,Throwable cause) {
        super(message,cause);
    }

    public SerializeException(String code, String message) {
        super(code, message);
    }

    public SerializeException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    @Override
    public String getDefaultCode() {
        return ERROR_CODE;
    }

    private static final String ERROR_CODE = "SERIALIZE_ERR";
}
