package org.github.framework.common.serializer.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.framework.common.mapper.JsonMapper;
import org.github.framework.common.serializer.AbstractObjectSerializer;
import org.github.framework.common.serializer.SerializeException;

import java.io.IOException;

/**
 * @ClassName JacksonObjectSerializer
 * @Description Jackson序列化器
 */
public class JacksonObjectSerializer extends AbstractObjectSerializer {

    private final ObjectMapper objectMapper;

    public JacksonObjectSerializer() {
        this.objectMapper = JsonMapper.OBJECT_MAPPER;
        this.objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        this.objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    protected <T> T doDeserialize(final byte[] bytes, final Class<T> type) {
        try {
            return objectMapper.readValue(bytes, type);
        }
        catch (final IOException e) {
            throw new SerializeException("使用Jackson串行化异常", e);
        }
    }

    public <T> T doDeserialize(final byte[] bytes, final JavaType type) {
        try {
            return objectMapper.readValue(bytes, type);
        }
        catch (final IOException e) {
            throw new SerializeException("使用Jackson串行化异常", e);
        }
    }

    @Override
    protected <T> byte[] doSerialize(final T object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        }
        catch (final JsonProcessingException e) {
            throw new SerializeException("使用Jackson串行化异常", e);
        }
    }

    @Override
    protected byte[] doSerializeArray(final Object[] params) {
        try {
            return objectMapper.writeValueAsBytes(params);
        }
        catch (final JsonProcessingException e) {
            throw new SerializeException("使用Jackson串行化异常", e);
        }
    }

    @Override
    protected Object[] doDeserializeArray(final byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, new TypeReference<Object[]>() {});
        }
        catch (final IOException e) {
            throw new SerializeException("使用Jackson串行化异常", e);
        }
    }
}
