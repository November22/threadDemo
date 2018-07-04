package com.thread.chapter4;

/**
 * @author sen.huang
 *         Date: 2018/7/3
 */
public class ThreadLocalTest {
//    使用ThreadLocal，子线程无法获得父线程的线程局部变量
    private static volatile ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
//    InheritableThreadLocal可以让子线程获得父线程的局部变量
//    private static volatile InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>();

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                threadLocal.set(100);
                ThreadLocal threadLocal1 = new ThreadLocal();
                System.out.println("仙剑："+threadLocal1.get());

                Runnable r2 = new Runnable() {
                    public void run() {
                        String name = Thread.currentThread().getName();
                        System.out.println("name:"+name+",value"+threadLocal.get());
                    }
                };
                Thread thread = new Thread(r2,"run2");
                thread.start();
            }
        };
        new Thread(runnable).start();
    }
}
