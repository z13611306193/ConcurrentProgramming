package org.dance.day3;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用版本号解决ABA问题
 * @author ZYGisComputer
 */
public class UseAtomicStampedReference {

    /**
     * 构造参数地第一个是默认值,第二个就是版本号
     */
    static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("src",0);

    public static void main(String[] args) throws InterruptedException {

        // 获取初始版本号
        final int oldStamp = atomicStampedReference.getStamp();

        // 获取初始值
        final String oldValue = atomicStampedReference.getReference();

        System.out.println("oldValue:"+oldValue+" oldStamp:"+oldStamp);

        Thread success = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+",当前变量值:"+oldValue+"当前版本号:"+oldStamp);
                // 变更值和版本号
                /**
                 * 第一个参数:期望值
                 * 第二个参数:新值
                 * 第三个参数:期望版本号
                 * 第四个参数:新版本号
                 */
                boolean b = atomicStampedReference.compareAndSet(oldValue, oldValue + "java", oldStamp, oldStamp + 1);
                System.out.println(b);
            }
        });

        Thread error = new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取原值
                String sz = atomicStampedReference.getReference();
                int stamp = atomicStampedReference.getStamp();
                System.out.println(Thread.currentThread().getName()+",当前变量值:"+sz+"当前版本号:"+stamp);
                boolean b = atomicStampedReference.compareAndSet(oldValue+"java", oldValue + "C", oldStamp, oldStamp + 1);
                System.out.println(b);
            }
        });

        success.start();
        success.join();
        error.start();
        error.join();
        System.out.println(atomicStampedReference.getReference()+":"+atomicStampedReference.getStamp());
    }

}
