package com.thread.chapter5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sen.huang
 *         Date: 2018/7/5
 */
public class CallabeleTest {

    private static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService ex = Executors.newCachedThreadPool();
        Callable<Integer> c1 = new Callable<Integer>() {
            public Integer call() throws Exception {
                for(int i=0;i<3;i++){
                    Thread.sleep(500L);
                    System.out.println("c1:"+integer.get());
                    integer.incrementAndGet();
                }
                return integer.get();
            }
        };
        Callable<Integer> c2 = new Callable<Integer>() {
            public Integer call() throws Exception {
                for(int i=0;i<3;i++){
                    System.out.println("c2:"+integer.get());
                    integer.incrementAndGet();
                }
                return integer.get();
            }
        };

        Callable<Integer> c3 = new Callable<Integer>() {
            public Integer call() throws Exception {
                for(int i=0;i<3;i++){
                    System.out.println("c3:"+integer.get());
                    integer.incrementAndGet();
                }
                return integer.get();
            }
        };

        Collection<Callable<Integer>> list = new ArrayList<Callable<Integer>>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        //ex.invokeAll(list); 这个方法提交后，等待所有线程结束，但是不关闭
        List<Future<Integer>> futures = ex.invokeAll(list);
        System.out.println("线程已经提交");
        for(Future o:futures){
            System.out.println(o.get()+"--"+o.isDone());
        }
        ex.shutdown();
    }
}
