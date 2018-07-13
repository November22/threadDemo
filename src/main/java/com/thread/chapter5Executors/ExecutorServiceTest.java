package com.thread.chapter5Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
                    System.out.println("任务执行完成1！！！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //submit 会等待提交的线程执行完成，execute不会
//        threadPool.submit(r);
        //
        threadPool.execute(r);
        System.out.println("线程1提交");
        Runnable r2 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000*30L);
                    System.out.println("任务执行完成2！！！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
//        Future<Integer> submit = threadPool.submit(r2, 3);
//        System.out.println(submit.get());
        threadPool.execute(r2);
        System.out.println("线程2提交");
        threadPool.shutdown();
        System.out.println("有序关闭之前提交的线程");
        try {
            threadPool.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
