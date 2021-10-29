package com.nynu.goule.file;

import com.zaxxer.hikari.util.ClockSource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @author  goule
 * @date  2021/10/29 13:47 
 */
public class ThreadPoolHolder {

    private static ThreadPoolExecutor instance;

    public static ThreadPoolExecutor getThreadPool(){
        if(instance == null){
            synchronized (ThreadPoolHolder.class){
                if(instance == null){
                    int corePoolSize = 6;
                    int maximumPoolSize = corePoolSize + 1;
                    long keepAliceTime = 60L;
                    instance = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliceTime,
                            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(300));
                }
            }
        }
        return instance;
    }
}
