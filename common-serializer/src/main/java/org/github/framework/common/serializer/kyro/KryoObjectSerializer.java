package org.github.framework.common.serializer.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.github.framework.common.serializer.AbstractObjectSerializer;
import org.github.framework.common.serializer.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName KryoObjectSerializer
 * @Description kryo序列化器
 * 注意：kryo不支持内部匿名类！！！
 */
public class KryoObjectSerializer extends AbstractObjectSerializer {

    @Override
    protected <T> T doDeserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = KRYO_HOLDER.get();
        Registration registration = REGISTERION_MAP.get(clazz);
        register(clazz,kryo);
        ByteArrayInputStream byteArrayInputStream =  new ByteArrayInputStream(bytes);
        try(Input input = new Input(byteArrayInputStream)) {
            return (T) kryo.readObject(input, registration.getType());
        } catch (Exception e) {
            throw new SerializeException("使用kryo序列化异常",e);
        }
    }

    @Override
    protected <T> byte[] doSerialize(T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        Kryo kryo = KRYO_HOLDER.get();
        register(clazz,kryo);
        return doOut(kryo,object);
    }


    @Override
    protected byte[] doSerializeArray(Object[] params) {
        Kryo kryo = KRYO_HOLDER.get();
        return doOut(kryo,params);
    }


    @Override
    protected Object[] doDeserializeArray(byte[] bytes) {
        Kryo kryo = KRYO_HOLDER.get();
        ByteArrayInputStream byteArrayInputStream =  new ByteArrayInputStream(bytes);
        try(Input input = new Input(byteArrayInputStream)) {
            return   kryo.readObject(input, Object[].class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializeException("使用kryo序列化异常",e);
        }
    }


    private void register(Class clazz,Kryo kryo) {
        if (!REGISTERION_MAP.containsKey(clazz)) {
            Registration registration = kryo.register(clazz);
            REGISTERION_MAP.put(clazz, registration);
        }
    }

    private byte[] doOut(Kryo kryo, Object object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Output output = new Output(outputStream)) {
            kryo.writeObject(output, object);
            output.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializeException("使用kryo序列化异常",e);
        }
    }

    /**
     * kryo不是线程安全的，必须每个线程都持有各自的实例
     */
    private static final ThreadLocal<Kryo> KRYO_HOLDER = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(Collection.class);
        kryo.register(Map.class);
        kryo.register(List.class);
        kryo.register(Object[].class);

        return kryo;
    });

    /**
     * kryo注册接口不检测是否已有注册，所以得自己加一个map来实现
     */
    private static Map<Class, Registration> REGISTERION_MAP = new ConcurrentHashMap();

}
