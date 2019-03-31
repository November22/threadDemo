package com.thread.ext;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 * @date 2019/3/31.
 */
public class TestExecutors implements Runnable{
    public void run() {
        System.out.println("线程["+Thread.currentThread().getName()+"]，start 睡眠");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程["+Thread.currentThread().getName()+"]，end 睡眠");

    }

    @Override
    public String toString() {
        return "123";
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(3,
                6,
                15,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(3),new RejectedExecutionHandler(){
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //Runnable被拒绝的线程
                //ThreadPoolExecutor执行不了此任务的线程池线程池
            }
        });
        for(int index=0;index<20;index++){
            executorService.execute(new TestExecutors());
        }
        Thread.sleep(20*5000L);
    }
}
