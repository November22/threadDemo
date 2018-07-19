package com.thread.chapter8;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author sen.huang
 *         Date: 2018/7/19
 *         用来求和
 */
public class RecursiveTaskTest extends RecursiveTask<Long>{

    /**
     * 处理的数据量
     */
    static final int THRESHOLD = 100;
    long[] array;
    int start;
    int end;

    public RecursiveTaskTest(long[] array,int start,int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }


    protected Long compute() {
        if(end -start < THRESHOLD){
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(String.format("compute %d~%d = %d", start, end, sum));
            return sum;
        }
        //将任务切割
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        RecursiveTaskTest subtask1 = new RecursiveTaskTest(this.array, start, middle);
        RecursiveTaskTest subtask2 = new RecursiveTaskTest(this.array, middle, end);
        //此处使用invokeAll,不浪费当前线程
        invokeAll(subtask1,subtask2);
        Long l1 = subtask1.join();
        Long l2 = subtask2.join();
        return l1+l2;
    }

    public static void main(String[] args) {
        // 创建随机数组成的数组:
        long[] array = new long[400];
        for(int i=0;i<400;i++){
           array[i] = Long.valueOf(i);
       }
        // fork/join task: 像这样的最大并发数就需要获取当前硬件的处理器数
        //static final int nThreads = Runtime.getRuntime().availableProcessors();
        //一般情况程序线程数等于cpu线程数的两到三倍就能很好的利用cpu了
        ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
        ForkJoinTask<Long> task = new RecursiveTaskTest(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

}
