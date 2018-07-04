package com.thread.chapter5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 *         Date: 2018/7/4
 */
public class ExecutorServiceTest {
    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Runnable r = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000*30L);
                    System.out.println("任务执行完成！！！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(r);
        Runnable r2 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000*30L);
                    System.out.println("任务执行完成！！！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Future<Integer> submit = threadPool.submit(r2, 3);
        System.out.println(submit.get());
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
