package com.nynu.goule.service.impl;

import com.nynu.goule.config.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskServiveImpl {

    private final Logger logger = LoggerFactory.getLogger(TaskServiveImpl.class);

    @Autowired
    private ThreadPoolConfig threadPoolInstance;

    @Autowired
    private RedisTemplate redisTemplate;



    @Scheduled(cron = "0 40 21 22 11 ?")
    public void task1ToTest(){
        threadPoolInstance.createThreadPoolInstance().execute(new Runnable() {
            @Override
            public void run() {
                redisTemplate.opsForValue().set("1","task1ToTest");
            }
        });
        logger.info("task1ToTest启动...");

    }

    @Scheduled(cron = "0 40 21 22 11 ?")
    public void task2ToTest(){
        threadPoolInstance.createThreadPoolInstance().execute(new Runnable() {
            @Override
            public void run() {
                redisTemplate.opsForValue().set("1","task2ToTest");
            }
        });
        logger.info("task2ToTest启动...");
    }
}
