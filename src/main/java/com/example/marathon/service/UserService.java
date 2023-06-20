package com.example.marathon.service;

import com.example.marathon.dataobject.Events;
import com.example.marathon.dataobject.User;
import com.example.marathon.model.RedisData;

import java.util.List;


public interface UserService {

    /**
     * 简单的用户名密码登录
     * @param username 名字
     * @param password 密码
     * @return 用户信息
     */
    User login(String username, String password);

    /**
     * 申请赛事
     * @param userId 用户id
     * @param eventId 赛事id
     * @return true 成功 false 失败
     */
    boolean apply(String userId, String eventId);

    /**
     * 搜索赛事
     *
     * 视作热门赛事数据
     * @param name
     * @return
     */
    Events searchEvent(String name);

    Events searchEventLogic(String name);
}
