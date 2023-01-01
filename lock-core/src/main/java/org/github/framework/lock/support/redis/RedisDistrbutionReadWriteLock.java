package org.github.framework.lock.support.redis;

import org.github.framework.lock.DistributionReadWriteLock;
import org.github.framework.lock.LockInfo;
import org.github.framework.lock.DistributionLock;
import org.redisson.api.RReadWriteLock;

public class RedisDistrbutionReadWriteLock implements DistributionReadWriteLock {

    private RReadWriteLock readWriteLock;

    private LockInfo lockInfo;

    public RedisDistrbutionReadWriteLock(RReadWriteLock readWriteLock,LockInfo lockInfo) {

        this.readWriteLock = readWriteLock;
        this.lockInfo = lockInfo;
    }

    @Override
    public DistributionLock readLock() {
        return new RedisDistrbutionLock(readWriteLock.readLock(),lockInfo);
    }

    @Override
    public DistributionLock writeLock() {
        return new RedisDistrbutionLock(readWriteLock.writeLock(),lockInfo);
    }
}
