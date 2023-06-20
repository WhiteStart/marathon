package com.example.marathon.util;

import com.alibaba.fastjson.TypeReference;
import com.example.marathon.model.RedisData;

import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface RedisUtil {

    String getFromCache(String key);

    <T> T getFromCache(String key, TypeReference<T> typeReference);

    <T> T getFromCache(String key, Class<T> clazz);

    void setToCache(String key, String value);

    void setToCache(String key, String value, long timeout, TimeUnit timeUnit);

    <T> void setToCache(String key, T value);

    <T> void setToCache(String key, T value, long timeout, TimeUnit timeUnit);

    // 互斥锁解决缓存击穿
    <T, R> R setWithMutex(String keyPrefix, T value, Class<R> targetClass, Function<T, R> function);

    <T, R> R setWithMutex(String keyPrefix, T value, Class<R> targetClass, Function<T, R> function, long time, TimeUnit unit);

    // 逻辑过期解决缓存击穿
    // 应提前存入redis
    <T, R> R setWithLogicExpire(String keyPrefix, T value, TypeReference<RedisData<R>> typeReference, Function<T, R> function, long time, TimeUnit unit);

    <T, R> R setWithLogicExpire(String keyPrefix, T value, TypeReference<RedisData<R>> typeReference, Function<T, R> function);

    Boolean tryLock(String key);

    void removeKey(String key);

    // 操作set
    Boolean isSetContains(String key, String userId);

    Boolean createSet(String key);

    Boolean createSet(String key, long timeout, TimeUnit timeUnit);

    void sAddToSet(String key, String value);

    List<String> randMembers(String key, Integer count);

    void publish(String channel, String message);
}
