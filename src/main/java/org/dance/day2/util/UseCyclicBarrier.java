package org.dance.day2.util;

import org.dance.tools.SleepTools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的使用
 *
 * @author ZYGisComputer
 */
public class UseCyclicBarrier {

    /**
     * 存放子线程工作结果的安全容器
     */
    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,new CollectThread());

    /**
     * 结果打印线程
     * 用来演示CyclicBarrier的第二个参数,barrierAction
     */
    private static class CollectThread implements Runnable {

        @Override
        public void run() {

            StringBuffer result = new StringBuffer();

            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }

            System.out.println("the result = " + result);
            System.out.println("do other business.....");

        }
    }

    /**
     * 工作子线程
     * 用于CyclicBarrier的一组线程
     */
    private static class SubThread implements Runnable {

        @Override
        public void run() {

            // 获取当前线程的ID
            long id = Thread.currentThread().getId();

            // 放入统计容器中
            resultMap.put(String.valueOf(id), id);

            Random random = new Random();

            try {
                if (random.nextBoolean()) {
                    Thread.sleep(1000 + id);
                    System.out.println("Thread_"+id+"..... do something");
                }
                System.out.println(id+" is await");
                cyclicBarrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+".....do its business");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {

        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }

    }

}
