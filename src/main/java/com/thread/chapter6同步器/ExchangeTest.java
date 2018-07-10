package com.thread.chapter6同步器;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sen.huang
 *         Date: 2018/7/10
 *
 *此类提供对外的操作是同步的；
 *用于成对出现的线程之间交换数据；
 *可以视作双向的同步队列；
 *可应用于基因算法、流水线设计等场景。
 *   https://www.cnblogs.com/davidwang456/p/4179488.html
 */
public class ExchangeTest {

    private static volatile Boolean isExecute = false;

    private static volatile Boolean isDone = false;

    static class ExchangerProducer implements Runnable{
        private Exchanger<Integer> exchanger;
        public ExchangerProducer(Exchanger<Integer> exchanger){
            this.exchanger = exchanger;
        }
        public void run() {
            int data = 0;
            for(int i = 0;i<4;i++){
                try {
                    data++;
                    System.out.println("exchange before:["+data+"]");
                    //消费者去执行
                    isExecute = true;
                    int ecData = exchanger.exchange(data);
                    //获得消费者的返回数据，禁止消费再次执行，直到生产者生产出数据
                    isExecute = false;
                    System.out.println("exchange after:["+ecData+"]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isDone = true;
        }
    }

    static class ExchangeConsumer implements Runnable{
        private Exchanger<Integer> exchanger;
        public ExchangeConsumer(Exchanger<Integer> exchanger){
            this.exchanger = exchanger;
        }
        public void run() {
            while (!isDone) {
                while (isExecute) {
                    try {
                        int i = 0;
//                        Thread.sleep(500L);
                        System.out.println("睡眠结束");
                        Integer exchange = exchanger.exchange(i,100L,TimeUnit.MILLISECONDS);
                        System.out.println("consumer get Data["+exchange+"]");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangeConsumer consumer = new ExchangeConsumer(exchanger);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(producer);
        service.execute(consumer);
        service.shutdown();

        try {
            service.awaitTermination(10, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
