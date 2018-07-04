package com.thread.chapter4;

/**
 * @author sen.huang
 *         Date: 2018/7/3
 */
public class ThreadExceptionGHandlerTest {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            public void run() {
                int x = 1/0;
            }
        };

        Thread t = new Thread(r,"wahaha");

        Thread.UncaughtExceptionHandler uceh;
        uceh = new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e){
                System.out.println("e:"+e+",name:"+t.getName());
            }
        };
        t.setUncaughtExceptionHandler(uceh);
        t.start();
    }


}
