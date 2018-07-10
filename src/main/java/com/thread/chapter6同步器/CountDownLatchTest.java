package com.thread.chapter6同步器;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sen.huang
 *         Date: 2018/7/9
 *         倒计时门闩同步器
 *   应用场景【假如有这样一个需求，当我们需要解析一个Excel里多个sheet的数据时，可以考虑使用多线程，
 *每个线程解析一个sheet里的数据，等到所有的sheet都解析完之后，程序需要提示解析完成。

 *在这个需求中，要实现主线程等待所有线程完成sheet的解析操作，最简单的做法是使用join。也可以使用CountDownLatch】
    //其实每个sheet页启动一个线程。然后用  boolean awaitTermination(long timeout, TimeUnit unit) 更好
 * 避免线程一直等待
 */
public class CountDownLatchTest {

    static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("解析sheet1开始");
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("解析sheet1完成");
                countDownLatch.countDown();
            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("解析sheet2开始");
                try {
                    Thread.sleep(15000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("解析sheet2完成");
                countDownLatch.countDown();
            }
        };
        threadPool.execute(r1);
        threadPool.execute(r2);
        //@TODO 谨记关闭线程池
        threadPool.shutdown();
        countDownLatch.await();
        System.out.println("excel解析完成");
    }
}
