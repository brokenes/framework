package org.github.framework.common.serializer;

/**
 * 序列化接口
 */
public interface ObjectSerializer {
	
	/**
     * 将一个对象主序列化
     * @param object 要序列化的对象
     * @return
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化一个对象
     * @param bytes 字节数组
     * @param type 对象类型
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> type);
	
	/**
	 * 把参数序列化成二进制
	 * @param params
	 * @return
	 */
	byte[] serializeArray(Object[] params);
	
	/**
	 * 把二进制反序列化成参数
	 * @param bytes
	 * @return
	 */
	Object[] deserializeArray(byte[] bytes);
}
