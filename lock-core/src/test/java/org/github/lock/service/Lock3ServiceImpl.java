package org.github.lock.service;


import org.github.framework.lock.annotation.Locking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class Lock3ServiceImpl {

    private Logger logger = LoggerFactory.getLogger(Lock3ServiceImpl.class);

    @Locking(id = "#lockName", module = "lock.test.1")
    public void lock(String lockName){
        logger.info(this.getClass().getName() + "-------lock...." + lockName);
        logger.info(this.getClass().getName() + "-------release");
    }
}
