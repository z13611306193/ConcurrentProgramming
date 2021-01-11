package org.dance.day5.bq;

import java.util.concurrent.DelayQueue;

/**
 * 将订单放入队列
 * @author ZYGisComputer
 */
public class PutOrder implements Runnable{

    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 5秒到期
        Order order = new Order("1",16.00d);
        // 包装成ItemVo
        ItemVo<Order> itemVo = new ItemVo<>(5000,order);
        queue.offer(itemVo);
        System.out.println("订单5秒后到期:"+order.getOrderNo());

        // 5秒到期
        order = new Order("2",18.00d);
        // 包装成ItemVo
        itemVo = new ItemVo<>(8000,order);
        queue.offer(itemVo);
        System.out.println("订单8秒后到期:"+order.getOrderNo());
    }
}
