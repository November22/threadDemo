package com.thread.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sen.huang
 *         Date: 2018/6/28
 */
public class SynchronizeedTest1 {

    private static int race = 10;

    public static void getId(){
        race++;
    }


    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        for(int i=0;i<20;i++){
//            threads[i] = new Thread(){
//                public void run(){
//                    for(int i=0;i<10000;i++){
//                        getId();
//
//                    }
//                    System.out.println(race);
//                }
//            };
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for(int i=0;i<10000;i++){
                        getId();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount()>2){
            System.out.println("当前线程数："+Thread.activeCount());
            Thread.yield();
        }
        System.out.println(race);
    }
}
