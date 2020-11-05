package org.dance.day4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 采用主类实现Lock接口,内部类继承AQS,封装细节
 * 自定义锁
 * @author ZYGisComputer
 */
public class CustomerLock implements Lock {

    private final Sync sync = new Sync();

    /**
     * 采用内部类来继承AQS,封装细节
     *  实现独占锁,通过控制state状态开表示锁的状态
     *      state:1 代表锁已被占用
     *      state:0 代表锁可以被占用
     */
    private static class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                // 当前线程获取到锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 如果状态为没人占用,还去释放,就报错
            if(getState()==0){
                throw new UnsupportedOperationException();
            }
            // 把锁的占用者制空
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 判断线程是否占用资源
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        /**
         * 获取Condition接口
         * @return
         */
        public Condition getCondition(){
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.getCondition();
    }
}
