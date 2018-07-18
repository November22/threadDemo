package com.thread.chapter8;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sen.huang
 *         Date: 2018/7/18
 */
public class AtomicBooleanTest {

    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        System.out.println(atomicBoolean.get());
        boolean b = atomicBoolean.compareAndSet(false, true);
        System.out.println(b);
//        compareAndSet方法的返回值是 如果update成功，就返回true，update失败就返回false
        //或者说预料值和atomicBoolean当前值一致，就返回true，否则返回false
        boolean b1 = atomicBoolean.compareAndSet(false, false);
        System.out.println(b1);

    }

}
