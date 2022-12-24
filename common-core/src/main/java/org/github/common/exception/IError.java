package org.github.common.exception;


import org.github.common.IEnum;
import org.github.common.i18n.I18N;

public interface IError<T> extends IEnum<String> {

    /**
     * 业务错误码
     */
    String code();

    /**
     * 业务错误描述
     */
    String message();

    @Override
    default String value() {
        return message();
    }

    @Override
    default String named() {
        return code();
    }

    /**
     * 创建异常实例
     */
    default BusinessException makeException(final Object... objects) {
        final String code = code();
        return Ex.business(code, I18N.resolve(code, objects));
    }

}