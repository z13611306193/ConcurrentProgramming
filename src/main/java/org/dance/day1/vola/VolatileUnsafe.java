package org.dance.day1.vola;

import org.dance.tools.SleepTools;

/**
 * volatile无法保证操作的原子性
 * @author ZYGisComputer
 */
public class VolatileUnsafe {

    private static class VolatileVar implements Runnable{

        private volatile int a = 0;

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            a = a + 1;
            System.out.println(name + ":" + a);
            SleepTools.ms(100);
            a = a + 1;
            System.out.println(name + ":" + a);
        }
    }

    public static void main(String[] args) {
        VolatileVar volatileVar = new VolatileVar();
        Thread thread = new Thread(volatileVar);
        Thread thread1 = new Thread(volatileVar);
        Thread thread2 = new Thread(volatileVar);
        Thread thread3 = new Thread(volatileVar);
        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
