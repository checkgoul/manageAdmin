package com.nynu.goule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    @Bean(value="threadPoolInstance")
    public ExecutorService createThreadPoolInstance(){
        ExecutorService threadPool = new ThreadPoolExecutor(
                10,
                16,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        return threadPool;
    }
}
