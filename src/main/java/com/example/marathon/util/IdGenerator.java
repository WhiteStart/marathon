package com.example.marathon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class IdGenerator {

    private static final long BEGIN = 1686614400L;
    private static final int BIT = 32;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public long nextId(String keyPrefix) {
        LocalDateTime now = LocalDateTime.now();
        long stamp = now.toEpochSecond(ZoneOffset.UTC) - BEGIN;

        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long increment = redisTemplate.opsForValue().increment("icr" + keyPrefix + ":" + format);

        return (stamp << BIT) | increment;
    }

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2023, 6, 13, 0, 0);
        long second = time.toEpochSecond(ZoneOffset.UTC);
        System.out.println("second = " + second);
    }
}
