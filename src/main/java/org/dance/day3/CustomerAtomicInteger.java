package org.dance.day3;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomerAtomicInteger {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public int getAndIncrement(){
        for (;;){
            int i = atomicInteger.get();
            atomicInteger.compareAndSet(i,i+1);
            return i;
        }
    }

}
