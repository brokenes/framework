package org.github.framework.lock.support.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * zk锁对象持有者，因为默认InterProcessMutex中虽然是处理了重入，但是不是静态变更 也不是ThreadLocal，所以在上层直接创建此
 * 对象就必须考虑可重入情况，此类是提供一个static map的方式来实现可重入
 */
public class ZookeeperInnerLockHolder {


    private static ConcurrentMap<Thread, Map<String, InterProcessMutex>> threadLockMap = new ConcurrentHashMap<>();

    /**
     * 获取zk的排他锁，如果是重入，直接从cache取出
     * @param client curator client
     * @param path zk锁的路径，必须是全局唯一
     * @return
     */
    public static InterProcessMutex getOrCreateMutexLock(CuratorFramework client, String path){
        Thread thread = Thread.currentThread();
        Map<String, InterProcessMutex> innerLockMap = threadLockMap.get(thread);
        InterProcessMutex innerLock;
        if (innerLockMap == null){
            innerLock = new InterProcessMutex(client, path);
            innerLockMap = new HashMap<String, InterProcessMutex>();
            innerLockMap.put(path, innerLock);
            threadLockMap.put(thread, innerLockMap);
        } else {
            innerLock = innerLockMap.get(path);
            if (innerLock == null){
                innerLock = new InterProcessMutex(client, path);
                innerLockMap.put(path, innerLock);
            }
        }
        return innerLock;
    }

    /**
     * 释放zk锁
     * @param path
     */
    public static void releaseMutexLock(String path){
        Thread thread = Thread.currentThread();
        Map<String, InterProcessMutex> map = threadLockMap.get(thread);
        if (map != null){
            map.remove(path);
            if (map.size() == 0){
                threadLockMap.remove(thread);
            }
        }
    }
}
