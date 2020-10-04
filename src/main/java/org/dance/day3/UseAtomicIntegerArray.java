package org.dance.day3;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 使用原子类int[]
 * @author ZYGisComputer
 */
public class UseAtomicIntegerArray {

    static int[] values = new int[]{1,2};

    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(values);

    public static void main(String[] args) {
        //改变的第一个参数是 数组的下标,第二个是新值
        atomicIntegerArray.getAndSet(0,3);
        // 获取原子数组类中的下标为0的值
        System.out.println(atomicIntegerArray.get(0));
        // 获取源数组中下标为0的值
        System.out.println(values[0]);
    }

}
