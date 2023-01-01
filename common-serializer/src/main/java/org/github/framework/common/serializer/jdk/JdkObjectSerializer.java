package org.github.framework.common.serializer.jdk;

import org.github.framework.common.serializer.AbstractObjectSerializer;
import org.github.framework.common.serializer.SerializeException;

import java.io.*;

/**
 * jdk提供的序列化
 */
public class JdkObjectSerializer extends AbstractObjectSerializer {

	@Override
	protected byte[] doSerializeArray(Object[] params) {
		return doSerialize(params);
	}

	@Override
	protected Object[] doDeserializeArray(byte[] bytes ) {

		try (InputStream in = new ByteArrayInputStream(bytes)) {
			ObjectInputStream ois = new ObjectInputStream(in);
			Object[] result = (Object[]) ois.readObject();

			return result;
		} catch (Exception e) {
			throw new SerializeException(null,"java object参数反序列化异常!", e);
		}
	}

	@Override
	protected <T> T doDeserialize(byte[] bytes, Class<T> type) {
		try {
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			ObjectInputStream inputStream = new ObjectInputStream(bin);
			Object object = inputStream.readObject();
			inputStream.close();
			bin.close();
			return (T) object;
		} catch (Exception e) {
			throw new SerializeException(null,"java object参数反序列化异常!", e);
		}
	}

	@Override
	protected <T> byte[] doSerialize(T object) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(object);
			oos.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw new SerializeException(null,"java object参数序列化异常!", e);
		}
	}

}
