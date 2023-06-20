package com.example.marathon.service;

import com.example.marathon.dataobject.Events;
import com.example.marathon.mapper.EventsMapper;
import com.example.marathon.mapper.RegisterMapper;
import com.example.marathon.mapper.UserMapper;
import com.example.marathon.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final EventsMapper eventsMapper;
    private final UserMapper userMapper;
    private final RegisterMapper registerMapper;
    private final RedisUtil redisService;

    public ManagerServiceImpl(EventsMapper eventsMapper, UserMapper userMapper, RegisterMapper registerMapper, RedisUtil redisService) {
        this.eventsMapper = eventsMapper;
        this.userMapper = userMapper;
        this.registerMapper = registerMapper;
        this.redisService = redisService;
    }

    @Override
    public void draw(String eventId) {
        String eventName = eventsMapper.getName(eventId);
//        int count = eventsMapper.getLimits(eventId);
        List<String> randomLists = redisService.randMembers(eventId, 2);
        List<String> participants = registerMapper.getParticipants(eventId);

        for (String userId : participants) {
            String username = userMapper.getUsername(userId);
            if (randomLists.contains(userId)) {
                registerMapper.updateSelect(1, userId, eventId);
                redisService.publish("marathon", "尊敬的" + username + "，恭喜您在本次" + eventName + "马拉松中中签");
            } else {
                registerMapper.updateSelect(0, userId, eventId);
                redisService.publish("marathon", "尊敬的" + username + "，很遗憾您未中本次" + eventName + "马拉松的抽签");
            }
//
        }
    }

    @Override
    public void createEvents(Events event) {
        // 新赛事入库
        eventsMapper.insert(event);
        // 删除缓存中的赛事列表
        redisService.removeKey("eventsnull");
        // 创建新赛事set用于报名
        Date deadline = event.getRegistrationDeadline();
        // todo eventId 不应重复 还没判断
        redisService.createSet(event.getEventId(), getDeadLine(deadline), TimeUnit.DAYS);
    }

    private static long getDeadLine(Date deadLine){
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = deadLine.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
