package com.thread.chapter1;

/**
 * @author sen.huang
 *         Date: 2018/6/27
 */
public class TestThread {
    public static void main(String[] args) {
        Thread mainT = Thread.currentThread();
        Thread t1 = new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //打开表示t1线程是main的守护线程，main结束，t1线程结束。
//        t1.setDaemon(true);
        t1.start();
        //对执行 sleep或join方法的线程进行中断，会抛错。
//        t1.interrupt();
        System.out.println(t1.isInterrupted());;
        System.out.println(mainT.getName());
    }
}
