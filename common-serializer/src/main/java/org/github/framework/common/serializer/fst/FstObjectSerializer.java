package org.github.framework.common.serializer.fst;

import org.github.framework.common.serializer.AbstractObjectSerializer;
import org.github.framework.common.serializer.SerializeException;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;

import java.io.ByteArrayInputStream;

/**
 * @ClassName FstObjectSerializer
 * @Description FST序列化器
 */
public class FstObjectSerializer extends AbstractObjectSerializer {

    static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();

    @Override
    protected <T> T doDeserialize(byte[] bytes, Class<T> type) {
        try {
            return (T) configuration.asObject(bytes);
        } catch (Exception e) {
            throw new SerializeException("使用FST序列异常",e);
        }
    }

    @Override
    protected <T> byte[] doSerialize(T object) {
        try {
            return configuration.asByteArray(object);
        } catch (Exception e) {
            throw new SerializeException("使用FST序列异常",e);
        }
    }

    @Override
    protected byte[] doSerializeArray(Object[] params) {
        try {
            return configuration.asByteArray(params);
        } catch (Exception e) {
            throw new SerializeException("使用FST序列异常",e);
        }
    }

    @Override
    protected Object[] doDeserializeArray(byte[] bytes) {
        try (FSTObjectInput fstObjectInput = new FSTObjectInput(new ByteArrayInputStream(bytes))){
            Object[] array = (Object[]) fstObjectInput.readObject(Object[].class);
            return array;
        } catch (Exception e) {
            throw new SerializeException("使用FST序列异常",e);
        }
    }
}
