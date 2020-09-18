package org.dance.day2.forkjoin.sum;

import java.util.Random;

/**
 * 数组制作类
 * @author ZYGisComputer
 */
public class MarkArray {

    public static final int ARRAY_LENGTH = 300000000;

    /**
     * int数组生成器
     * @return int数组
     */
    public static int[] markArray(){

        Random random = new Random();

        int[] array = new int[ARRAY_LENGTH];

        for (int i = 0; i < ARRAY_LENGTH; i++) {
            array[i] = random.nextInt(ARRAY_LENGTH*3);
        }

        return array;

    }

}
