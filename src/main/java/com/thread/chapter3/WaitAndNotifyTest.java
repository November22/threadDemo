package com.thread.chapter3;

/**
 * @author sen.huang
 *         Date: 2018/7/2
 */
public class WaitAndNotifyTest {
    //当其他线程获取处理器的时间，那么就可以获得这个锁对象
    private static final Object object = new Object();

    private static volatile boolean b = true;

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(){
            public void run(){
                synchronized (object){
                    try {
                        System.out.println("t1-->"+Thread.holdsLock(object));
                        System.out.println("线程等待");
                        while (b) {
                            System.out.println("执行："+b);
                            object.wait();//当线程为等待，while不在循环。
                            System.out.println("执行2");
                        }
                        System.out.println("线程被唤醒");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };


        Thread t2 = new Thread(){
            public void run(){
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t21-->"+Thread.holdsLock(object));
                synchronized (object){
                    System.out.println("t22-->"+Thread.holdsLock(object));
                    System.out.println("拿到对象object");
                    b = false;//修改为false，不会再出发while循环
                    System.out.println("b修改为："+b);
                    object.notify();//此时唤醒，处于等待的t1，然后执行while，不被循环
                }
            }
        };

        t1.start();
        t2.start();

    }
}
