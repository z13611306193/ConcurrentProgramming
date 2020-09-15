package org.dance.day1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 新启动线程的三种方式
 * @author ZYGisComputer
 */
public class NewThread {

    /**
     * 继承Thread类
     */
    private static class UseThread extends Thread{
        @Override
        public void run() {
            System.out.println("继承自Thread类!");
            super.run();
        }
    }

    /**
     * 实现Runnable接口
     */
    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("实现自Runnable接口!");
        }
    }

    /**
     * 实现Callable接口
     *  可以有返回值
     */
    private static class UseCallable implements Callable<Boolean>{
        @Override
        public Boolean call() throws Exception {
            System.out.println("实现自Callable接口!");
            return true;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 启动线程一
        UseThread useThread = new UseThread();
        useThread.start();
        // 启动线程二
        UseRunnable useRunnable = new UseRunnable();
        Thread thread = new Thread(useRunnable);
        thread.start();
        // 启动线程三
        UseCallable useCallable = new UseCallable();
        FutureTask<Boolean> futureTask = new FutureTask<Boolean>(useCallable);
        Thread thread1 = new Thread(futureTask);
        thread1.start();
        // 获取Callable的返回值 get方法是阻塞的
        Boolean aBoolean = futureTask.get();
        System.out.println(aBoolean);
    }

}
