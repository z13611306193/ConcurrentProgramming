package org.dance.day1.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池
 *
 * @author ZYGisComputer
 */
public class DBPool {

    /**
     * 连接池,采用LinkedList链表实现有序
     */
    private static final LinkedList<Connection> pool = new LinkedList<Connection>();

    /**
     * 初始化池大小
     * @param initialSize
     */
    public DBPool(int initialSize) {
        if (0 < initialSize) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(SqlConnection.fetchConnection());
            }
        }
    }

    /**
     * 获取数据库连接
     * @param mills 超时时间
     * @return connection 连接
     * @throws InterruptedException 中断异常
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        // 需要锁住数据库池
        synchronized (pool){
            // 判断超时时间
            if(0>mills){
                // 如果小于0 那么永不超时
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                // 获取过期时间
                long overtime = System.currentTimeMillis() + mills;
                // 获取等待时间
                long remain = mills;
                while (pool.isEmpty()&&remain>0){
                    pool.wait(remain);
                    remain = overtime - System.currentTimeMillis();
                }
                // 到这里的时候 要么是等待时间过期了 要么是连接池有连接了
                Connection connection = null;
                // 再次尝试去拿一下
                if(!pool.isEmpty()){
                    connection = pool.removeFirst();
                }
                return connection;
            }
        }
    }

    /**
     * 释放数据库连接
     * @param connection
     */
    public void releaseConnection(Connection connection){
        if(null!=connection){
            synchronized (pool){
                pool.addLast(connection);
                // 放回连接需要通知在线程池上等待的线程
                pool.notifyAll();

            }
        }
    }

}
