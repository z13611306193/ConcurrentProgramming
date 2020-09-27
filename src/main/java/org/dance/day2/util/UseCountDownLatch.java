package org.dance.day2.util;

import org.dance.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的使用,有五个线程,6个扣除点
 * 扣除完成后主线程和业务线程,才能执行工作
 *  扣除点一般都是大于等于需要初始化的线程的
 * @author ZYGisComputer
 */
public class UseCountDownLatch {

    /**
     * 设置为6个扣除点
     */
    static CountDownLatch countDownLatch = new CountDownLatch(6);

    /**
     * 初始化线程
     */
    private static class InitThread implements Runnable {

        @Override
        public void run() {

            System.out.println("thread_" + Thread.currentThread().getId() + " ready init work .....");

            // 执行扣减 扣减不代表结束
            countDownLatch.countDown();

            for (int i = 0; i < 2; i++) {
                System.out.println("thread_" + Thread.currentThread().getId() + ".....continue do its work");
            }

        }
    }

    /**
     * 业务线程
     */
    private static class BusiThread implements Runnable {

        @Override
        public void run() {

            // 业务线程需要在等初始化完毕后才能执行
            try {
                countDownLatch.await();
                for (int i = 0; i < 3; i++) {
                    System.out.println("BusiThread " + Thread.currentThread().getId() + " do business-----");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // 创建单独的初始化线程
        new Thread(){
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("thread_" + Thread.currentThread().getId() + " ready init work step 1st.....");
                // 扣减一次
                countDownLatch.countDown();
                System.out.println("begin stop 2nd.....");
                SleepTools.ms(1);
                System.out.println("thread_" + Thread.currentThread().getId() + " ready init work step 2nd.....");
                // 扣减一次
                countDownLatch.countDown();

            }
        }.start();
        // 启动业务线程
        new Thread(new BusiThread()).start();
        // 启动初始化线程
        for (int i = 0; i <= 3; i++) {
            new Thread(new InitThread()).start();
        }
        // 主线程进入等待
        try {
            countDownLatch.await();
            System.out.println("Main do ites work.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
