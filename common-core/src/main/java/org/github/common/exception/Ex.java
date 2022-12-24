package org.github.common.exception;

import org.github.common.lang.CustomStringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * 异常工具创建类，用于快速抛出异常.
 * 主要用法有：
 *     //抛出业务异常
 *     throw Ex.business(msg);
 *
 *     //抛出验证异常
 *     throw Ex.violation(msg);
 */
public class Ex {
    /**
     * 业务异常
     */
    public static BusinessException business(String code,String msg,Object...params) {
        return new BusinessException(code,buildMessage(msg,params));
    }

    /**
     * 验证异常
     */
    public static ViolationException violation(String msg, Object...params) {
        return new ViolationException(buildMessage(msg,params));
    }


    public static CryptoException crypto(String msg,Object...params) {
        return new CryptoException(buildMessage(msg,params));
    }

    public static CryptoException crypto(Throwable throwable,String msg,Object...params) {
        String message = buildMessage(msg,params);
        if (CustomStringUtils.isNotBlank(message)) {
            return new CryptoException(message,throwable);
        }
        return new CryptoException(throwable);
    }

    /**
     * 系统错误异常
     */
    public static SystemErrorException systemError(Throwable throwable,String msg,Object...params) {
        String message = buildMessage(msg,params);
        if (CustomStringUtils.isNotBlank(message)) {
            return new SystemErrorException(message,throwable);
        }
        return new SystemErrorException(throwable);
    }

    /**
     * 系统错误异常
     */
    public static SystemErrorException systemError(String msg,Object...params) {
        String message = buildMessage(msg,params);
        return new SystemErrorException(message);
    }

    //--------------------------------------------exception处理----------------

    /**
     *
     * 将ErrorStack转化为String
     *
     */
    public static String getStackTraceAsString(final Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        final StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     *
     * 判断异常是否由某些底层的异常引起.
     *
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(final Exception exception,
                                     final Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = exception.getCause();
        while (cause != null) {
            for (final Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 判断是否是一个 BaseException 异常
     */
    public static boolean isCauseWlbankException(final Exception exception) {
        if (exception.getCause() != null && exception.getCause() instanceof BaseException) {
            return true;
        }
        return false;
    }

    /**
     * 构建格化式消息
     */
    private static String buildMessage(String msg,Object...params) {
        if (params == null || params.length <= 0) {
            return msg;
        }
        return String.format(msg,params);
    }

}
