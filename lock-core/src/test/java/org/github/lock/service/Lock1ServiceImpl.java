package org.github.lock.service;

import org.github.framework.lock.annotation.Locking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class Lock1ServiceImpl {

    private Logger logger = LoggerFactory.getLogger(Lock1ServiceImpl.class);

    @Autowired
    private Lock2ServiceImpl lock2Service;



    @Locking(id = "#lockName", module = "lock.test")
    public void lock(String lockName){
        logger.info(this.getClass().getName() + "-------lock...." + lockName);

        lock2Service.lock(lockName);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info(this.getClass().getName() + "-------release");
    }
}
