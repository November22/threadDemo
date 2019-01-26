package com.thread.chapter2;

/**
 * @author sen.huang
 *         Date: 2018/7/2
 */
public class DeadLockTest {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public void invokeM1() throws Exception{
        synchronized (lock1){
            //添加睡眠，等待invokeM2拿到lock2
            Thread.sleep(50);
            System.out.println("invokeM1 wake up");
            synchronized (lock2){
                System.out.println("线程1执行");
            }
        }
    }

    public void invokeM2() throws Exception{
        synchronized (lock2){
            //添加睡眠，等待invokeM1拿到lock1
            Thread.sleep(50);
            System.out.println("invokeM2 wake up");
            synchronized (lock1){
                System.out.println("线程2执行");
            }
        }
    }

    public static void main(String[] args) {
        final DeadLockTest test = new DeadLockTest();
        Runnable r1 = new Runnable() {
            public void run() {
                try {
                    test.invokeM1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2 = new Runnable() {
            public void run() {
                try {
                    test.invokeM2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println(lock1.toString());
        System.out.println(lock2.toString());
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }

}
