package org.dance.day4.rw;


import org.dance.tools.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类说明：使用读写锁 实现读写分离
 */
public class UseRwLock implements GoodsService {

    private GoodsInfo goodsInfo;

    /**
     * 创建读写锁,默认使用非公平锁
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 获取读锁
     */
    private final Lock readLock = lock.readLock();

    /**
     * 获取写锁
     */
    private final Lock writeLock = lock.writeLock();


    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        // 添加读锁
        readLock.lock();
        try {
            SleepTools.ms(5);
            return this.goodsInfo;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setNum(int number) {
        // 添加写锁
        writeLock.lock();
        try {
            SleepTools.ms(5);
            goodsInfo.changeNumber(number);
        } finally {
            writeLock.unlock();
        }
    }

}
