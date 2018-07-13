package com.thread.chapter7lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2018/7/13.
 * 调用Condition的await()和signal()方法，都必须在lock保护之内，就是说必须在lock.lock()和lock.unlock之间才可以使用
 */
public class ConditionTest1 {
    private static Lock lock = new ReentrantLock();

    /**
     * 获取进行等待的线程
     * @param i
     * @return
     */
    private static Condition waitByCondition(final int i){

        final Condition condition = lock.newCondition();
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    lock.lock();
                    System.out.println("线程"+i+"开始等待");
                    condition.await();
                    System.out.println("线程"+i+"被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        new Thread(runnable).start();
        return condition;
    }

    //FIFO队列，先进先出
    public static void main(String[] args) throws Exception {
        List<Condition> conditions = new ArrayList<Condition>();
        for(int i=0;i<10;i++){
            Condition condition = waitByCondition(i);
            conditions.add(i,condition);
        }
        System.out.println("主线程休息10秒");
        Thread.sleep(10000L);
        lock.lock();
        //唤醒方法在执行的时候需要持有锁，即在lock.lock(); 和 lock.unlock();之间执行
        //否则 IllegalMonitorStateException
        conditions.get(0).signal();
        lock.unlock();
    }
}
