package org.dance.day1.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试数据库连接池
 * @author ZYGisComputer
 */
public class DBPoolTest {

    /**
     * 初始化连接池
     */
    static DBPool dbPool = new DBPool(10);

    static CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException {

        // 线程数量
        int threadCount = 50;

        countDownLatch = new CountDownLatch(threadCount);

        // 每个线程操作的次数
        int count = 20;

        // 计数器:统计可以拿到链接的线程的数量
        AtomicInteger get = new AtomicInteger();

        // 计数器:统计没有拿到链接的线程的数量
        AtomicInteger noGet = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new Worker(count,get,noGet),"worker_"+i);
            thread.start();
        }

        countDownLatch.await();

        System.out.println("总共尝试了:"+(threadCount * count));

        System.out.println("拿到链接的次数:"+get.get());

        System.out.println("没有拿到链接的次数:"+noGet.get());


    }

    static class Worker implements Runnable{

        int count;
        AtomicInteger get;
        AtomicInteger noGet;

        public Worker(int count, AtomicInteger get, AtomicInteger noGet) {
            this.count = count;
            this.get = get;
            this.noGet = noGet;
        }

        @Override
        public void run() {
            while (count>0){
                try {
                    // 从数据库连接池开始拿链接,如果1秒内没有拿到链接将会返回null
                    // 然后统计拿到和没拿到的数量
                    Connection connection = dbPool.fetchConnection(1000);
                    if(null!=connection){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            dbPool.releaseConnection(connection);
                            get.incrementAndGet();
                            System.out.println(Thread.currentThread().getName()+":拿到了线程");
                        }
                    }else{
                        noGet.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            countDownLatch.countDown();
        }

    }

}
