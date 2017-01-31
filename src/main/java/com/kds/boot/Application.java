package com.kds.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *  Spring boot main application
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application {

    public static void main (String [] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        // Only for POC type projects
        //in memory
        ConcurrentMapCacheManager concurrentMapCacheManager =
                new ConcurrentMapCacheManager("greetings");
        return concurrentMapCacheManager;
    }
}
