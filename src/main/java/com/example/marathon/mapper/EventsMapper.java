package com.example.marathon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.marathon.dataobject.Events;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface EventsMapper extends BaseMapper<Events> {
    @Select("select limits from events where event_id = #{eventId}")
    Integer getLimits(String eventId);

    @Select("select name from events where event_id = #{eventId}")
    String getName(String eventId);

    @Select("select * from events where id = (select id from events where name = #{name})")
    Events getSearchedEvent(String name);

    @Select("select type from events")
    List<String> getTypes();
}
