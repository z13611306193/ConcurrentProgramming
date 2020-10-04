package org.dance.day2.future;

import org.dance.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable的两种执行方式
 * @author ZYGisComputer
 */
public class UseCallable {

    /**
     * 实现Callable接口的线程
     */
    private static class UseCall implements Callable<Integer>{

        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("callable子线程开始执行任务计算");
            Thread.sleep(2000);
            for (int i = 0; i < 5000; i++) {
                sum += i;
            }
            System.out.println("子线程任务计算完成,返回值:"+sum);
            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        UseCall useCall = new UseCall();

        // 使用FutureTask包装
        FutureTask<Integer> futureTask = new FutureTask<>(useCall);

        // 包装为Thread
        Thread thread = new Thread(futureTask);

        thread.start();

        // 开始主线程的任务
        Random random = new Random();

        SleepTools.second(1);

        if(random.nextBoolean()){
            System.out.println("获取Callable result:"+futureTask.get());
        }else{
            System.out.println("中断计算");
            // 中断计算,取消线程的执行
            futureTask.cancel(true);
        }
    }

}
