package com.thread.chapter7lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2019/3/26.
 */
public class TestReentrantLock implements Runnable {

    private ReentrantLock lock ;

    private int index;

    private AtomicInteger integer = new AtomicInteger(0);

    public TestReentrantLock(ReentrantLock lock,int index){
        this.lock = lock;
        this.index = index;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"准别acqire锁"+index);
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"开始睡眠，不会释放锁");
            if(integer.get() == 0){
                integer.incrementAndGet();
                Thread.sleep(10*1000L);
            }else {
                Thread.sleep(30*60*1000L);
            }

            System.out.println("睡眠结束...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {


        ReentrantLock lock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int index=0;index<3;index++){
            executorService.execute(new TestReentrantLock(lock,index));
        }


        executorService.shutdown();
        executorService.awaitTermination(1,TimeUnit.HOURS);
    }
}
