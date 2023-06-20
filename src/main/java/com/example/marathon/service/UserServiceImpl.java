package com.example.marathon.service;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.marathon.dataobject.Events;
import com.example.marathon.dataobject.Register;
import com.example.marathon.dataobject.User;
import com.example.marathon.mapper.EventsMapper;
import com.example.marathon.mapper.RegisterMapper;
import com.example.marathon.mapper.UserMapper;
import com.example.marathon.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RegisterMapper registerMapper;
    private final EventsMapper eventsMapper;
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;

    public UserServiceImpl(RegisterMapper registerMapper, EventsMapper eventsMapper, UserMapper userMapper, RedisUtil redisService) {
        this.registerMapper = registerMapper;
        this.eventsMapper = eventsMapper;
        this.userMapper = userMapper;
        this.redisUtil = redisService;
    }

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        } else {
            if (password.equals(user.getPassword())) {
                return user;
            }
            return null;
        }
    }

    @Override
    public boolean apply(String userId, String eventId) {
        // 已报过名
        if (redisUtil.isSetContains(eventId, userId)) {
            return false;
        } else {
            Register register = new Register();
            register.setParticipantId(userId);
            register.setEventId(eventId);

            registerMapper.insert(register);
            redisUtil.sAddToSet(eventId, userId);
            return true;
        }
    }

    @Override
    public Events searchEvent(String name) {
        return redisUtil.setWithMutex("hot:", name, Events.class, input -> {
            Events event = eventsMapper.getSearchedEvent(input);
            return event;
        });
    }

    @Override
    public Events searchEventLogic(String name) {
        return redisUtil.setWithLogicExpire("hot:", name, new TypeReference<>() {}, eventsMapper::getSearchedEvent, 2L, TimeUnit.SECONDS);
    }
}
