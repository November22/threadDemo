package com.thread.ext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author sen.huang
 * @date 2019/3/27.
 */
public class LockSupportTest implements Runnable{

    private int index;

    public LockSupportTest(int index){
        this.index = index;
    }

    public void run() {
        if(index%10 == 0){
            System.out.println(Thread.currentThread().getName()+"线程park");
            LockSupport.park();
        }
        try {
            Thread.sleep(10*60*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for(int index=0;index<100;index++){
            executorService.submit(new LockSupportTest(index));
        }

        executorService.shutdown();
        boolean b = executorService.awaitTermination(1, TimeUnit.HOURS);

    }

}
