package com.example.marathon.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.marathon.dataobject.Events;
import com.example.marathon.mapper.EventsMapper;
import com.example.marathon.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl extends ServiceImpl<EventsMapper, Events> implements EventService {

    private final RedisUtil redisUtil;

    public EventServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private static final String EVENTS = "events";

    @Override
    public List<Events> listEvents(int type, int pageNum, int pageSize) {
        String key = EVENTS + type;
        List<Events> listFromCache = redisUtil.getFromCache(key, new TypeReference<>() {
        });

        // 未命中缓存
        if (listFromCache == null) {
            QueryWrapper<Events> wrapper = new QueryWrapper<>();
            wrapper.eq(type != -1, "type", type);

            Page<Events> page = new Page<>(pageNum, pageSize);
            List<Events> records = page(page, wrapper).getRecords();

            redisUtil.setToCache(key, JSONObject.toJSONString(records));
            return records;
        }
        // 返回指定页的结果
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listFromCache.size());
        return listFromCache.subList(startIndex, endIndex);
    }
}
