package org.dance.day2.future;

import org.dance.tools.SleepTools;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Callable的两种执行方式
 * @author ZYGisComputer
 */
public class UseCallablePool {

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

        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();


        Future<Integer> future = executorService.submit(useCall);

        // 开始主线程的任务
        Random random = new Random();

        SleepTools.second(1);

        if(random.nextBoolean()){
            System.out.println("获取Callable result:"+future.get());
        }else{
            System.out.println("中断计算");
            // 中断计算,取消线程的执行
            future.cancel(true);
        }
    }

}
