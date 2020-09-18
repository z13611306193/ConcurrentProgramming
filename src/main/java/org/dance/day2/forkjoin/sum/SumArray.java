package org.dance.day2.forkjoin.sum;

import org.dance.tools.SleepTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoin框架实现求和
 * @author ZYGisComputer
 */
public class SumArray {

    /**
     * 因为需要返回值所以继承RecursiveTask类
     *  因为计算的是整型,所以泛型是Integer
     */
    private static class SumTask extends RecursiveTask<Integer> {

        // 计算阈值
        private final static int THRESHOLD = MarkArray.ARRAY_LENGTH/10;

        // 源数组
        private int[] src;

        // 开始坐标
        private int fromIndex;

        // 结束坐标
        private int toIndex;

        /**
         * 通过创建时传入
         * @param src 元素组
         * @param fromIndex 开始坐标
         * @param toIndex 结束坐标
         */
        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        /**
         * 覆盖执行方法
         * @return 整型
         */
        @Override
        protected Integer compute() {
            // 如果 结束下标减去开始下标小于阈值的时候,那么任务就可以开始执行了
            if( toIndex - fromIndex < THRESHOLD ){
                int count = 0;
                // 从开始下标开始循环,循环到结束下标
                for (int i = fromIndex; i < toIndex; i++) {
                    // 休眠1毫秒
//                    SleepTools.ms(1);
                    count += src[i];
                }
                return count;
            }else{
                // 大于阈值 继续拆分任务
                // 从formIndex---------------------->到toIndex
                // 计算中间值,从formIndex----------计算mid------------>到toIndex
                int mid = (fromIndex + toIndex) / 2;
                // 左侧任务 从formIndex------------>到mid结束
                SumTask left = new SumTask(src, fromIndex, mid);
                // 右侧任务 从mid+1开始------------->到toIndex结束
                SumTask right = new SumTask(src, mid+1,toIndex);
                // 调用任务
                invokeAll(left,right);
                // 获取结果
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {

        // 创建ForkJoin任务池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 制作源数组
        int[] src = MarkArray.markArray();

        long l = System.currentTimeMillis();

        // 创建一个任务 下标因为从0开始所以结束下标需要-1
        SumTask sumTask = new SumTask(src, 0, src.length - 1);

        // 提交同步任务
        Integer invoke = forkJoinPool.invoke(sumTask);

        // 无论是接收invoke方法的返回值还是调用任务的Join方法都可以获取到结果值
        System.out.println("The count is "+invoke+" spend time "+(System.currentTimeMillis() - l));
        System.out.println("The count is "+sumTask.join()+" spend time "+(System.currentTimeMillis() - l));

    }

}
