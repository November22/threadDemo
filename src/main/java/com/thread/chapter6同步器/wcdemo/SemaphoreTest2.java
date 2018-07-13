package com.thread.chapter6同步器.wcdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 * @date 2018/7/13.
 * 场景：wc只有10个坑位，100个人排队，大家公平使用
 * 目标：无打架抢坑（线程并发异常），有序等待
 * 分为两个对象
 * WcSourceManager：厕所坑位管理类。资源管理，保证有限资源的使用。 new Semaphore(wcNum,true)  使用公平锁
 * WcUser：等待上厕所的人。资源的使用者，去使用有限的资源。
 */
public class SemaphoreTest2 {

    public static void main(String[] args) {
        WcSourceManager wcSourceManager = new WcSourceManager();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            Runnable r = new WcUser(wcSourceManager,i);
            executorService.execute(r);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10,TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}