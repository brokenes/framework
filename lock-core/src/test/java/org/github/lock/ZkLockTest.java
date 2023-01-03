package org.github.lock;


import org.apache.commons.lang3.RandomUtils;
import org.github.framework.lock.DistributionLock;
import org.github.framework.lock.LockManager;
import org.github.framework.lock.exception.LockException;
import org.github.lock.service.LockDemoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {LockTestApplication.class},properties = {"zk.host=localhost:2180"
        //,"dz.lock.onlyUseZkLock=true"
})
public class ZkLockTest {

    @Autowired
    LockManager lockManager;

    @Autowired
    LockDemoService lockDemoService;

//    @Autowired
//    Lock1ServiceImpl lock1Service;

    @Test
    public void testLock() {

        try( DistributionLock lock = lockManager.createLock("WR","10001")) {
            lock.lock();
            System.out.println("I 'm get the lock ");
        }
    }

    @Test
    public void testNest() {
//        lockDemoService.nestInvoke();
    }

    /**
     * 用10个线程测试并发获取锁，应该只有一个线程能拿到锁
     */
    @Test
    public void testConcurrency() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        final CountDownLatch latch = new CountDownLatch(10);
        final AtomicInteger doStatus = new AtomicInteger(0);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("我到了" + Thread.currentThread().getName());
                    cyclicBarrier.await();
                    System.out.println("开始竟争锁" + Thread.currentThread().getName());
                    lockDemoService.sayNum(RandomUtils.nextInt());
//                    lockDemoService.nestInvoke();
                    doStatus.incrementAndGet();
                    latch.countDown();
                    System.out.println("拿到锁并且完成任务了" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (LockException e) {
                    latch.countDown();
//                    e.printStackTrace();
                    System.out.println("锁等待超时了" + Thread.currentThread().getName() + e.getMessage());
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        };

        for (int i = 0,  len = 10;i < len; i++ ) {
            executor.submit(runnable);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(doStatus.get()==1);
        executor.shutdown();
    }

    @Test
    public void testAop() {
        lockDemoService.hello("XXXX");
    }

    @Test
    public void testAopRedis() {
        lockDemoService.helloRedis("XXXX","NIHao");
    }

    @Test
    public void testSaveSimple() {
        lockDemoService.saveSimple(new LockDemoService.Simple("1","simpl Name 1"));
    }


//    @Test
//    public void lock(){
//        final String lockName = "aaaa";
//        lock1Service.lock(lockName);
//    }
}
