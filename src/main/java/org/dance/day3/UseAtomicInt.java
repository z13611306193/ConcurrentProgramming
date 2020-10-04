package org.dance.day3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用原子类int类型
 * @author ZYGisComputer
 */
public class UseAtomicInt {

    static AtomicInteger atomicInteger = new AtomicInteger(10);

    public static void main(String[] args) {
        // 10->11 10先去再增加
        System.out.println(atomicInteger.getAndIncrement());
        // 11->12 12先增加再取
        System.out.println(atomicInteger.incrementAndGet());
        // 获取
        System.out.println(atomicInteger.get());
    }

}
