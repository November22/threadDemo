package com.thread.chapter6同步器.wcdemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sen.huang
 * @date 2018/7/13.
 * 厕所资源管理器
 */
public class WcSourceManager {
    /**
     * 获得坑位的锁,防止两个人用一个坑位
     */
    private ReentrantLock lock;

    /**
     * 初始化坑位个数
     */
    private static volatile int wcNum = 10;
    /**
     * 坑位的使用状态,false-无人使用，true-被使用
     */
    private boolean[] wcUseStatus;

    /**
     * 管理坑位的信号量
     */
    private Semaphore semaphore;

    /**
     * 坑位使用者的编号
     */
    public WcSourceManager(){
        //初始化坑位管理的信号量
        //new Semaphore(wcNum) 非公平锁
        //new Semaphore(wcNum,true) 公平锁
        semaphore = new Semaphore(wcNum,true);
        wcUseStatus = new boolean[wcNum];
        for(int i=0;i<wcNum;i++){
            wcUseStatus[i] = false;
        }
    }

    /**
     * 有人来上厕所
     * @param userId
     */
    public void useWc(int userId){
        try {
            System.out.println(userId+"等待坑位");
            //获取坑位等待
            semaphore.acquire();
            System.out.println(userId+"获得坑位！！");
            Thread.sleep(2000L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(userId+"便便完成释放坑位");
        }
    }

    /**
     * 获取可使用的坑位，并标记为当前不可使用
     * @return
     */
    private int getWcId(){
        try {
            //防止竞争同一个坑位
            lock.lock();
            for(int i=0;i<wcNum;i++){
                //获得坑位没有被使用的坑位
                if(!wcUseStatus[i]){
                    //修改坑位为使用的状态
                    wcUseStatus[i] = false;
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最总释放锁，让后人肯选坑
            lock.unlock();
        }
        throw new RuntimeException("没有可用的坑位资源");
    }
}
