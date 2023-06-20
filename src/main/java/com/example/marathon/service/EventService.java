package com.example.marathon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.marathon.dataobject.Events;

import java.util.List;

public interface EventService extends IService<Events> {

    /**
     * 在常规的分页查询中，当用户点击下一页时，通常会根据页码触发一次新查询请求
     *
     * 如果希望在点击下一页时不触发新查询请求，而是从缓存中获取已经加载的数据进行展示
     * 那么需要在前端进行相应的处理。可以在前端保存已加载的数据，并根据页码和页面大小进行数据的切片展示
     *
     * 根据种类展示若干Events信息，旁路缓存
     *
     * @param type 种类
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return List<Events>
     */
    List<Events> listEvents(int type, int pageNum, int pageSize);
}
