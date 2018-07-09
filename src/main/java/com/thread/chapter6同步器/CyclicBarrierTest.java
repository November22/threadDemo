package com.thread.chapter6同步器;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 *         Date: 2018/7/9
 *    [人满发车]这个词来理解CyclicBarrier的作用：
 * 长途汽车站提供长途客运服务。
 * 当等待坐车的乘客到达20人时，汽车站就会发出一辆长途汽车，让这20个乘客上车走人。
 * 等到下次等待的乘客又到达20人是，汽车站就会又发出一辆长途汽车。
 */
public class CyclicBarrierTest {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("解析sheet1开始");
                try {
                    //等待超时会抛出异常。java.util.concurrent.TimeoutException
//                    cyclicBarrier.await(1000L, TimeUnit.MILLISECONDS);
                    cyclicBarrier.await();
                    System.out.println("同步执行器等待完成1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("解析sheet1完成");

            }
        };
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("解析sheet2开始");
                try {
                    Thread.sleep(15000L);
                    //如果有其他的等待线程抛出异常，则其他的等待线程会抛出 java.util.concurrent.BrokenBarrierException
                    //同步屏障被打破
                    cyclicBarrier.await();
                    System.out.println("同步2执行");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("解析sheet2完成");
            }
        };
        threadPool.execute(r1);
        threadPool.execute(r2);
        //@TODO 谨记关闭线程池
        threadPool.shutdown();
        while (true){
            try {
                Thread.sleep(500L);
                System.out.println(cyclicBarrier.getNumberWaiting());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
