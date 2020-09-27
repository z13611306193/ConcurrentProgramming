package org.dance.day2.util.pool;

import org.dance.tools.SleepTools;

import java.sql.Connection;
import java.util.Random;

/**
 * 测试Semaphore
 * @author ZYGisComputer
 */
public class UseSemaphore {

    /**
     * 连接池
     */
    public static final DBPoolSemaphore pool = new DBPoolSemaphore();

    private static class BusiThread extends Thread{
        @Override
        public void run() {
            // 随机数工具类 为了让每个线程持有连接的时间不一样
            Random random = new Random();
            long start = System.currentTimeMillis();
            try {
                Connection connection = pool.takeConnection();
                System.out.println("Thread_"+Thread.currentThread().getId()+
                        "_获取数据库连接耗时["+(System.currentTimeMillis()-start)+"]ms.");
                // 模拟使用连接查询数据
                SleepTools.ms(100+random.nextInt(100));
                System.out.println("查询数据完成归还连接");
                pool.returnConnection(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            BusiThread busiThread = new BusiThread();
            busiThread.start();
        }
    }

}
