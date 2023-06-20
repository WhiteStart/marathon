package com.example.marathon.service;

import com.example.marathon.dataobject.Events;

public interface ManagerService {

    /**
     * 消息推送
     * @param eventId
     */
    void draw(String eventId);

    /**
     * 创建赛事
     *
     * 同时更新redis中的列表信息
     * @param event
     */
    void createEvents(Events event);
}
