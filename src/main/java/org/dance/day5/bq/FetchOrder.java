package org.dance.day5.bq;

import java.util.concurrent.DelayQueue;

/**
 * 获取订单
 *
 * @author ZYGisComputer
 */
public class FetchOrder implements Runnable {

    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            ItemVo<Order> poll = null;
            try {
                poll = queue.take();
                Order data = poll.getData();
                String orderNo = data.getOrderNo();
                System.out.println(orderNo + "已经消费");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
