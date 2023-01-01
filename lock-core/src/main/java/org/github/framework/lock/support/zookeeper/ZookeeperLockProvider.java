package org.github.framework.lock.support.zookeeper;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.github.framework.common.lang.CheckUtils;
import org.github.framework.lock.DistributionReadWriteLock;
import org.github.framework.lock.LockInfo;
import org.github.framework.lock.DistributionLock;
import org.github.framework.lock.LockProvider;
import org.github.framework.lock.enums.LockProviderType;
import org.springframework.beans.factory.InitializingBean;


@Slf4j
public class ZookeeperLockProvider implements LockProvider, InitializingBean {

    /**基于zookeeper锁节点的工具类*/
    @Setter
    private ZookeeperClient zookeeperClient;

    @Override
    public DistributionLock createMutexLock(LockInfo lockInfo) {
        return new ZookeeperDistributionLock(
                ZookeeperInnerLockHolder.getOrCreateMutexLock(
                        zookeeperClient.getCuratorFramework(),lockInfo.getLockURI()),lockInfo);
    }

    @Override
    public DistributionReadWriteLock createReadWriteLock(LockInfo lockInfo) {

//        return new ZookeeperDistributionReadWriteLock();
        return null;
    }

    @Override
    public LockProviderType getLockProviderType() {
        return LockProviderType.ZOOKEEPER;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CheckUtils.notNull(this.zookeeperClient,"ZKClient为空！");
    }
}
