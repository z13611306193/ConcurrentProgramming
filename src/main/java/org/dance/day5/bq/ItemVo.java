package org.dance.day5.bq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 存放到队列的元素
 * @author ZYGisComputer
 */
public class ItemVo<T> implements Delayed {

    /**
     * 延时时间 单位为毫秒
     */
    private long activeTime;

    private T data;

    public ItemVo(long activeTime, T data) {
        // 将时间转换为纳秒 并 + 当前纳秒 = 将超时时长转换为超时时刻
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime,TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = data;
    }

    /**
     * 返回元素的剩余时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * 按照剩余时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d==0)?0:(d>0)?1:-1;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }
}
