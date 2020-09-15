package org.dance.day1;

/**
 * 异常中断
 * @author ZYGisComputer
 */
public class HasInterruptException {

    /**
     * 继承Thread类的中断
     */
    private static class UseThread extends Thread {

        public UseThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            // 获取当前线程的名称
            String name = Thread.currentThread().getName();
            while (!isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(name + " interrupt flag is " +isInterrupted());
                    // 含有中断异常的 需要在中断异常中再次中断 否则因为中断异常 线程中断标志位会被重新置为false
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(name);
            }
            System.out.println(name + " interrupt flag is " +isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread("HasInterruptException");
        useThread.start();
        Thread.sleep(500);
        useThread.interrupt();
    }
}
