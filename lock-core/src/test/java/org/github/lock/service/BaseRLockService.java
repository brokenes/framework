
package org.github.lock.service;


import org.github.framework.lock.annotation.Locking;
import org.github.framework.lock.enums.LockProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class BaseRLockService {
    @Autowired
    LockDemoService lockDemoService;

    private AtomicInteger incr = new AtomicInteger(0);

    @Locking(id = "'simpleLock:' + #hello",module = "baseRlock",provider = LockProviderType.REDIS)
    public void simpleLock(String hello){

        System.out.println("*********************hello****************************"+hello);
    }
}
