package com.example.marathon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPool {

    @Bean
    public ThreadPoolExecutor myExecutor(){
        return new ThreadPoolExecutor(
                0,
                10,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
    }
}
