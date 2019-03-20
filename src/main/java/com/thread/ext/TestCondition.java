package com.thread.ext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2019/3/10.
 */
public class TestCondition implements Runnable {

    private Condition condition;

    private Lock lock ;

    public TestCondition(Condition condition,Lock lock){
        this.condition = condition;
        this.lock = lock;
    }

    public void run() {
        try {
            lock.lockInterruptibly();
            System.out.println("线程"+Thread.currentThread().getName()+"开始睡眠，等待被唤醒");
            //等待期间会释放锁
            condition.await(300,TimeUnit.MILLISECONDS);
            System.out.println("线程"+Thread.currentThread().getName()+"睡眠");
            Thread.sleep(3000L);
            System.out.println("线程"+Thread.currentThread().getName()+"被唤醒");

        } catch (InterruptedException e) {
            System.out.println("线程:"+Thread.currentThread().getName()+",被中断！");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Thread t1 = new Thread(new TestCondition(condition1,lock),"condition1");

        Condition condition2 = lock.newCondition();
        Thread t2 = new Thread(new TestCondition(condition1,lock),"condition2");

        t1.start();
        t2.start();
        System.out.println("线程启动");
        Thread.sleep(3000L);
        try {
            lock.lockInterruptibly();
            //唤醒操作，也只能持有锁的时候操作。
            //只唤醒一条等待的线程，如果存在多条，则唤醒先进入休眠的线程
//            condition1.signal();
            //唤醒所有的使用condition1进入休眠的线程。
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
