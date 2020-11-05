package org.dance.day4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类说明：使用Condition接口实现等待通知模式
 */
public class ExpressCond {

    public final static String CITY = "ShangHai";

    /**
     * 快递运输里程数
     */
    private int km;

    /**
     * 快递到达地点
     */
    private String site;

    /**
     * 创建显示锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 检测城市变化
     */
    private final Condition siteCond = lock.newCondition();

    /**
     * 检测公里数变化
     */
    private final Condition kmCond = lock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public void changeKm() {
        // 获取锁
        lock.lock();
        try {
            this.km = 101;
            // 唤醒在kmCond 上 等待的线程
            kmCond.signal();
        } finally {
            lock.unlock();
        }
    }

    /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public void changeSite() {
        // 获取锁
        lock.lock();
        try {
            this.site = "BeiJing";
            // 唤醒在siteCond 上 等待的线程
            siteCond.signal();
        } finally {
            lock.unlock();
        }
    }

    /*当快递的里程数大于100时更新数据库*/
    public void waitKm() {
        lock.lock();
        try {
            while (this.km <= 100) {
                try {
                    kmCond.await();
                } catch (InterruptedException e) {
                    // 处理线程中断
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("check km thread[" + Thread.currentThread().getId()
                        + "] is be notifed.");
            }
        } finally {
            lock.unlock();
        }
        System.out.println("the Km is " + this.km + ",I will change db");
    }

    /*当快递到达目的地时通知用户*/
    public void waitSite() {
        lock.lock();
        try {
            while (CITY.equals(this.site)) {
                try {
                    siteCond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("check site thread[" + Thread.currentThread().getId()
                        + "] is be notifed.");
            }
        } finally {
            lock.unlock();
        }
        System.out.println("the site is " + this.site + ",I will call user");
    }
}
