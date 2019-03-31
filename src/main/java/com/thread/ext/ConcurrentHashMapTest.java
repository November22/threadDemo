package com.thread.ext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sen.huang
 * @date 2019/3/30.
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String,String> syncMap = new ConcurrentHashMap<String, String>();
        String put = syncMap.put("key", "value");
        System.out.println(put);
    }
}
