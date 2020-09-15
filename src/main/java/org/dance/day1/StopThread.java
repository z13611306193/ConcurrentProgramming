package org.dance.day1;

/**
 * 停止线程
 *
 * @author ZYGisComputer
 */
public class StopThread {

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
            // 如果这里是true的话 那么这个子线程是可以完全不理会主线程发出的中断请求的 但是如果是调用stop方法的话那么会直接停止
            // 所以说java是协作式的不是抢占式的
            while (!isInterrupted()) {
                System.out.println("当前线程:" + name);
            }
            System.out.println(name + " interrupt flag is " +isInterrupted());
        }
    }

    /**
     * 如果是实现自Runnable接口,那么就不能像继承Thread类一样中断了
     */
    private static class UseRunnable implements Runnable{
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            // 需要获取线程然后中断
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("当前线程:" + name);
            }
            System.out.println(name + " interrupt flag is "+ Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        stopThread();
        stopRunnable();


    }

    /**
     * 继承Thread类中断
     * @throws InterruptedException
     */
    public static void stopThread() throws InterruptedException {
        UseThread stopThread = new UseThread("StopThread");
        stopThread.start();
        Thread.sleep(20);
        // 尝试去中断线程
        stopThread.interrupt();
    }

    /**
     * 实现Runnable接口中断
     * @throws InterruptedException
     */
    public static void stopRunnable() throws InterruptedException {
        UseRunnable useRunnable = new UseRunnable();
        Thread stopThread = new Thread(useRunnable, "StopThread");
        stopThread.start();
        Thread.sleep(20);
        stopThread.interrupt();
    }

    /**
     * Callable 中断同 Runnable 中断方式一致
     */

}
