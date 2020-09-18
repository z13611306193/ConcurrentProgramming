package org.dance.day2.forkjoin.sum;

import org.dance.tools.SleepTools;

/**
 * 单线程实现求和
 * @author ZYGisComputer
 */
public class SumNormal {

    public static void main(String[] args) {
        int count = 0;

        // 获取数组
        int[] src = MarkArray.markArray();

        long l = System.currentTimeMillis();

        for (int i = 0; i < src.length; i++) {
            // 执行一毫秒的休眠
//            SleepTools.ms(1);
            count += src[i];
        }

        System.out.println("The count is "+count+" spend time "+(System.currentTimeMillis() - l));
    }

}
