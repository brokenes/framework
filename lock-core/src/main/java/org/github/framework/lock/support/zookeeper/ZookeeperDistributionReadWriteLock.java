package org.github.framework.lock.support.zookeeper;

import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.github.framework.lock.DistributionReadWriteLock;
import org.github.framework.lock.LockInfo;
import org.github.framework.lock.DistributionLock;


public class ZookeeperDistributionReadWriteLock implements DistributionReadWriteLock {

    private InterProcessReadWriteLock interProcessReadWriteLock;

    private LockInfo lockInfo;

    public ZookeeperDistributionReadWriteLock(InterProcessReadWriteLock interProcessReadWriteLock,LockInfo lockInfo) {
        this.interProcessReadWriteLock = interProcessReadWriteLock;
        this.lockInfo  = lockInfo;
    }

    @Override
    public DistributionLock readLock() {

        return new ZookeeperDistributionLock(interProcessReadWriteLock.readLock(),lockInfo);
    }

    @Override
    public DistributionLock writeLock() {
        return new ZookeeperDistributionLock(interProcessReadWriteLock.writeLock(),lockInfo);
    }
}
