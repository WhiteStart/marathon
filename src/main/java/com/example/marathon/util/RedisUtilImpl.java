package com.example.marathon.util;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.marathon.model.RedisData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Slf4j
public class RedisUtilImpl implements RedisUtil {

    private static final long DEFAULT_TIMEOUT = 14;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.DAYS;
    private final RedisTemplate<String, String> template;
    private final ThreadPoolExecutor myExecutor;

    public RedisUtilImpl(RedisTemplate<String, String> template, ThreadPoolExecutor myExecutor) {
        this.template = template;
        this.myExecutor = myExecutor;
    }

    @Override
    public String getFromCache(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public <T> T getFromCache(String key, TypeReference<T> typeReference) {
        String fromCache = getFromCache(key);
        return JSONObject.parseObject(fromCache, typeReference.getType());
    }

    @Override
    public <T> T getFromCache(String key, Class<T> clazz) {
        String fromCache = getFromCache(key);
        return JSONObject.parseObject(fromCache, clazz);
    }

    @Override
    public void setToCache(String key, String value) {
        template.opsForValue().set(key, value, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    @Override
    public void setToCache(String key, String value, long timeout, TimeUnit timeUnit) {
        template.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public <T> void setToCache(String key, T value) {
        setToCache(key, value, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    @Override
    public <T> void setToCache(String key, T value, long timeout, TimeUnit timeUnit) {
        String content = JSONObject.toJSONString(value);
        setToCache(key, content, timeout, timeUnit);
    }

    public <T, R> R setWithMutex(String keyPrefix, T value, Class<R> targetClass, Function<T, R> function) {
        return setWithMutex(keyPrefix, value, targetClass, function, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    public <T, R> R setWithMutex(String keyPrefix, T value, Class<R> targetClass, Function<T, R> function, long time, TimeUnit unit) {
        String key = keyPrefix + value;
        String fromCache = getFromCache(key);
        if (StringUtils.isNotBlank(fromCache)) {
            log.info("命中缓存");
            return JSONObject.parseObject(fromCache, targetClass);
        }
        String mutex = "hot:mutex:" + value;
        // 获取互斥锁
        if (!tryLock(mutex)) {
            log.info("未获取到互斥锁、等待");
            try {
                Thread.sleep(100);
                return setWithMutex(keyPrefix, value, targetClass, function);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("获取到互斥锁");
        try {
            R r = function.apply(value);
            log.info("缓存热点数据");
            setToCache(key, r, time, unit);
            return r;
        } finally {
            removeKey(mutex);
        }
    }

    @Override
    public <T, R> R setWithLogicExpire(String keyPrefix, T value, TypeReference<RedisData<R>> typeReference, Function<T, R> function) {
        return setWithLogicExpire(keyPrefix, value, typeReference, function, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    @Override
    public <T, R> R setWithLogicExpire(String keyPrefix, T value, TypeReference<RedisData<R>> typeReference, Function<T, R> function
            , long time, TimeUnit unit) {
        String key = keyPrefix + value;
        String fromCache = getFromCache(key);
        // 逻辑过期中，如果不存在说明当前数据并不是热点数据，本就不在缓存中
        if (StringUtils.isBlank(fromCache)) {
            log.info("当前数据不存在，可能不是热点数据");
            return null;
        }

        RedisData<R> data = JSONObject.parseObject(fromCache, typeReference);
        R r = data.getData();
        String mutex = "hot:mutex:" + value;
        if (data.getExpireTime().isAfter(LocalDateTime.now())) { // 还没过期 || 没获取到互斥锁
            log.info("数据未过期，直接返回");
            return r;
        }
        log.debug("===数据过期===");

        if (!tryLock(mutex)) {
            log.info("未获取到互斥锁，直接返回旧数据:{}", r);
            return r;
        }

        try {
            log.info("获取到了互斥锁");
            Future<R> submit = myExecutor.submit(() -> {
                R apply = function.apply(value);
                // 防止线程安全问题，新建了一个RedisData<R>对象
                RedisData<R> newData = new RedisData<>();
                newData.setData(apply);
                newData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
                log.info("更新redis中的逻辑过期键值对");
                setToCache(key, newData);
                return apply;
            });
            R ret = submit.get();
            log.info("返回数据库中的信息:{}", r);
            return ret;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("解锁");
            removeKey(mutex);
        }
        return null;
    }

    @Override
    public Boolean tryLock(String key) {
        Boolean nx = template.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(nx);
    }

    @Override
    public void removeKey(String key) {
        template.delete(key);
    }

    @Override
    public Boolean isSetContains(String key, String userId) {
        return template.boundSetOps(key).isMember(userId);
    }

    @Override
    public Boolean createSet(String key) {
        return createSet(key, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    @Override
    public Boolean createSet(String key, long timeout, TimeUnit timeUnit) {
        BoundSetOperations<String, String> set = template.boundSetOps(key);
        set.add("-1");
        return set.expire(timeout, timeUnit);
    }

    @Override
    public void sAddToSet(String key, String value) {
        template.opsForSet().add(key, value);
    }

    @Override
    public List<String> randMembers(String key, Integer count) {
        return template.opsForSet().randomMembers(key, count);
    }

    @Override
    public void publish(String channel, String message) {
        template.convertAndSend(channel, message);
    }
}

