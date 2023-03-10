
package org.github.lock;


import lombok.extern.slf4j.Slf4j;
import org.github.framework.lock.DistributionLock;
import org.github.framework.lock.LockManager;
import org.github.lock.service.BaseRLockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {LockTestApplication.class},properties = {"lock.redis.servers=redis://127.0.0.1:6379"})
public class RedisLockTest {

    @Autowired
    BaseRLockService baseRLockService;

    @Autowired
    LockManager lockManager;

    @Test
    public void testBase() {

        baseRLockService.simpleLock("hello world");
    }

    @Test
    public void testLock() {
//        LockInfo lockInfo = SimpleLockInfo.of()
        try( DistributionLock lock = lockManager.createLock("WR","10001")) {
            lock.lock();
            log.info("***************获取分布式锁**************");
        }
    }
}
