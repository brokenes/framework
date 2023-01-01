package org.github.framework.common;

/***
 * 枚举类型的值域对象，所有的枚举类型实现该接口；并提供基本的操作
 *
 */
public interface IEnum <T>{

    T value();

    String named();
}
