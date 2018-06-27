package com.thread.chapter1;

/**
 * @author sen.huang
 *         Date: 2018/6/27
 */
public class TestJoin {
    private static int result = 0;
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(){
            @Override
            public void run(){
                for(int i=0;i<10000;i++){
                    try {
                        Thread.sleep(1L);
                        result = result+i;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        /**
         * 这中间执行其他的工作任务
         * */
        Thread.sleep(10L);
        System.out.println("其他任务执行完成，等待工作线程，执行完成");
        try {
            //其他工作执行完成后，开始等待thread的工作完成
            thread.join();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
