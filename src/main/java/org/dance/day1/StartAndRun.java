package org.dance.day1;

import org.dance.tools.SleepTools;

/**
 * 线程调用 run 和 start 方法的区别
 * @author ZYGisComputer
 */
public class StartAndRun {

    /**
     * 继承Thread类
     */
    private static class ThreadRun extends Thread{
        @Override
        public void run() {
            int i = 90;
            while (i>0){
                SleepTools.ms(1000);
                System.out.println("I am "+Thread.currentThread().getName()+" and now the i = "+ i);
                i--;
            }
        }
    }

    public static void main(String[] args) {

        // 执行run
//        executeRun();
        // 执行start
        executeStart();

    }

    public static void executeRun(){
        ThreadRun thread = new ThreadRun();
        thread.setName("run");
        // 调用线程的Run方法
        thread.run();
        // 执行结果
        // I am main and now the i = 90
    }

    public static void executeStart(){
        ThreadRun thread = new ThreadRun();
        thread.setName("run");
        // 调用线程的Run方法
        thread.start();
        // 执行结果
        // I am run and now the i = 90
    }

}
