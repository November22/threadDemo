package com.thread.chapter6同步器;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 *         Date: 2018/7/11
 */
public class SemaphoreTest {
    static class Wc implements Runnable{
        //测试的可用坑位
        private Semaphore semaphore;
        private String userId;
        public Wc(Semaphore semaphore,String userId){
            //初始化10个坑
            this.semaphore = semaphore;
            this.userId = userId;
        }

        public void run() {
            try {
                System.out.println("用户["+userId+"],等待坑位");
                semaphore.acquire();
                System.out.println("用户["+userId+"],获得坑位");
                Thread.sleep(1000L);
                semaphore.release();
                System.out.println("用户["+userId+"],获得释放坑位");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3,true);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int i=100;i<110;i++){
            Wc wc = new Wc(semaphore, i + "");
            threadPool.execute(wc);
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("大家都便便完了");
    }

}
