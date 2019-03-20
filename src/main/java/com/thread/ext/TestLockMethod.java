package com.thread.ext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2019/3/10.
 */
public class TestLockMethod {

   private static volatile boolean interrupted = false;

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock(true);

        lock.lock();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
//                lock.lock();
                try {
                    lock.lockInterruptibly();
                    System.out.println("获得锁成功");
                } catch (InterruptedException e) {
                    System.out.println("锁被中断，不再尝试获取锁");
                }
            }
        });
        t1.start();
        /**
         * 1.Runnable实现中，使用`lock.lock();`方法尝试获取锁，然后将线程t1中断，中断线程操作没有效果,
         * 因为在main线程释放锁后，run方法还是获得了锁。而中断没有生效的话，那么`lock.lock();`还是会持续的去尝试获取锁。
         * PS：只有`lock.lock();`获得锁之后才能被中断。
         *
         * 2.使用`lock.lockInterruptibly();`那么在调用线程中断后，就不会再尝试尝试去获取锁，线程中断，资源是方法。
         * 同比两者。建议使用`lockInterruptibly()`方法，自定义对线程中断异常的处理，而不是线程不能被中断。
         */

        t1.interrupt();
        Thread.sleep(3000L);
        lock.unlock();
        System.out.println("释放锁");
        Thread.sleep(3000L);
    }
}
