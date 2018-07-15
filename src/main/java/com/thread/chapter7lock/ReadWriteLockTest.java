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

        public ReadRun(ReentrantReadWriteLock lock){
            rLock = lock.readLock();
        }

        public void run() {
            try {
                rLock.lock();
                System.out.println(isOpenNewChannel);
                Thread.sleep(5000L);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
            }
        }
    }

    static  class WriteRun implements Runnable{
        private Lock wLock;
        public WriteRun(ReentrantReadWriteLock lock){
            wLock = lock.writeLock();
        }


        public void run() {
            try {
                wLock.lock();
                isOpenNewChannel = !isOpenNewChannel;
                System.out.println("修改bool值");
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
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        for(int i=0;i<10;i++){
            if(i == 5 || i==7 ){
                //普通重入锁（ReentrantLock）不会出现线程并发问题，但是读也是同步的，效率低
                new Thread(new WriteRun(lock)).start();
            }
            new Thread(new ReadRun(lock)).start();
        }
    }
}
