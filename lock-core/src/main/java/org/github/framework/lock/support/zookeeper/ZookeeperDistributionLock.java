package org.github.framework.lock.support.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.github.framework.lock.BaseDistributionLock;
import org.github.framework.lock.LockInfo;
import org.github.framework.lock.exception.LockException;
import org.github.framework.lock.DistributionLock;

import java.util.concurrent.TimeUnit;

/**
 * 基于ZK的InterProcessMutex实现的分布式独占锁
 */
@Slf4j
public class ZookeeperDistributionLock extends BaseDistributionLock implements DistributionLock {

    private InterProcessMutex innerLock;

    private LockInfo lockInfo;

    private TimeUnit timeUnit;
 


    public ZookeeperDistributionLock(InterProcessMutex innerLock, LockInfo lockInfo ) {
        this.innerLock  = innerLock;
        this.timeUnit   = TimeUnit.MILLISECONDS;
        this.lockInfo   = lockInfo; 
    }

    @Override
    public void lock() {
        try {
            //计时器
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            //logging
            log.info("Try to get lock from zookeeper -> lockId:{},lockURI:{},lockProvider:{},waitTime:{},expiredTime:{},startMills:{}",
                            lockInfo.getId(),
                            lockInfo.getLockURI(),
                            lockInfo.getProviderType(),
                            lockInfo.getWaitTime(),
                            lockInfo.getExpiredTime(),
                            stopWatch.getStartTime());

            if (innerLock.acquire(lockInfo.getWaitTime(), timeUnit)) {
                log.info(" Get lock[{}] from zookeeper success~",lockInfo.getLockURI() );
            } else {
                throw new LockException(lockInfo.getLockedAlert());
            }
        } catch (LockException ex) {
                throw   ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            throw new LockException(String.format("Get lock[%s] failed!",lockInfo.getLockURI()),ex);
        }
    }


    @Override
    public boolean tryLock() {
        return  tryLock(lockInfo.getWaitTime(),timeUnit);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)  {
        try {
            return innerLock.acquire(time, unit);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            throw new LockException(String.format("Get Lock[%s] failed！",lockInfo.getLockURI()),ex);
        }
    }

    @Override
    public void unlock() {
        if (innerLock != null ) {
            try {
                log.info("Start Release lock[{}]~",lockInfo.getLockURI());
                if (innerLock.isAcquiredInThisProcess()) {
                    innerLock.release();
                }
                log.info("Release lock[{}] success~",lockInfo.getLockURI());
            } catch (Exception ex) {
                throw new LockException(String.format("Release lock [%s] failed！",lockInfo.getLockURI()),ex);
            } finally {
                ZookeeperInnerLockHolder.releaseMutexLock(lockInfo.getLockURI());
            }
        }
    }

}
