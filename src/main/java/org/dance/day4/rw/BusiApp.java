package org.dance.day4.rw;


import org.dance.tools.SleepTools;

import java.util.Random;

/**
 * 类说明：对商品进行业务的应用
 */
public class BusiApp {
    /**
     * 读写线程的比例
     */
    static final int readWriteRatio = 10;
    /**
     * 最少线程数
     */
    static final int minthreadCount = 3;

    /**
     * 读操作
     */
    private static class GetThread implements Runnable{

        private GoodsService goodsService;

        /**
         * 传入商品
         * @param goodsService
         */
        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            // 读取100次数量
            for(int i=0;i<100;i++){//操作100次
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName()+"读取商品数据耗时："
             +(System.currentTimeMillis()-start)+"ms");

        }
    }

    /**
     * 写操做
     */
    private static class SetThread implements Runnable{

        private GoodsService goodsService;
        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();
            //操作10次
            for(int i=0;i<10;i++){
            	SleepTools.ms(50);
                goodsService.setNum(r.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName()
            		+"写商品数据耗时："+(System.currentTimeMillis()-start)+"ms---------");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsInfo goodsInfo = new GoodsInfo("Cup",100000,10000);
        // 使用Sync锁
//        GoodsService goodsService = new UseSyn(goodsInfo);
        // 使用读写锁
        GoodsService goodsService = new UseRwLock(goodsInfo);
        // 启动三个写线程
        for(int i = 0;i<minthreadCount;i++){
            Thread setT = new Thread(new SetThread(goodsService));
            // 每启动一个写线程,就启动10个读线程
            for(int j=0;j<readWriteRatio;j++) {
                Thread getT = new Thread(new GetThread(goodsService));
                getT.start();
            }
            SleepTools.ms(100);
            setT.start();
        }
    }
}
