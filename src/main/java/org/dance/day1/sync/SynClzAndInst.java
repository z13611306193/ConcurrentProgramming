package org.dance.day1.sync;

import org.dance.tools.SleepTools;

/**
 * 对象锁和类锁
 * @author ZYGisComputer
 */
public class SynClzAndInst {

    /**
     * 使用类锁的线程
     */
    private static class SynClass extends Thread{
        @Override
        public void run() {
            System.out.println("TestClass is running...");
            synClass();
        }
    }

    /**
     * 使用对象锁的线程
     */
    private static class InstanceSyn implements Runnable{
        private SynClzAndInst synClzAndInst;

        public InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance is running..."+synClzAndInst);
            synClzAndInst.instance();
        }
    }

    /**
     * 使用对象锁的线程
     */
    private static class Instance2Syn implements Runnable{
        private SynClzAndInst synClzAndInst;

        public Instance2Syn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }
        @Override
        public void run() {
            System.out.println("TestInstance2 is running..."+synClzAndInst);
            synClzAndInst.instance2();
        }
    }

    /**
     * 锁对象
     */
    private synchronized void instance(){
        SleepTools.second(3);
        System.out.println("synInstance is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance ended "+this.toString());
    }

    /**
     * 锁对象
     */
    private synchronized void instance2(){
        SleepTools.second(3);
        System.out.println("synInstance2 is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance2 ended "+this.toString());
    }

    /**
     * 类锁，实际是锁类的class对象
     */
    private static synchronized void synClass(){
        SleepTools.second(1);
        System.out.println("synClass going...");
        SleepTools.second(1);
        System.out.println("synClass end");
    }

    public static void main(String[] args) {
        // 测试锁两个对象
//        testTwoObject();
    	// 测试锁一个对象
//        testOneObject();
        // 测试类锁
        testClass();
        SleepTools.second(1);
    }

    /**
     * 对象锁
     * 因为是锁的两个对象 所以可以同时运行
     */
    public static void testTwoObject(){
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
    	Thread t2 = new Thread(new Instance2Syn(synClzAndInst2));
        t1.start();
        t2.start();
    }

    /**
     * 对象锁
     * 因为是锁的同一个对象 所以不可以同时运行
     */
    public static void testOneObject(){
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
    	Thread t2 = new Thread(new Instance2Syn(synClzAndInst));
        t1.start();
        t2.start();
    }
    /**
     * 类锁
     * 类锁锁的是虚拟机内存中的唯一一份的class镜像
     */
    public static void testClass(){
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
        t1.start();
    	SynClass synClass = new SynClass();
    	synClass.start();
    }


}
