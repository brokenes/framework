package org.github.framework.common.serializer;

import java.math.BigDecimal;

/**
 *
 * 抽象对象序列化器，这一层只处理java的基础包装类型，并封装若干模板方法由子类实现
 *
 */
public abstract class AbstractObjectSerializer implements ObjectSerializer {

	@Override
	public <T> byte[] serialize(T object) {
		if (object == null) {
			return new byte[0];
		}
		if (object instanceof byte[]) {
			return (byte[]) object;
		}
		if (object instanceof String) {
			return object.toString().getBytes();
		}
		if (object instanceof Long || object instanceof Integer || object instanceof Double || object instanceof Short
				|| object instanceof BigDecimal) {
			return object.toString().getBytes();
		}

		return doSerialize(object);

	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) {
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		if (type == String.class) {
			return (T) new String(bytes);
		}
		if (type == Integer.class) {
			return ((T) Integer.valueOf(new String(bytes)));
		}
		if (type == Long.class) {
			return (T) Long.valueOf(new String(bytes));
		}
		if (type == Double.class) {
			return (T) Double.valueOf(new String(bytes));
		}
		if (type == Short.class) {
			return (T) Short.valueOf(new String(bytes));
		}
		if (type == BigDecimal.class) {
			return (T) new BigDecimal(new String(bytes));
		}

		return doDeserialize(bytes, type);

	}

	/**
	 * 把参数序列化成二进制
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public byte[] serializeArray(Object[] params) {
		if (params == null) {
			return new byte[0];
		}

		return doSerializeArray(params);
	}

	/**
	 * 把二进制反序列化成参数
	 * 
	 * @param bytes
	 * @return
	 */
	@Override
	public Object[] deserializeArray(byte[] bytes) {
		if (bytes == null  ) {
			return new Object[0];
		}

		return doDeserializeArray(bytes);
	}

	/**
	 * 序列化
	 *
	 * @param object
	 * @param <T>
	 * @return
	 */
	protected abstract <T> byte[] doSerialize(T object);

	/**
	 * 反序列化
	 * @param bytes
	 * @param type
	 * @return
	 */
	protected abstract <T> T doDeserialize(byte[] bytes, Class<T> type);

	/**
	 * 把参数序列化成二进制
	 * 
	 * @param params
	 * @return
	 */
	protected abstract byte[] doSerializeArray(Object[] params);

	/**
	 * 把二进制反序列化成参数
	 * 
	 * @param bytes
	 * @return
	 */
	protected abstract Object[] doDeserializeArray(byte[] bytes);
}
