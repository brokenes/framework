package org.github.framework.lock;

public interface DistributionReadWriteLock {

    DistributionLock readLock();

    DistributionLock writeLock();
}
