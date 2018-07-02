package com.thread.chapter2;

/**
 * @author sen.huang
 *         Date: 2018/7/2
 */
public class DeadLockTest {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public void invokeM1(){
        synchronized (lock1){
            synchronized (lock2){
                System.out.println("线程1执行");
            }
        }
    }

    public void invokeM2(){
        synchronized (lock2){
            synchronized (lock1){
                System.out.println("线程2执行");
            }
        }
    }

    public static void main(String[] args) {
        final DeadLockTest test = new DeadLockTest();
        Runnable r1 = new Runnable() {
            public void run() {
                while (true){
                    test.invokeM1();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable r2 = new Runnable() {
            public void run() {
                while (true){
                    test.invokeM2();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }

}
