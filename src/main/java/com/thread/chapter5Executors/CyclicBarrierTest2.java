package com.thread.chapter5Executors;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 *         Date: 2018/7/9
 */
public class CyclicBarrierTest2 {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        public void run() {
            System.out.println("同步屏障上的线程执行完成！！");
        }
    });

    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500L);
                    cyclicBarrier.await();
                    System.out.println("r1等待完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(15000L);
                    cyclicBarrier.await();
                    System.out.println("r2等待完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(r1);
        service.execute(r2);
        service.shutdown();
        System.out.println("线程都已提交，等待线程执行完成");
        try {
            service.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("等待结束");
    }
}
