package org.dance.day5.bq;

import java.util.Queue;
import java.util.concurrent.DelayQueue;

/**
 * @author ZYGisComputer
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        DelayQueue<ItemVo<Order>> queue = new DelayQueue<>();
        new Thread(new PutOrder(queue)).start();
        new Thread(new FetchOrder(queue)).start();
        for (int i = 0; i < 15; i++) {
            Thread.sleep(500);
            System.out.println(i*500);
        }

    }

}
