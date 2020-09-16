package org.dance.day1.wn;

import org.dance.tools.SleepTools;

/**
 * 测试wait notify/notifyAll
 * @author ZYGisComputer
 */
public class TestWN {

    private static Express express = new Express(0,Express.CITY);

    /**
     * 检查里程数变化的线程,不满足一直等待
     */
    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    /**
     * 检查城市变化的线程,不满足一直等待
     */
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new CheckKm().start();
        }
        for (int i = 0; i < 3; i++) {
            new CheckSite().start();
        }
        SleepTools.second(1);
        // 修改里程数
        express.checkKm();
    }
}
