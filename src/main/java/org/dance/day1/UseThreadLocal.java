package org.dance.day1;

import org.dance.tools.SleepTools;

/**
 * ThreadLocal 的使用
 * @author ZYGisComputer
 */
public class UseThreadLocal {

    /**
     * 声明ThreadLocal并设置初始值
     */
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    /**
     * 启动线程组
     */
    public void startThreadArray(){
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestThread(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    /**
     * 测试线程:线程的工作是将ThreadLocal变量的值变化,并写回,看看线程之间是否会互相影响
     */
    public static class TestThread implements Runnable{

        int id;

        public TestThread(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" start!");
            SleepTools.ms(100);
            // 从ThreadLocal中获取值
            Integer integer = threadLocal.get();
            integer = integer+id;
            threadLocal.set(integer);
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThreadArray();
    }
}
