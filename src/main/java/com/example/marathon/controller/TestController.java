package com.example.marathon.controller;

import com.example.marathon.mapper.EventsMapper;
import com.example.marathon.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ThreadPoolExecutor myExecutor;

    @GetMapping("/test")
    public String test(){
        System.out.println("push");
        System.out.println("omg");
        return "12≥34567";
    }

//    @GetMapping("/test")
//    public Events queryCachePenetration(@RequestParam String str) throws InterruptedException {
//        String fromCache = redisUtil.getFromCache(str);
//        if (StringUtils.isNotBlank(fromCache)) {
//            // do sth.
//            return JSONObject.parseObject(fromCache, Events.class);
//        }
//
//        // 判断是否命中空值
//        if ("".equals(fromCache)) {
//            throw new RuntimeException("店铺不存在");
//        }
//
//        // 实现缓存重建
//        // 获取互斥锁
//        String mutex = "lock:" + str;
//        try {
//            Boolean b = redisUtil.tryLock(mutex);
//            if (!b) {
//                Thread.sleep(50);
//                return queryCachePenetration(str);
//            }
//            Events searchedEvent = eventsMapper.getSearchedEvent(str);
//            Thread.sleep(200);
//            if (searchedEvent == null) {
//                redisUtil.setToCache(str, "", 30, TimeUnit.SECONDS);
//                log.error("数据库中不存在，防止缓存穿透，缓存空值入redis");
//                return null;
//            } else {
//                redisUtil.setToCache(str, searchedEvent, 30, TimeUnit.SECONDS);
//                log.info("成功从数据库中查询到数据");
//                return searchedEvent;
//            }
//        } finally {
//            redisUtil.removeKey(mutex);
//        }
//    }

//    @GetMapping("/test2")
//    public Events queryBreakdown(@RequestParam String str){
//        String key = "hot:" + str;
//        // 查询缓存
//        String fromCache = redisUtil.getFromCache(key);
//        // 不存在
//        if (StringUtils.isBlank(fromCache)) {
//            return null;
//        }
//
//        // 存在
//        RedisData<Events> redisData = JSONObject.parseObject(fromCache, new TypeReference<>(){});
//        Events event = redisData.getData();
//        if(redisData.getExpireTime().isAfter(LocalDateTime.now())){ // 未过期 直接返回信息
//            return event;
//        }else { // 逻辑过期 重建缓存 会有一致性的问题
//            String lock = "hotlock:" + str;
//            if(redisUtil.tryLock(lock)){ // 获取锁，缓存重建
//                myExecutor.execute(() -> {
//                    try {
//                        RedisData<Events> data = new RedisData<>();
//                        Events latestEvent = eventsMapper.getSearchedEvent(str);
//                        data.setData(latestEvent);
//                        data.setExpireTime(LocalDateTime.now().plusDays(7));
//                        redisUtil.setToCache("hot:" + event.getName(), data);
//                    }finally {
//                        redisUtil.removeKey(lock);
//                    }
//                });
//            }else {
//                return event;
//            }
//        }
//        return event;
//    }
}
