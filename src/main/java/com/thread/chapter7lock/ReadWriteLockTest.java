package com.thread.chapter7lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author sen.huang
 *         Date: 2018/7/15
 *         模拟多线程对配置文件的读写
 */
public class ReadWriteLockTest {
    //初始化的配置
    private static boolean isOpenNewChannel = false;

    static class ReadRun implements Runnable{

        private Lock rLock;
        private int index;
        public ReadRun(ReentrantReadWriteLock lock,int index){
            rLock = lock.readLock();
            this.index = index;
        }

        public void run() {
            try {
                rLock.lock();
                System.out.println("读锁index["+index+"]");
                Thread.sleep(10*1000L);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
            }
        }
    }

    static  class WriteRun implements Runnable{
        private Lock wLock;
        private int index;
        public WriteRun(ReentrantReadWriteLock lock,int index){
            wLock = lock.writeLock();
            this.index = index;
        }


        public void run() {
            try {
                wLock.lock();
                System.out.println("写锁index["+index+"]");
                Thread.sleep(15*1000L);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                wLock.unlock();
            }
        }
    }

    /**
     * 尝试获取一个读锁的线程会因为写锁被持有或存在一条等待的写线程而被阻塞。
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        for(int index=0;index<15;index++){
            if(index%3 == 0){
                //普通重入锁（ReentrantLock）不会出现线程并发问题，但是读也是同步的，效率低
                new Thread(new WriteRun(lock,index)).start();
            }else{
                new Thread(new ReadRun(lock,index)).start();
            }
        }

        Thread.sleep(10*60*1000L);
    }
}
