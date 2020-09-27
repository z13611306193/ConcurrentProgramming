package org.dance.day2.util.pool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量控制数据库的链接和释放
 *
 * @author ZYGisComputer
 */
public class DBPoolSemaphore {

    /**
     * 池容量
     */
    private final static int POOL_SIZE = 10;

    /**
     * useful 代表可用连接
     * useless 代表已用连接
     *  为什么要使用两个Semaphore呢?是因为,在连接池中不只有连接本身是资源,空位也是资源,也需要记录
     */
    private final Semaphore useful, useless;

    /**
     * 连接池
     */
    private final static LinkedList<Connection> POOL = new LinkedList<>();

    /**
     * 使用静态块初始化池
     */
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            POOL.addLast(SqlConnection.fetchConnection());
        }
    }

    public DBPoolSemaphore() {
        // 初始可用的许可证等于池容量
        useful = new Semaphore(POOL_SIZE);
        // 初始不可用的许可证容量为0
        useless = new Semaphore(0);
    }

    /**
     * 获取数据库连接
     *
     * @return 连接对象
     */
    public Connection takeConnection() throws InterruptedException {
        // 可用许可证减一
        useful.acquire();
        Connection connection;
        synchronized (POOL) {
            connection = POOL.removeFirst();
        }
        // 不可用许可证数量加一
        useless.release();
        return connection;
    }

    /**
     * 释放链接
     *
     * @param connection 连接对象
     */
    public void returnConnection(Connection connection) throws InterruptedException {
        if(null!=connection){
            // 打印日志
            System.out.println("当前有"+useful.getQueueLength()+"个线程等待获取连接,,"
                    +"可用连接有"+useful.availablePermits()+"个");
            // 不可用许可证减一
            useless.acquire();
            synchronized (POOL){
                POOL.addLast(connection);
            }
            // 可用许可证加一
            useful.release();
        }
    }

}
