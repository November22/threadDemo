package com.thread.chapter8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 *         Date: 2018/7/16
 */
public class QueueTest {

    public static void main(String[] args) {
        //Queue中元素按FIFO原则进行排序
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final BlockingQueue<Character> bq;
        bq = new ArrayBlockingQueue<Character>(26);
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        Runnable producer = new Runnable() {
            public void run() {
                for(char ch='A';ch <= 'Z';ch++){
                    try {
                        lock.lock();
                        bq.put(ch);
                        System.out.println("生产者生产["+ch+"]");
                        //生产完一条数据，就开始等待唤醒
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };

        Runnable consumer = new Runnable() {
            public void run() {
                Character take = null;
                do {
                    try {
                        //take 取出队列中的值，队列长度减1，队列为空时，使用take方法会阻塞,但是不会释放掉当前线程持有的锁
                        take = bq.take();
                        System.out.println(bq.size());;
                        System.out.println("消费者消费["+take+"]");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //消费完一条数据唤醒生产者
                        lock.lock();
                        //只有在finally的时候，获取锁来唤醒生产者，而此时生产者处以等待中，也不会去强锁。
                        condition.signal();
                        lock.unlock();
                    }
                } while (take != 'Z');
                executorService.shutdownNow();
            }
        };
        executorService.execute(producer);
        executorService.execute(consumer);

    }
}
