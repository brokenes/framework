package org.github.framework.common.serializer.hession;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.github.framework.common.serializer.AbstractObjectSerializer;
import org.github.framework.common.serializer.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @ClassName HessianObjectSerializer
 * @Description hessian 序列化
 */
public class HessianObjectSerializer extends AbstractObjectSerializer {
    private boolean allowUnSerializable = true;

    @Override
    protected byte[] doSerializeArray(Object[] params) {
        return doSerialize(params);
    }

    @Override
    protected Object[] doDeserializeArray(byte[] bytes ) {

        try (InputStream in = new ByteArrayInputStream(bytes)) {
            Hessian2Input hessianInput = new Hessian2Input(in);
            hessianInput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
            Object[] result = (Object[]) hessianInput.readObject(Object[].class);
            return result;
        } catch (Exception e) {
            throw new SerializeException("hessian参数反序列化异常!", e);
        }
    }

    @Override
    protected <T> T doDeserialize(byte[] bytes, Class<T> type) {
        try (InputStream in = new ByteArrayInputStream(bytes)) {
            Hessian2Input hessianInput = new Hessian2Input(in);
            hessianInput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
            return (T) hessianInput.readObject();
        } catch (Exception e) {
            throw new SerializeException("hessian参数反序列化异常!", e);
        }
    }

    @Override
    protected <T> byte[] doSerialize(T object) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Hessian2Output hessianOutput = new Hessian2Output(out);
            hessianOutput.getSerializerFactory().setAllowNonSerializable(allowUnSerializable);
            hessianOutput.writeObject(object);
            hessianOutput.flush();
            return out.toByteArray();
        } catch (Exception e) {
            throw new SerializeException("hessian参数序列化异常!", e);
        }
    }
}
