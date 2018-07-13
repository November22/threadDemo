package com.thread.chapter7lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2018/7/13.
 */
public class ConditionTest2 {
    private static Lock lock = new ReentrantLock(true);


    /**
     * 获取进行等待的线程
     * @param i
     * @return
     */
    private static Condition waitByCondition(final int i,final Condition condition){
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    lock.lock();
                    System.out.println("线程"+i+"开始等待");
                    //await方法操作：线程被加入到 Condition的 的FIFO队列，等待signal，同时【释放锁，释放锁，释放锁】
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

    public static void main(String[] args) throws Exception {
        Condition condition = lock.newCondition();
        for(int i=0;i<10;i++){
            waitByCondition(i,condition);
        }
        lock.lock();
        //FIFO队列，先进先出,所以线程1会被唤醒
        condition.signal();
        condition.signal();
        condition.signal();
        //锁没有释放的时候，被唤醒的线程，还没有执行而是被加入到AQS的等待队列中，
        // 【因为lock还没有释放，lock释放后，等待的线程开始竞争】，看锁的竞争策略
        lock.unlock();
        System.exit(0);
    }
}
