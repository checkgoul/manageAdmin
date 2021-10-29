package com.nynu.goule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author  goule
 * @date  2020/10/28 20:14
 */

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class GouleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GouleApplication.class, args);
    }

}
