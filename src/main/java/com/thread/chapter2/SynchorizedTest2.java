package com.thread.chapter2;

/**
 * @author sen.huang
 *         Date: 2018/6/28
 */
public class SynchorizedTest2 implements Runnable {
    private TestMethod testMethod;
    public SynchorizedTest2(TestMethod testMethod) {
        this.testMethod = testMethod;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName()+"--->"+testMethod.getId());
    }
}
