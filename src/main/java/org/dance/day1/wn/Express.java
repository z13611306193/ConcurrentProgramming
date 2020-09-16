package org.dance.day1.wn;

/**
 * 快递实体类
 *
 * @author ZYGisComputer
 */
public class Express {

    public final static String CITY = "ShangHai";

    /**
     * 快递运输的里程数
     */
    private int km;

    /**
     * 快递到达的地点
     */
    private String site;

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 变化公里数:然后通知处于wait状态并需要处理公里数的线程进行业务处理
     */
    public synchronized void checkKm() {
//      变化公里数
        this.km = 101;
//      全部通知
//        notifyAll();
        notify();
    }

    /**
     * 变化地点:然后通知处于wait状态并需要处理地点的线程进行业务处理
     */
    public synchronized void checkSite() {
//        变化城市
        this.site = "BeiJin";
//         全部通知
//        notifyAll();
        notify();
    }

    public synchronized void waitKm() {
        // 循环等待
        while (this.km <= 100) {
            try {
                wait();
                System.out.println("check km " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the km is " + this.km + ", I will change DB.");
    }

    public synchronized void waitSite() {
        // 循环等待
        while (CITY.equals(this.site)) {
            try {
                wait();
                System.out.println("check site " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is " + this.site + ", I will change DB.");
    }
}
