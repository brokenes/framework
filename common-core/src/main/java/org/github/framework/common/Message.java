package org.github.framework.common;

import java.io.Serializable;

/**
 * 定义dto对象，包含code和message
 *
 */
public interface Message<T extends Serializable> {

    T getCode();

    String getMessage();
}
