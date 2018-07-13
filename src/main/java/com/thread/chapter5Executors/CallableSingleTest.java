package com.thread.chapter5Executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sen.huang
 *         Date: 2018/7/5
 */
public class CallableSingleTest {

    private static AtomicInteger integer = new AtomicInteger(0);


    public static void main(String[] args) throws Exception{
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                for(int i=0;i<3;i++){
                    Thread.sleep(5000L);
                    System.out.println("c2:"+integer.get());
                    integer.incrementAndGet();
                }
                return integer.get();
            }
        };

        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<Integer> submit = threadPool.submit(c);
        //关闭之前提交的任务，不再接受新的任务
        /**
         * 当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态,
         * 以后不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。
         * 但是，此时线程池不会立刻退出，
         * 直到添加到线程池中的任务都已经处理完成，才会退出。
         * 与它相似的还有一个shutdownNow()，它通过调用Thread.interrupt来实现线程的立即退出。
         */
        threadPool.shutdown();
        //等待之前的任务结束
        threadPool.awaitTermination(10, TimeUnit.HOURS);
       /* while (!submit.isDone()){
            //线程没有结束调用 Future 的get方法不会被执行。
            System.out.println("线程还没有结束");
        }*/
        System.out.println(submit.isDone());
    }

}
