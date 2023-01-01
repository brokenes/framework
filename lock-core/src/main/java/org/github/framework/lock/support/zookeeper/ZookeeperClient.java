package org.github.framework.lock.support.zookeeper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.github.framework.common.lang.CheckUtils;
import org.github.framework.common.serializer.ObjectSerializer;
import org.github.framework.common.serializer.json.JacksonObjectSerializer;
import org.github.framework.lock.exception.LockException;
import org.github.framework.lock.config.ZookeeperLockProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ApplicationObjectSupport;

/**
 * 简易Zookeeper客户端封装
 */
@Slf4j
public class ZookeeperClient extends ApplicationObjectSupport implements InitializingBean,DisposableBean {

    @Setter
    private ZookeeperLockProperties properties;

    @Getter
    private CuratorFramework curatorFramework;

    @Setter
    private ObjectSerializer objectSerializer;

    /** 是否使用外部注入的curatorFramework */
    private boolean isUseDI;

    public void setData(String path, Object object) {
        try {
            this.curatorFramework.setData().forPath(path,objectSerializer.serialize(object));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new LockException(String.format("向Zookeeper写数据时出错了，路径:%s",path),e);
        }
    }

    public void createNode(String path, CreateMode createMode) {
        try {
            this.curatorFramework.create().withMode(createMode).forPath(path);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new LockException(String.format("向Zookeeper写数据时出错了，路径:%s",path),e);
        }
    }

    public <T> T getData(String path, Class<T> clazz) {
        try {
            byte[] bytes = this.curatorFramework.getData().forPath(path);
            return  objectSerializer.deserialize(bytes,clazz);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new LockException(String.format("向Zookeeper读取数据时出错了，路径:%s",path),e);
        }
    }

    public void delete(String path) {
        try {
            this.curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new LockException(String.format("向Zookeeper删除数据时出错了，路径:%s",path),e);
        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.curatorFramework   = getApplicationContext().getBean(CuratorFramework.class);
            this.isUseDI            = true;
            this.objectSerializer   = new JacksonObjectSerializer();
        } catch (NoSuchBeanDefinitionException e) {
            CheckUtils.hasText(properties.getServer(),"the 'server' parameter is empty!");
            try {
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries());
                this.curatorFramework =
                        CuratorFrameworkFactory.builder().connectString(properties.getServer())
                            .retryPolicy(retryPolicy)
                            .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                            .sessionTimeoutMs(properties.getSessionTimeoutMs())
                            .build();
                this.curatorFramework.start();
                this.objectSerializer   = new JacksonObjectSerializer();
                log.debug("start curatorFramework~");
            } catch (Exception ex) {
                log.error(ex.getMessage(),ex);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            if (this.curatorFramework != null && !isUseDI) {
                log.debug("release curatorFramework~");
                this.curatorFramework.close();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }
    }

}
