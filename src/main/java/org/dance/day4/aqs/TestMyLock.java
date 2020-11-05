package org.dance.day4.aqs;


import org.dance.tools.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *类说明：测试手写锁
 */
public class TestMyLock {
    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();
    }
    public void test() {
        // 先使用ReentrantLock 然后替换为我们自己的Lock
//        final Lock lock = new ReentrantLock();
        final Lock lock = new CustomerLock();

        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                    	SleepTools.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepTools.second(1);
                    } finally {
                        lock.unlock();
                    }
                    SleepTools.second(2);
                }
            }
        }
        // 启动10个子线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
        	SleepTools.second(1);
            System.out.println();
        }
    }
}
