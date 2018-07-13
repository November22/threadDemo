package com.thread.chapter6同步器.wcdemo;

import com.thread.chapter6同步器.wcdemo.WcSourceManager;

/**
 * @author sen.huang
 * @date 2018/7/13.
 * 需要上测试的人
 */
public class WcUser implements Runnable {

    private WcSourceManager wcSourceManager;

    private int userId;
    /**
     * @param wcSourceManager 上厕所的人，依赖坑位管理器来管理坑位
     * @param userId 人的编号
     */
    public WcUser(WcSourceManager wcSourceManager,int userId){
        this.wcSourceManager = wcSourceManager;
        this.userId = userId;
    }

    /**
     * 使用厕所
     */
    public void run() {
        wcSourceManager.useWc(userId);
    }
}
