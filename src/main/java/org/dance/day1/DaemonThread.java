package org.dance.day1;

import org.dance.tools.SleepTools;

/**
 * 守护线程
 * @author ZYGisComputer
 */
public class DaemonThread {

    /**
     * 继承Thread类
     */
    private static class UseThread extends Thread {

        public UseThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                while (!isInterrupted()) {
                    System.out.println("当前线程:" + name);
                }
                System.out.println(name + " interrupt flag is " + isInterrupted());
            } finally {
                System.out.println("执行 finally");
            }
        }
    }

    public static void main(String[] args) {
        // 执行不是守护线程
//        noDaemon();
        // 执行守护线程
        daemon();
        // 对比之下就可以看到
        // 不是守护线程 需要中断 主线程执行完毕之后不会停止 finally语句块一定会执行
        // 守护线程 主线程执行完毕立即停止 finally语句块不一定会执行
    }

    public static void noDaemon(){
        UseThread useThread = new UseThread("DaemonThread");
        useThread.start();
        Thread.yield();
        SleepTools.ms(5);
        useThread.interrupt();
    }

    public static void daemon(){
        UseThread useThread = new UseThread("DaemonThread");
        useThread.setDaemon(true);
        useThread.start();
        SleepTools.ms(5);
    }

}
