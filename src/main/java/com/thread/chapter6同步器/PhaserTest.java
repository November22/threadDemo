package com.thread.chapter6同步器;

import java.util.concurrent.Phaser;

/**
 * @author sen.huang
 *         Date: 2018/7/12
 */
public class PhaserTest  implements Runnable{

    public PhaserTest(Phaser phaser){
        this.phaser = phaser;
    }

    //Phaser这个类主要是针对于多阶段注册的任务的多线程工具类.
    //以下是个人理解,没看API源码.
    //顾名思义这个类中会存在类似的两个变量来控制注册和阶段.

    //注册步骤(parties)一般情况下可理解为线程.
    //可以通过arrive()/arriveAndAwaitAdvance()/arriveAndDeregister()推进每个已注册步骤的完成.
    //可以在初始化Phaser时候指定,并且采用register()/bulkRegister(int parties)方法来注册增加步骤.
    //为了让已注册步骤不参与下一阶段的操作可采用arriveAndDeregister().
    //特殊情况下注册步骤不可理解为线程.比如当某个线程调用两次arrive()那么相当于推进两次注册步骤.

    //Phaser.getPhase()用于获取当前的阶段.默认阶段从0开始.
    //当某个阶段所有的注册步骤都完成的时候.阶段值会自动增加1.
    //Phaser存在一个awaitAdvance(int phase)方法.如果形参等于Phaser.getPhase()即当前阶段,那么等待.直到phase改变的时候这个方法继续执行.

	/*
	*以下是一些对应的解释代码.
	*假设一个情景:一项任务分为三个阶段(phase = 3),每个阶段有4个注册步骤.
	*/

    public static void main(String[] args){
        int parties = 4;//四个步骤
        Phaser phaser = new Phaser(parties);
        Thread[] threads = new Thread[parties];
        for(int i = 0 ; i < parties; i ++){
            //四个线程用的同一个Phaser
            threads[i] = new Thread(new PhaserTest(phaser));
        }

        try{
            /**
             * 第一次：first(); 方法等待 - 到达屏障线程数1
             * 第二次：first(); 方法等待 - 到达屏障线程数2
             * 第三次：first(); 方法等待 - 到达屏障线程数3
             * 第四次：first(); 方法等待 - 到达屏障线程数4
             *      1.然后四个线程的first方法执行；
             *      2.接着四个线程又执行second();方法，到达屏障四个线程执行
             */
            for(int i = 0 ; i < parties ; i ++){
                System.out.println(i);

//                threads[i].join();
                threads[i].start();
                Thread.sleep(6000);
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(phaser.isTerminated());

    }

    @Override
    public void run(){
        first();
        second();
        third();
    }

    public void first(){
        //记录到达，并等待前进
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + ":first.");
    }

    public void second(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + ":second.");

    }

    public void third(){
        //抵达此phaser，同时从中注销而不会等待其他线程到达，
        // 由此减少了未来需要phaser上需要前进的线程数量。
        //相遇与自己到了减一，自己任务再减一
        // phaser.register(); 往这个phaser中注册一个尚未抵达的线程

        phaser.arriveAndDeregister();
        System.out.println(Thread.currentThread().getName() + ":third.");

    }

    private final Phaser phaser;

}
