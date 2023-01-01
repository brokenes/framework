package org.github.framework.lock.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ZookeeperLockProperties
 * @Description zookeeper锁相关配置
 */

@Component
public class ZookeeperLockProperties {

    @Value("${lock.zk.connectionTimeoutMs:10000}")
    private Integer connectionTimeoutMs;

    @Value("${lock.zk.sessionTimeoutMs:6000}")
    private Integer sessionTimeoutMs;

    @Value("${lock.zk.baseSleepTimeMs:100}")
    private Integer baseSleepTimeMs;

    @Value("${lock.zk.maxRetries:5}")
    private Integer maxRetries;

    @Value("${lock.zk.server}")
    private String server;


    public Integer getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public Integer getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public Integer getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
