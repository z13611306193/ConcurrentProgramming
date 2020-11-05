package org.dance.day4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lockDemo
 * @author ZYGisComputer
 */
public class LockDemo {

    private Lock lock = new ReentrantLock();

    private int count;

    /**
     * 累加操作
     */
    public void increment(){
        // 加锁
        lock.lock();
        try {
            count++;
        }finally {
            // 释放锁
            lock.unlock();
        }
    }

    public synchronized void incrementSync(){
        count++;
        // 可重入锁演示
        incrementSync();
    }

}
