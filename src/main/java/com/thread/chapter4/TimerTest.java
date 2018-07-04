package com.thread.chapter4;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author sen.huang
 *         Date: 2018/7/3
 */
public class TimerTest {

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行1"+Thread.currentThread().getName());
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行2"+Thread.currentThread().getName());
            }
        };
        /**
         * 使用ScheduledExecutorService代替Timer吧 less... (Ctrl+F1)
         * 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。
         *  new Timer("timer-->"); 指定执行任务的名称。
         *  注意定时任务应该很快完成，不然就会霸占定时器的任务执行线程，推迟后续定时任务的执行。
         */
        Timer timer = new Timer("timer-->");
        timer.schedule(task,2000);
        timer.schedule(task2,0,1000);
    }
}
